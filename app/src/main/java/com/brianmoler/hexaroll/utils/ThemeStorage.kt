package com.brianmoler.hexaroll.utils

import android.content.Context
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
        private const val KEY_CUSTOMIZATION = "dice_customization"
    }
    
    /**
     * Save complete customization settings including theme and background preferences
     */
    fun saveCustomization(customization: DiceCustomization) {
        val customizationJson = gson.toJson(customization)
        sharedPreferences.edit {
            putString(KEY_CUSTOMIZATION, customizationJson)
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
                // JSON is corrupted, fall back to data class defaults
            }
        }
        
        // No customization data or corrupted data - use data class defaults
        return DiceCustomization()
        // All properties will use AppDefaultsData values from data class defaults
    }
} 