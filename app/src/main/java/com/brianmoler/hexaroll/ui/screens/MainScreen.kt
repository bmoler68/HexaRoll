package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.ui.components.AchievementNotification
import com.brianmoler.hexaroll.ui.components.ThemedBackground
import com.brianmoler.hexaroll.ui.theme.ThemeColorUtils
import com.brianmoler.hexaroll.ui.theme.ColorType
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

/**
 * MainScreen - Primary navigation container for the HexaRoll application
 * 
 * This refactored MainScreen provides:
 * - Clean separation of concerns with individual screen files
 * - Centralized navigation management
 * - Consistent theming with ThemeColorUtils
 * - Improved maintainability and testability
 * 
 * Architecture improvements:
 * - Screen logic moved to dedicated files
 * - Reduced code duplication
 * - Better performance through optimized rendering
 * - Enhanced security through input validation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: DiceRollViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val customization by viewModel.customization.collectAsState()
    val newlyUnlockedAchievements by viewModel.newlyUnlockedAchievements.collectAsState()
    
    val tabs = listOf(
        TabItem(Icons.Filled.PlayArrow, stringResource(R.string.tab_dice_arena)),
        TabItem(Icons.Filled.Edit, stringResource(R.string.tab_customization)),
        TabItem(Icons.Filled.Favorite, stringResource(R.string.tab_presets)),
        TabItem(Icons.AutoMirrored.Filled.List, stringResource(R.string.tab_history)),
        TabItem(Icons.Filled.Star, stringResource(R.string.tab_achievements)),
        TabItem(Icons.Filled.Settings, stringResource(R.string.tab_settings))
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                ThemeColorUtils.getBackgroundColor(
                    customization.theme,
                    customization.backgroundEnabled,
                    customization.backgroundOpacity
                )
            )
    ) {
        // App Title
        AppTitle(customization.theme)

        // Tab Row Navigation
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(
                ThemeColorUtils.getThemeColor(customization.theme, ColorType.ELEVATED_CARD_BACKGROUND)
            ),
            containerColor = Color.Transparent,
            contentColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT)
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (selectedTabIndex == index) {
                                ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW)
                            } else {
                                ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE)
                            }
                        )
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        
        // Content based on selected tab with themed background
        ThemedBackground(
            theme = customization.theme,
            alpha = if (customization.backgroundEnabled) customization.backgroundOpacity else 0f,
            fitMode = customization.backgroundScaling,
            modifier = Modifier.weight(1f).fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        ThemeColorUtils.getBackgroundColor(
                            customization.theme,
                            customization.backgroundEnabled,
                            customization.backgroundOpacity
                        ).copy(
                            alpha = if (customization.backgroundEnabled) {
                                (1.0f - customization.backgroundOpacity * 0.75f).coerceAtLeast(0.1f)
                            } else {
                                1.0f
                            }
                        )
                    )
            ) {
                // Navigate to appropriate screen
                when (selectedTabIndex) {
                    0 -> RollScreen(viewModel)
                    1 -> CustomizeScreen(viewModel)
                    2 -> PresetsScreen(viewModel)
                    3 -> HistoryScreen(viewModel)
                    4 -> AchievementScreen(viewModel)
                    5 -> SettingsScreen(viewModel)
                }
            }
        }
        
        // Achievement popup overlay
        AchievementNotification(
            achievements = newlyUnlockedAchievements,
            onDismiss = { /* Achievement popup will auto-dismiss */ },
            theme = customization.theme,
            customization = customization
        )
    }
}

/**
 * Data class for tab items
 */
data class TabItem(
    val icon: ImageVector,
    val label: String
)

/**
 * App title component with consistent theming
 */
@Composable
private fun AppTitle(theme: com.brianmoler.hexaroll.data.AppTheme) {
    Text(
        text = "HEXAROLL",
        color = ThemeColorUtils.getThemeColor(theme, ColorType.NEON_YELLOW),
        fontSize = 32.sp,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                ThemeColorUtils.getThemeColor(theme, ColorType.ELEVATED_CARD_BACKGROUND)
            )
            .padding(16.dp)
            .padding(vertical = 8.dp)
    )
}
