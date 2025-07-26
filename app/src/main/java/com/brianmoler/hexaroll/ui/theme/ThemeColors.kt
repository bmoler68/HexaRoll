package com.brianmoler.hexaroll.ui.theme

import androidx.compose.ui.graphics.Color
import com.brianmoler.hexaroll.data.AppTheme

// Cyberpunk Theme Colors - Vibrant neon, high contrast
object CyberpunkColors {
    val PrimaryText = Color(0xFFE0E0E0) // Bright white
    val SecondaryText = Color(0xFFB0B0B0) // Light gray
    val CardBackground = Color(0xFF1A1A2E) // Deep blue-black
    val ElevatedCardBackground = Color(0xFF16213E) // Dark blue-gray
    val BorderBlue = Color(0xFF0F3460) // Deep blue
    val NeonYellow = Color(0xFFFFD700) // Bright neon yellow
    val NeonBlue = Color(0xFF00FFFF) // Electric cyan
    val NeonGreen = Color(0xFF00FF00) // Bright neon green
    val NeonRed = Color(0xFFFF0000) // Bright neon red
    val NeonCyan = Color(0xFF00FFFF) // Electric cyan
    val NeonPurple = Color(0xFF8000FF) // Bright neon purple
    val ButtonRed = Color(0xFF8B0000) // Dark red
    val ButtonGreen = Color(0xFF006400) // Dark green
}

// Fantasy Theme Colors - Warm, earthy, magical
object FantasyColors {
    val PrimaryText = Color(0xFFF5E6D3) // Warm cream
    val SecondaryText = Color(0xFFD4C4A8) // Aged parchment
    val CardBackground = Color(0xFF2D1810) // Deep brown wood
    val ElevatedCardBackground = Color(0xFF3D2415) // Rich mahogany
    val BorderBlue = Color(0xFF8B4513) // Saddle brown
    val NeonYellow = Color(0xFFDAA520) // Goldenrod (magical gold)
    val NeonBlue = Color(0xFF4169E1) // Royal blue (mystical)
    val NeonGreen = Color(0xFF228B22) // Forest green (nature)
    val NeonRed = Color(0xFFDC143C) // Crimson (dragon fire)
    val NeonCyan = Color(0xFF40E0D0) // Turquoise (magical)
    val NeonPurple = Color(0xFF9370DB) // Medium purple (enchanted)
    val ButtonRed = Color(0xFF8B0000) // Dark red (danger)
    val ButtonGreen = Color(0xFF006400) // Dark green (safe)
}

// SCI-FI Theme Colors - Cool, futuristic, high-tech
object SciFiColors {
    val PrimaryText = Color(0xFFE6F3FF) // Bright white-blue
    val SecondaryText = Color(0xFFB8D4E3) // Cool gray-blue
    val CardBackground = Color(0xFF0A0A0A) // Deep space black
    val ElevatedCardBackground = Color(0xFF1A1A1A) // Dark metal
    val BorderBlue = Color(0xFF0066CC) // Electric blue
    val NeonYellow = Color(0xFFFFD700) // Bright yellow (warning)
    val NeonBlue = Color(0xFF00BFFF) // Deep sky blue (technology)
    val NeonGreen = Color(0xFF00FF7F) // Spring green (success)
    val NeonRed = Color(0xFFFF4444) // Bright red (error)
    val NeonCyan = Color(0xFF00FFFF) // Cyan (holographic)
    val NeonPurple = Color(0xFF9932CC) // Dark orchid (quantum)
    val ButtonRed = Color(0xFF8B0000) // Dark red (danger)
    val ButtonGreen = Color(0xFF006400) // Dark green (safe)
}

// Theme Color Manager
object ThemeColors {
    fun getColors(theme: AppTheme) = when (theme) {
        AppTheme.CYBERPUNK -> CyberpunkColors
        AppTheme.FANTASY -> FantasyColors
        AppTheme.SCI_FI -> SciFiColors
    }
} 