package com.brianmoler.hexaroll.utils

import android.content.Context
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
                AppTheme.CYBERPUNK // Default fallback
            }
        } else {
            AppTheme.CYBERPUNK // Default theme
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
     * Load complete customization settings with backward compatibility
     * Falls back to theme-only if customization data is not available
     */
    fun loadCustomization(): DiceCustomization {
        // Try to load full customization first
        val customizationJson = sharedPreferences.getString(KEY_CUSTOMIZATION, null)
        if (customizationJson != null) {
            try {
                return gson.fromJson(customizationJson, DiceCustomization::class.java)
            } catch (e: Exception) {
                // Fall through to backward compatibility
            }
        }
        
        // Backward compatibility: load theme-only and create customization with defaults
        val theme = loadTheme()
        return DiceCustomization(
            theme = theme,
            backgroundEnabled = true,
            backgroundOpacity = 0.3f
        )
    }
} 