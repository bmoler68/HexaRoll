package com.brianmoler.hexaroll.ui.theme

import androidx.compose.ui.graphics.Color
import com.brianmoler.hexaroll.data.AppTheme

/**
 * Centralized theme color utility for consistent theming across the app
 * 
 * This utility provides a single source of truth for theme-aware colors,
 * reducing code duplication and ensuring consistent styling.
 */
object ThemeColorUtils {
    
    /**
     * Get theme-aware color based on current theme and color type
     * 
     * @param theme The current application theme
     * @param colorType The type of color to retrieve
     * @return The appropriate Color for the theme and type
     */
    fun getThemeColor(theme: AppTheme, colorType: ColorType): Color {
        return when (theme) {
            AppTheme.CYBERPUNK -> getCyberpunkColor(colorType)
            AppTheme.FANTASY -> getFantasyColor(colorType)
            AppTheme.SCI_FI -> getSciFiColor(colorType)
            AppTheme.WESTERN -> getWesternColor(colorType)
            AppTheme.ANCIENT -> getAncientColor(colorType)
        }
    }
    
    private fun getCyberpunkColor(colorType: ColorType): Color {
        return when (colorType) {
            ColorType.PRIMARY_TEXT -> CyberpunkColors.PrimaryText
            ColorType.SECONDARY_TEXT -> CyberpunkColors.SecondaryText
            ColorType.CARD_BACKGROUND -> CyberpunkColors.CardBackground
            ColorType.ELEVATED_CARD_BACKGROUND -> CyberpunkColors.ElevatedCardBackground
            ColorType.BORDER_BLUE -> CyberpunkColors.BorderBlue
            ColorType.NEON_YELLOW -> CyberpunkColors.NeonYellow
            ColorType.NEON_BLUE -> CyberpunkColors.NeonBlue
            ColorType.NEON_GREEN -> CyberpunkColors.NeonGreen
            ColorType.NEON_RED -> CyberpunkColors.NeonRed
            ColorType.BUTTON_GREEN -> CyberpunkColors.ButtonGreen
            ColorType.BUTTON_RED -> CyberpunkColors.ButtonRed
        }
    }
    
    private fun getFantasyColor(colorType: ColorType): Color {
        return when (colorType) {
            ColorType.PRIMARY_TEXT -> FantasyColors.PrimaryText
            ColorType.SECONDARY_TEXT -> FantasyColors.SecondaryText
            ColorType.CARD_BACKGROUND -> FantasyColors.CardBackground
            ColorType.ELEVATED_CARD_BACKGROUND -> FantasyColors.ElevatedCardBackground
            ColorType.BORDER_BLUE -> FantasyColors.BorderBlue
            ColorType.NEON_YELLOW -> FantasyColors.NeonYellow
            ColorType.NEON_BLUE -> FantasyColors.NeonBlue
            ColorType.NEON_GREEN -> FantasyColors.NeonGreen
            ColorType.NEON_RED -> FantasyColors.NeonRed
            ColorType.BUTTON_GREEN -> FantasyColors.ButtonGreen
            ColorType.BUTTON_RED -> FantasyColors.ButtonRed
        }
    }
    
    private fun getSciFiColor(colorType: ColorType): Color {
        return when (colorType) {
            ColorType.PRIMARY_TEXT -> SciFiColors.PrimaryText
            ColorType.SECONDARY_TEXT -> SciFiColors.SecondaryText
            ColorType.CARD_BACKGROUND -> SciFiColors.CardBackground
            ColorType.ELEVATED_CARD_BACKGROUND -> SciFiColors.ElevatedCardBackground
            ColorType.BORDER_BLUE -> SciFiColors.BorderBlue
            ColorType.NEON_YELLOW -> SciFiColors.NeonYellow
            ColorType.NEON_BLUE -> SciFiColors.NeonBlue
            ColorType.NEON_GREEN -> SciFiColors.NeonGreen
            ColorType.NEON_RED -> SciFiColors.NeonRed
            ColorType.BUTTON_GREEN -> SciFiColors.ButtonGreen
            ColorType.BUTTON_RED -> SciFiColors.ButtonRed
        }
    }
    
    private fun getWesternColor(colorType: ColorType): Color {
        return when (colorType) {
            ColorType.PRIMARY_TEXT -> WesternColors.PrimaryText
            ColorType.SECONDARY_TEXT -> WesternColors.SecondaryText
            ColorType.CARD_BACKGROUND -> WesternColors.CardBackground
            ColorType.ELEVATED_CARD_BACKGROUND -> WesternColors.ElevatedCardBackground
            ColorType.BORDER_BLUE -> WesternColors.BorderBlue
            ColorType.NEON_YELLOW -> WesternColors.NeonYellow
            ColorType.NEON_BLUE -> WesternColors.NeonBlue
            ColorType.NEON_GREEN -> WesternColors.NeonGreen
            ColorType.NEON_RED -> WesternColors.NeonRed
            ColorType.BUTTON_GREEN -> WesternColors.ButtonGreen
            ColorType.BUTTON_RED -> WesternColors.ButtonRed
        }
    }
    
    private fun getAncientColor(colorType: ColorType): Color {
        return when (colorType) {
            ColorType.PRIMARY_TEXT -> AncientColors.PrimaryText
            ColorType.SECONDARY_TEXT -> AncientColors.SecondaryText
            ColorType.CARD_BACKGROUND -> AncientColors.CardBackground
            ColorType.ELEVATED_CARD_BACKGROUND -> AncientColors.ElevatedCardBackground
            ColorType.BORDER_BLUE -> AncientColors.BorderBlue
            ColorType.NEON_YELLOW -> AncientColors.NeonYellow
            ColorType.NEON_BLUE -> AncientColors.NeonBlue
            ColorType.NEON_GREEN -> AncientColors.NeonGreen
            ColorType.NEON_RED -> AncientColors.NeonRed
            ColorType.BUTTON_GREEN -> AncientColors.ButtonGreen
            ColorType.BUTTON_RED -> AncientColors.ButtonRed
        }
    }
    
    /**
     * Get background color with opacity based on customization settings
     */
    fun getBackgroundColor(theme: AppTheme, backgroundEnabled: Boolean, backgroundOpacity: Float): Color {
        val baseColor = getThemeColor(theme, ColorType.CARD_BACKGROUND)
        return if (backgroundEnabled) {
            baseColor.copy(alpha = (1.0f - backgroundOpacity * 0.9f).coerceAtLeast(0.0f))
        } else {
            baseColor
        }
    }
    
    /**
     * Get card background color with opacity based on customization settings
     */
    fun getCardBackgroundColor(theme: AppTheme, backgroundEnabled: Boolean, backgroundOpacity: Float): Color {
        val baseColor = getThemeColor(theme, ColorType.ELEVATED_CARD_BACKGROUND)
        return if (backgroundEnabled) {
            baseColor.copy(alpha = (1.0f - backgroundOpacity * 0.7f).coerceAtLeast(0.2f))
        } else {
            baseColor
        }
    }
}

/**
 * Enum for different color types used throughout the app
 */
enum class ColorType {
    PRIMARY_TEXT,
    SECONDARY_TEXT,
    CARD_BACKGROUND,
    ELEVATED_CARD_BACKGROUND,
    BORDER_BLUE,
    NEON_YELLOW,
    NEON_BLUE,
    NEON_GREEN,
    NEON_RED,
    BUTTON_GREEN,
    BUTTON_RED
}
