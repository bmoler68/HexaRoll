package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.ui.theme.ThemeColorUtils
import com.brianmoler.hexaroll.ui.theme.ColorType
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

/**
 * CustomizeScreen - Interface for theme customization
 * 
 * This screen allows users to:
 * - Select from available visual themes
 * - Preview theme styles in real-time
 * - Apply changes immediately
 * 
 * Features:
 * - Clean theme selection interface
 * - Real-time theme preview
 * - Consistent styling with current theme
 * - Input validation and error handling
 */
@Composable
fun CustomizeScreen(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val customization by viewModel.customization.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Screen title
        Text(
            text = "HEXAROLL THEME",
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Description
        Text(
            text = "Choose your preferred visual theme for the app",
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Theme selection list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AppTheme.entries) { theme ->
                ThemeSelectionCard(
                    theme = theme,
                    isSelected = theme == customization.theme,
                    onSelect = { viewModel.updateTheme(theme) },
                    displayTheme = customization.theme,
                    customization = customization
                )
            }
        }
    }
}

/**
 * Card component for theme selection
 * 
 * Displays theme information and allows selection with visual feedback.
 * Uses the centralized ThemeColorUtils for consistent styling.
 */
@Composable
private fun ThemeSelectionCard(
    theme: AppTheme,
    isSelected: Boolean,
    onSelect: () -> Unit,
    displayTheme: AppTheme,
    customization: DiceCustomization
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                ThemeColorUtils.getCardBackgroundColor(
                    displayTheme,
                    customization.backgroundEnabled,
                    customization.backgroundOpacity
                )
            } else {
                ThemeColorUtils.getThemeColor(displayTheme, ColorType.CARD_BACKGROUND).copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
            }
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) {
                ThemeColorUtils.getThemeColor(displayTheme, ColorType.NEON_YELLOW)
            } else {
                ThemeColorUtils.getThemeColor(displayTheme, ColorType.BORDER_BLUE)
            }
        )
    ) {
        Button(
            onClick = onSelect,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Theme name
                    Text(
                        text = getThemeName(theme),
                        color = ThemeColorUtils.getThemeColor(displayTheme, ColorType.NEON_YELLOW),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Theme description
                    Text(
                        text = getThemeDescription(theme),
                        color = ThemeColorUtils.getThemeColor(displayTheme, ColorType.SECONDARY_TEXT),
                        fontSize = 14.sp
                    )
                }
                
                // Selection indicator
                if (isSelected) {
                    Text(
                        text = "✓",
                        color = ThemeColorUtils.getThemeColor(displayTheme, ColorType.NEON_GREEN),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Get localized theme name
 */
@Composable
private fun getThemeName(theme: AppTheme): String {
    return when (theme) {
        AppTheme.CYBERPUNK -> stringResource(R.string.theme_cyberpunk)
        AppTheme.FANTASY -> stringResource(R.string.theme_fantasy)
        AppTheme.SCI_FI -> stringResource(R.string.theme_scifi)
        AppTheme.WESTERN -> stringResource(R.string.theme_western)
        AppTheme.ANCIENT -> stringResource(R.string.theme_ancient)
    }
}

/**
 * Get localized theme description
 */
@Composable
private fun getThemeDescription(theme: AppTheme): String {
    return when (theme) {
        AppTheme.CYBERPUNK -> stringResource(R.string.theme_description_cyberpunk)
        AppTheme.FANTASY -> stringResource(R.string.theme_description_fantasy)
        AppTheme.SCI_FI -> stringResource(R.string.theme_description_scifi)
        AppTheme.WESTERN -> stringResource(R.string.theme_description_western)
        AppTheme.ANCIENT -> stringResource(R.string.theme_description_ancient)
    }
}
