package com.brianmoler.hexaroll.utils

import android.content.Context
import android.content.SharedPreferences
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.ui.components.BackgroundFitMode
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.*

/**
 * Unit tests for ThemeStorage utility class
 * 
 * Tests the theme storage implementation that uses
 * KEY_CUSTOMIZATION for storing complete DiceCustomization objects.
 * 
 * @see ThemeStorage
 */
@RunWith(MockitoJUnitRunner::class)
class ThemeStorageTest {
    
    private lateinit var themeStorage: ThemeStorage
    
    @Mock
    private lateinit var mockContext: Context
    
    @Mock
    private lateinit var mockSharedPreferences: SharedPreferences
    
    @Mock
    private lateinit var mockEditor: SharedPreferences.Editor
    
    private lateinit var gson: Gson
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        gson = Gson()
        
        `when`(mockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences)
        `when`(mockSharedPreferences.edit()).thenReturn(mockEditor)
        `when`(mockEditor.putString(anyString(), anyString())).thenReturn(mockEditor)
        
        themeStorage = ThemeStorage(mockContext)
    }
    
    @Test
    fun `constructor should initialize SharedPreferences with correct name and mode`() {
        verify(mockContext).getSharedPreferences("theme_preferences", Context.MODE_PRIVATE)
    }
    
    @Test
    fun `saveCustomization should store complete DiceCustomization object`() {
        // Given
        val customization = DiceCustomization(
            theme = AppTheme.FANTASY,
            backgroundEnabled = true,
            backgroundOpacity = 0.8f,
            backgroundScaling = BackgroundFitMode.STRETCH
        )
        
        // When
        themeStorage.saveCustomization(customization)
        
        // Then
        verify(mockEditor).putString("dice_customization", anyString())
        verify(mockEditor).apply()
        
        // Verify the stored JSON contains the correct data
        val argumentCaptor = org.mockito.ArgumentCaptor.forClass(String::class.java)
        verify(mockEditor).putString(eq("dice_customization"), argumentCaptor.capture())
        
        val storedJson = argumentCaptor.value
        val storedCustomization = gson.fromJson(storedJson, DiceCustomization::class.java)
        assertEquals(AppTheme.FANTASY, storedCustomization.theme)
        assertTrue(storedCustomization.backgroundEnabled)
        assertEquals(0.8f, storedCustomization.backgroundOpacity, 0.01f)
        assertEquals(BackgroundFitMode.STRETCH, storedCustomization.backgroundScaling)
    }
    
    @Test
    fun `loadCustomization should return stored DiceCustomization when valid data exists`() {
        // Given
        val storedCustomization = DiceCustomization(
            theme = AppTheme.CYBERPUNK,
            backgroundEnabled = false,
            backgroundOpacity = 0.5f,
            backgroundScaling = BackgroundFitMode.FIT_SCREEN
        )
        val storedJson = gson.toJson(storedCustomization)
        
        `when`(mockSharedPreferences.getString("dice_customization", null)).thenReturn(storedJson)
        
        // When
        val result = themeStorage.loadCustomization()
        
        // Then
        assertEquals(AppTheme.CYBERPUNK, result.theme)
        assertFalse(result.backgroundEnabled)
        assertEquals(0.5f, result.backgroundOpacity, 0.01f)
        assertEquals(BackgroundFitMode.FIT_SCREEN, result.backgroundScaling)
    }
    
    @Test
    fun `loadCustomization should return default DiceCustomization when no data exists`() {
        // Given
        `when`(mockSharedPreferences.getString("dice_customization", null)).thenReturn(null)
        
        // When
        val result = themeStorage.loadCustomization()
        
        // Then
        // Should use data class defaults which reference AppDefaultsData
        assertNotNull("Result should not be null", result)
        // Note: Exact values depend on AppDefaultsData constants, but structure should be valid
    }
    
    @Test
    fun `loadCustomization should return default DiceCustomization when JSON is corrupted`() {
        // Given
        `when`(mockSharedPreferences.getString("dice_customization", null)).thenReturn("invalid json {")
        
        // When
        val result = themeStorage.loadCustomization()
        
        // Then
        // Should fall back to data class defaults when JSON parsing fails
        assertNotNull("Result should not be null", result)
        // Note: Exact values depend on AppDefaultsData constants, but structure should be valid
    }
    
    @Test
    fun `loadCustomization should handle empty JSON string gracefully`() {
        // Given
        `when`(mockSharedPreferences.getString("dice_customization", null)).thenReturn("")
        
        // When
        val result = themeStorage.loadCustomization()
        
        // Then
        // Should fall back to data class defaults when JSON is empty
        assertNotNull("Result should not be null", result)
    }
    
    @Test
    fun `storage should handle complex theme combinations correctly`() {
        // Given
        val complexCustomization = DiceCustomization(
            theme = AppTheme.ANCIENT,
            backgroundEnabled = true,
            backgroundOpacity = 1.0f,
            backgroundScaling = BackgroundFitMode.FILL_SCREEN
        )
        
        // When
        themeStorage.saveCustomization(complexCustomization)
        val argumentCaptor = org.mockito.ArgumentCaptor.forClass(String::class.java)
        verify(mockEditor).putString(eq("dice_customization"), argumentCaptor.capture())
        
        // Simulate loading the stored data
        `when`(mockSharedPreferences.getString("dice_customization", null)).thenReturn(argumentCaptor.value)
        val loadedCustomization = themeStorage.loadCustomization()
        
        // Then
        assertEquals(AppTheme.ANCIENT, loadedCustomization.theme)
        assertTrue(loadedCustomization.backgroundEnabled)
        assertEquals(1.0f, loadedCustomization.backgroundOpacity, 0.01f)
        assertEquals(BackgroundFitMode.FILL_SCREEN, loadedCustomization.backgroundScaling)
    }
    
    @Test
    fun `storage should persist data across multiple save operations`() {
        // Given
        val firstCustomization = DiceCustomization(theme = AppTheme.FANTASY)
        val secondCustomization = DiceCustomization(theme = AppTheme.SCI_FI)
        
        // When
        themeStorage.saveCustomization(firstCustomization)
        themeStorage.saveCustomization(secondCustomization)
        
        // Then
        verify(mockEditor, times(2)).putString("dice_customization", anyString())
        verify(mockEditor, times(2)).apply()
    }
    
    @Test
    fun `storage should handle all theme types correctly`() {
        // Test all available themes
        val themes = listOf(
            AppTheme.CYBERPUNK,
            AppTheme.FANTASY,
            AppTheme.SCI_FI,
            AppTheme.WESTERN,
            AppTheme.ANCIENT
        )
        
        themes.forEach { theme ->
            val customization = DiceCustomization(theme = theme)
            themeStorage.saveCustomization(customization)
            
            val argumentCaptor = org.mockito.ArgumentCaptor.forClass(String::class.java)
            verify(mockEditor).putString(eq("dice_customization"), argumentCaptor.capture())
            
            // Verify the theme was stored correctly
            val storedJson = argumentCaptor.value
            val storedCustomization = gson.fromJson(storedJson, DiceCustomization::class.java)
            assertEquals("Theme $theme should be stored correctly", theme, storedCustomization.theme)
        }
    }
}
