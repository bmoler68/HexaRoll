package com.brianmoler.hexaroll.data

import com.brianmoler.hexaroll.ui.components.BackgroundFitMode

/**
 * AppDefaultsData - Central storage for application-wide default values
 * 
 * Contains all app-wide default configuration values in a centralized location
 * for easy maintenance and consistency throughout the application.
 * This ensures that default values are consistent across data models, UI components,
 * and storage fallbacks.
 */
object AppDefaultsData {
    
    /**
     * Default theme background settings
     * 
     * These values define the standard background behavior for the application:
     * - Always enabled for immersive visual experience
     * - Full opacity for vivid theme display
     * - Stretch scaling for optimal landscape background support
     */
    object Background {
        /** Whether theme backgrounds are enabled by default */
        const val ENABLED = true
        
        /** Default background opacity (1.0f = 100% opacity for full vivid display) */
        const val OPACITY = 1.0f
        
        /** Default background scaling mode (STRETCH for optimal landscape support) */
        val SCALING_MODE = BackgroundFitMode.STRETCH
        
        /** Alpha value for overlay to ensure text readability over backgrounds */
        const val OVERLAY_ALPHA = 0.25f
    }
    
    /**
     * Default theme settings
     */
    object Theme {
        /** Default application theme */
        val DEFAULT_THEME = AppTheme.FANTASY
    }
    
    /**
     * Business logic constants
     */
    object Data {
        /** Maximum number of roll history entries to maintain */
        const val MAX_ROLL_HISTORY = 100
    }
    
    // Future app-wide defaults can be added here:
    // object Dice { ... }
    // object UI { ... }
    // object Audio { ... }
}
