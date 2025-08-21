package com.brianmoler.hexaroll.utils

import android.content.Context
import com.brianmoler.hexaroll.data.DiceCustomization
import com.google.gson.Gson
import androidx.core.content.edit

/**
 * ThemeStorage - Handles persistence of user customization preferences
 * 
 * This utility class manages all user interface customization settings including:
 * - Visual themes (CYBERPUNK, FANTASY, SCI_FI, WESTERN, ANCIENT)
 * - Background display preferences (enabled/disabled, opacity, scaling mode)
 * - Complete DiceCustomization object serialization using Gson
 * 
 * Features:
 * - JSON-based storage for complex object persistence
 * - Automatic fallback to AppDefaultsData defaults for corrupted or missing data
 * - SharedPreferences integration for lightweight, persistent storage
 * - Handles schema evolution gracefully through data class defaults
 * 
 * Storage Format:
 * - Uses Gson to serialize DiceCustomization as JSON string
 * - Single key storage for complete customization object
 * - Automatic type safety through Kotlin data classes
 */
class ThemeStorage(context: Context) {
    
    /** SharedPreferences instance for persistent storage */
    private val sharedPreferences = context.getSharedPreferences(
        "theme_preferences",
        Context.MODE_PRIVATE
    )
    
    /** Gson instance for JSON serialization/deserialization */
    private val gson = Gson()
    
    companion object {
        /** Storage key for the complete DiceCustomization object */
        private const val KEY_CUSTOMIZATION = "dice_customization"
    }
    
    /**
     * Save complete customization settings including theme and background preferences
     * 
     * Serializes the entire DiceCustomization object to JSON and stores it in
     * SharedPreferences. This method handles all customization properties:
     * - theme: Current visual theme (AppTheme enum)
     * - backgroundEnabled: Whether theme backgrounds are displayed
     * - backgroundOpacity: Background transparency level (0.0f - 1.0f)
     * - backgroundScaling: How backgrounds are scaled (BackgroundFitMode enum)
     * 
     * @param customization The complete DiceCustomization object to persist
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
     * Attempts to deserialize the stored DiceCustomization JSON and return the complete
     * customization object. Includes robust error handling for data corruption scenarios.
     * 
     * Fallback Strategy:
     * 1. Try to load and deserialize stored JSON from SharedPreferences
     * 2. If JSON is corrupted or invalid, fall back to DiceCustomization() defaults
     * 3. All default values come from AppDefaultsData constants via data class defaults
     * 
     * Default Values (from AppDefaultsData):
     * - theme: AppTheme.FANTASY (default app theme)
     * - backgroundEnabled: true (immersive visual experience)
     * - backgroundOpacity: 1.0f (full vivid display)
     * - backgroundScaling: BackgroundFitMode.STRETCH (optimal landscape support)
     * 
     * @return Complete DiceCustomization object with all settings loaded or defaulted
     */
    fun loadCustomization(): DiceCustomization {
        // Try to load stored customization JSON
        val customizationJson = sharedPreferences.getString(KEY_CUSTOMIZATION, null)
        if (customizationJson != null) {
            try {
                return gson.fromJson(customizationJson, DiceCustomization::class.java)
            } catch (e: Exception) {
                // JSON is corrupted or invalid, fall back to defaults
                // Log could be added here for debugging if needed
            }
        }
        
        // No stored data or corrupted data - return new instance with AppDefaultsData defaults
        return DiceCustomization()
    }
} 