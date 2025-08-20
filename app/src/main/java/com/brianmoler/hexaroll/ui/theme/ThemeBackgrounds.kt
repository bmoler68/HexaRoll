package com.brianmoler.hexaroll.ui.theme

import androidx.annotation.DrawableRes
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.AppTheme

/**
 * ThemeBackgrounds - Utility object for managing theme-specific background resources
 * 
 * Provides centralized access to background images for each application theme.
 * This ensures consistent background resource management and makes it easy to
 * add or modify theme backgrounds in the future.
 * 
 * Now supports orientation-specific backgrounds for optimal display in both
 * portrait and landscape modes.
 */
object ThemeBackgrounds {
    /**
     * Get the background drawable resource for a specific theme
     * 
     * @param theme The application theme to get the background for
     * @return The drawable resource ID for the theme's background image
     */
    @DrawableRes
    fun getBackgroundResource(theme: AppTheme): Int {
        return when (theme) {
            AppTheme.CYBERPUNK -> R.drawable.bg_theme_cyberpunk
            AppTheme.FANTASY -> R.drawable.bg_theme_fantasy
            AppTheme.SCI_FI -> R.drawable.bg_theme_scifi
            AppTheme.WESTERN -> R.drawable.bg_theme_western
            AppTheme.ANCIENT -> R.drawable.bg_theme_ancient
        }
    }
    
    /**
     * Get the background drawable resource for a specific theme and orientation
     * 
     * @param theme The application theme to get the background for
     * @param isLandscape Whether the device is in landscape orientation
     * @return The drawable resource ID for the theme's background image (orientation-specific)
     */
    @DrawableRes
    fun getBackgroundResource(theme: AppTheme, isLandscape: Boolean): Int {
        return if (isLandscape) {
            when (theme) {
                AppTheme.CYBERPUNK -> R.drawable.bg_theme_cyberpunk_landscape
                AppTheme.FANTASY -> R.drawable.bg_theme_fantasy_landscape
                AppTheme.SCI_FI -> R.drawable.bg_theme_scifi_landscape
                AppTheme.WESTERN -> R.drawable.bg_theme_western_landscape
                AppTheme.ANCIENT -> R.drawable.bg_theme_ancient_landscape
            }
        } else {
            // Use portrait backgrounds (existing resources)
            getBackgroundResource(theme)
        }
    }
}
