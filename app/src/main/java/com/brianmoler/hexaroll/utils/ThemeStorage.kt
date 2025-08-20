package com.brianmoler.hexaroll.utils

import android.content.Context
import com.brianmoler.hexaroll.data.AppDefaultsData
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceCustomization
import com.google.gson.Gson
import androidx.core.content.edit

class ThemeStorage(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(
        "theme_preferences",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    
    companion object {
        private const val KEY_SELECTED_THEME = "selected_theme"
        private const val KEY_CUSTOMIZATION = "dice_customization"
    }

    fun loadTheme(): AppTheme {
        val themeJson = sharedPreferences.getString(KEY_SELECTED_THEME, null)
        return if (themeJson != null) {
            try {
                gson.fromJson(themeJson, AppTheme::class.java)
            } catch (e: Exception) {
                AppDefaultsData.Theme.DEFAULT_THEME // Centralized default fallback
            }
        } else {
            AppDefaultsData.Theme.DEFAULT_THEME // Centralized default theme
        }
    }
    
    /**
     * Save complete customization settings including theme and background preferences
     */
    fun saveCustomization(customization: DiceCustomization) {
        val customizationJson = gson.toJson(customization)
        sharedPreferences.edit {
            putString(KEY_CUSTOMIZATION, customizationJson)
            // Also save theme separately for backward compatibility
            putString(KEY_SELECTED_THEME, gson.toJson(customization.theme))
        }
    }
    
    /**
     * Load complete customization settings with automatic defaults
     * 
     * Uses Gson deserialization with automatic fallback to data class defaults
     * for any missing or corrupted fields. This eliminates the need for manual
     * fallback construction since DiceCustomization default parameters handle
     * missing values automatically.
     */
    fun loadCustomization(): DiceCustomization {
        // Try to load full customization first
        val customizationJson = sharedPreferences.getString(KEY_CUSTOMIZATION, null)
        if (customizationJson != null) {
            try {
                return gson.fromJson(customizationJson, DiceCustomization::class.java)
            } catch (e: Exception) {
                // JSON is corrupted, fall back to theme-only with data class defaults
            }
        }
        
        // No customization data or corrupted data - use theme with data class defaults
        val theme = loadTheme()
        return DiceCustomization(theme = theme)
        // backgroundEnabled, backgroundOpacity, backgroundScaling will use AppDefaultsData values
    }
} 