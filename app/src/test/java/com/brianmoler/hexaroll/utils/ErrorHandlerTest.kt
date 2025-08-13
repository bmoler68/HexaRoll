package com.brianmoler.hexaroll.utils

import android.content.Context
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit tests for ErrorHandler utility class
 * 
 * Tests validation methods and error handling functionality
 * that ensures data integrity throughout the application.
 */
class ErrorHandlerTest {
    
    @Mock
    private lateinit var mockContext: Context
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }
    
    @Test
    fun `validateDiceCount should return true for valid counts`() {
        assertTrue("0 should be valid", ErrorHandler.validateDiceCount(0))
        assertTrue("1 should be valid", ErrorHandler.validateDiceCount(1))
        assertTrue("50 should be valid", ErrorHandler.validateDiceCount(50))
        assertTrue("100 should be valid", ErrorHandler.validateDiceCount(100))
    }
    
    @Test
    fun `validateDiceCount should return false for invalid counts`() {
        assertFalse("-1 should be invalid", ErrorHandler.validateDiceCount(-1))
        assertFalse("101 should be invalid", ErrorHandler.validateDiceCount(101))
        assertFalse("1000 should be invalid", ErrorHandler.validateDiceCount(1000))
    }
    
    @Test
    fun `validateModifier should return true for valid modifiers`() {
        assertTrue("-1000 should be valid", ErrorHandler.validateModifier(-1000))
        assertTrue("-1 should be valid", ErrorHandler.validateModifier(-1))
        assertTrue("0 should be valid", ErrorHandler.validateModifier(0))
        assertTrue("1 should be valid", ErrorHandler.validateModifier(1))
        assertTrue("1000 should be valid", ErrorHandler.validateModifier(1000))
    }
    
    @Test
    fun `validateModifier should return false for invalid modifiers`() {
        assertFalse("-1001 should be invalid", ErrorHandler.validateModifier(-1001))
        assertFalse("1001 should be invalid", ErrorHandler.validateModifier(1001))
    }
    
    @Test
    fun `validatePresetName should return true for valid names`() {
        assertTrue("Single character should be valid", ErrorHandler.validatePresetName("A"))
        assertTrue("Normal name should be valid", ErrorHandler.validatePresetName("Test Preset"))
        assertTrue("Name with spaces should be valid", ErrorHandler.validatePresetName("My Preset"))
        assertTrue("50 character name should be valid", ErrorHandler.validatePresetName("A".repeat(50)))
    }
    
    @Test
    fun `validatePresetName should return false for invalid names`() {
        assertFalse("Empty string should be invalid", ErrorHandler.validatePresetName(""))
        assertFalse("Whitespace only should be invalid", ErrorHandler.validatePresetName("   "))
        assertFalse("51 character name should be invalid", ErrorHandler.validatePresetName("A".repeat(51)))
    }
    
    @Test
    fun `validatePresetDescription should return true for valid descriptions`() {
        assertTrue("Empty description should be valid", ErrorHandler.validatePresetDescription(""))
        assertTrue("Normal description should be valid", ErrorHandler.validatePresetDescription("Test description"))
        assertTrue("200 character description should be valid", ErrorHandler.validatePresetDescription("A".repeat(200)))
    }
    
    @Test
    fun `validatePresetDescription should return false for invalid descriptions`() {
        assertFalse("201 character description should be invalid", ErrorHandler.validatePresetDescription("A".repeat(201)))
    }
    
    @Test
    fun `validatePresetName should handle edge cases`() {
        assertTrue("Single space should be valid", ErrorHandler.validatePresetName(" "))
        assertTrue("Special characters should be valid", ErrorHandler.validatePresetName("Preset-123_Test"))
        assertTrue("Unicode characters should be valid", ErrorHandler.validatePresetName("🎲 Dice Preset 🎯"))
    }
    
    @Test
    fun `validateModifier should handle edge cases`() {
        assertTrue("Boundary value -1000 should be valid", ErrorHandler.validateModifier(-1000))
        assertTrue("Boundary value 1000 should be valid", ErrorHandler.validateModifier(1000))
        assertFalse("Boundary value -1001 should be invalid", ErrorHandler.validateModifier(-1001))
        assertFalse("Boundary value 1001 should be invalid", ErrorHandler.validateModifier(1001))
    }
    
    @Test
    fun `validateDiceCount should handle edge cases`() {
        assertTrue("Boundary value 0 should be valid", ErrorHandler.validateDiceCount(0))
        assertTrue("Boundary value 100 should be valid", ErrorHandler.validateDiceCount(100))
        assertFalse("Boundary value -1 should be invalid", ErrorHandler.validateDiceCount(-1))
        assertFalse("Boundary value 101 should be invalid", ErrorHandler.validateDiceCount(101))
    }
} 