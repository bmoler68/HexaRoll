package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.*
import com.brianmoler.hexaroll.ui.components.AchievementNotification
import com.brianmoler.hexaroll.ui.components.DiceArena
import com.brianmoler.hexaroll.ui.theme.*
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

// Helper function for theme-aware colors
private fun getThemeColor(theme: AppTheme, colorType: String): Color {
    return when (theme) {
        AppTheme.CYBERPUNK -> when (colorType) {
            "PrimaryText" -> CyberpunkColors.PrimaryText
            "SecondaryText" -> CyberpunkColors.SecondaryText
            "CardBackground" -> CyberpunkColors.CardBackground
            "ElevatedCardBackground" -> CyberpunkColors.ElevatedCardBackground
            "BorderBlue" -> CyberpunkColors.BorderBlue
            "NeonYellow" -> CyberpunkColors.NeonYellow
            "NeonBlue" -> CyberpunkColors.NeonBlue
            "NeonGreen" -> CyberpunkColors.NeonGreen
            "NeonRed" -> CyberpunkColors.NeonRed
            "ButtonGreen" -> CyberpunkColors.ButtonGreen
            "ButtonRed" -> CyberpunkColors.ButtonRed
            else -> CyberpunkColors.PrimaryText
        }
        AppTheme.FANTASY -> when (colorType) {
            "PrimaryText" -> FantasyColors.PrimaryText
            "SecondaryText" -> FantasyColors.SecondaryText
            "CardBackground" -> FantasyColors.CardBackground
            "ElevatedCardBackground" -> FantasyColors.ElevatedCardBackground
            "BorderBlue" -> FantasyColors.BorderBlue
            "NeonYellow" -> FantasyColors.NeonYellow
            "NeonBlue" -> FantasyColors.NeonBlue
            "NeonGreen" -> FantasyColors.NeonGreen
            "NeonRed" -> FantasyColors.NeonRed
            "ButtonGreen" -> FantasyColors.ButtonGreen
            "ButtonRed" -> FantasyColors.ButtonRed
            else -> FantasyColors.PrimaryText
        }
        AppTheme.SCI_FI -> when (colorType) {
            "PrimaryText" -> SciFiColors.PrimaryText
            "SecondaryText" -> SciFiColors.SecondaryText
            "CardBackground" -> SciFiColors.CardBackground
            "ElevatedCardBackground" -> SciFiColors.ElevatedCardBackground
            "BorderBlue" -> SciFiColors.BorderBlue
            "NeonYellow" -> SciFiColors.NeonYellow
            "NeonBlue" -> SciFiColors.NeonBlue
            "NeonGreen" -> SciFiColors.NeonGreen
            "NeonRed" -> SciFiColors.NeonRed
            "ButtonGreen" -> SciFiColors.ButtonGreen
            "ButtonRed" -> SciFiColors.ButtonRed
            else -> SciFiColors.PrimaryText
        }
        AppTheme.WESTERN -> when (colorType) {
            "PrimaryText" -> WesternColors.PrimaryText
            "SecondaryText" -> WesternColors.SecondaryText
            "CardBackground" -> WesternColors.CardBackground
            "ElevatedCardBackground" -> WesternColors.ElevatedCardBackground
            "BorderBlue" -> WesternColors.BorderBlue
            "NeonYellow" -> WesternColors.NeonYellow
            "NeonBlue" -> WesternColors.NeonBlue
            "NeonGreen" -> WesternColors.NeonGreen
            "NeonRed" -> WesternColors.NeonRed
            "ButtonGreen" -> WesternColors.ButtonGreen
            "ButtonRed" -> WesternColors.ButtonRed
            else -> WesternColors.PrimaryText
        }
        AppTheme.ANCIENT -> when (colorType) {
            "PrimaryText" -> AncientColors.PrimaryText
            "SecondaryText" -> AncientColors.SecondaryText
            "CardBackground" -> AncientColors.CardBackground
            "ElevatedCardBackground" -> AncientColors.ElevatedCardBackground
            "BorderBlue" -> AncientColors.BorderBlue
            "NeonYellow" -> AncientColors.NeonYellow
            "NeonBlue" -> AncientColors.NeonBlue
            "NeonGreen" -> AncientColors.NeonGreen
            "NeonRed" -> AncientColors.NeonRed
            "ButtonGreen" -> AncientColors.ButtonGreen
            "ButtonRed" -> AncientColors.ButtonRed
            else -> AncientColors.PrimaryText
        }
    }
}

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
        TabItem(Icons.Filled.List, stringResource(R.string.tab_history)),
        TabItem(Icons.Filled.Star, stringResource(R.string.tab_achievements))
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                AppTheme.FANTASY -> FantasyColors.CardBackground
                AppTheme.SCI_FI -> SciFiColors.CardBackground
                AppTheme.WESTERN -> WesternColors.CardBackground
                AppTheme.ANCIENT -> AncientColors.CardBackground
            })
    ) {
        // App Title
        AppTitle(theme = customization.theme)

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            }),
            containerColor = Color.Transparent,
            contentColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                AppTheme.FANTASY -> FantasyColors.PrimaryText
                AppTheme.SCI_FI -> SciFiColors.PrimaryText
                AppTheme.WESTERN -> WesternColors.PrimaryText
                AppTheme.ANCIENT -> AncientColors.PrimaryText
            }
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
                                when (customization.theme) {
                                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                                    AppTheme.WESTERN -> WesternColors.NeonYellow
                                    AppTheme.ANCIENT -> AncientColors.NeonYellow
                                }
                            } else {
                                when (customization.theme) {
                                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                                    AppTheme.FANTASY -> FantasyColors.NeonBlue
                                    AppTheme.SCI_FI -> SciFiColors.NeonBlue
                                    AppTheme.WESTERN -> WesternColors.NeonBlue
                                    AppTheme.ANCIENT -> AncientColors.NeonBlue
                                }
                            },
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        
        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> RollScreen(viewModel)
            1 -> CustomizeScreen(viewModel)
            2 -> PresetsScreen(viewModel)
            3 -> HistoryScreen(viewModel)
            4 -> AchievementScreen(viewModel)
        }
        
        // Achievement popup
        AchievementNotification(
            achievements = newlyUnlockedAchievements,
            onDismiss = { /* Achievement popup will auto-dismiss */ },
            theme = customization.theme
        )
    }
}

data class TabItem(
    val icon: ImageVector,
    val label: String
)

@Composable
fun RollScreen(viewModel: DiceRollViewModel) {
    val presetLoadedMessage by viewModel.presetLoadedMessage.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Preset loaded message
        presetLoadedMessage?.let { message ->
            PresetLoadedNotification(
                message = message,
                onDismiss = { viewModel.clearPresetLoadedMessage() }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        DiceArena(viewModel)
    }
}

@Composable
fun CustomizeScreen(viewModel: DiceRollViewModel) {
    val customization by viewModel.customization.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "HEXAROLL THEME",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Choose your preferred visual theme for the app",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                AppTheme.FANTASY -> FantasyColors.SecondaryText
                AppTheme.SCI_FI -> SciFiColors.SecondaryText
                AppTheme.WESTERN -> WesternColors.SecondaryText
                AppTheme.ANCIENT -> AncientColors.SecondaryText
            },
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(AppTheme.entries) { theme ->
                ThemeSelectionCard(
                    theme = theme,
                    isSelected = theme == customization.theme,
                    onSelect = { viewModel.updateTheme(theme) },
                    themeType = customization.theme
                )
            }
        }
    }
}

@Composable
fun ThemeSelectionCard(
    theme: AppTheme,
    isSelected: Boolean,
    onSelect: () -> Unit,
    themeType: AppTheme
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                when (themeType) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                    AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                    AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                    AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                    AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
                }
            } else {
                when (themeType) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                    AppTheme.FANTASY -> FantasyColors.CardBackground
                    AppTheme.SCI_FI -> SciFiColors.CardBackground
                    AppTheme.WESTERN -> WesternColors.CardBackground
                    AppTheme.ANCIENT -> AncientColors.CardBackground
                }
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = when (themeType) {
                AppTheme.CYBERPUNK -> if (isSelected) CyberpunkColors.NeonYellow else CyberpunkColors.BorderBlue
                AppTheme.FANTASY -> if (isSelected) FantasyColors.NeonYellow else FantasyColors.BorderBlue
                AppTheme.SCI_FI -> if (isSelected) SciFiColors.NeonYellow else SciFiColors.BorderBlue
                AppTheme.WESTERN -> if (isSelected) WesternColors.NeonYellow else WesternColors.BorderBlue
                AppTheme.ANCIENT -> if (isSelected) AncientColors.NeonYellow else AncientColors.BorderBlue
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
                    Text(
                        text = when (theme) {
                            AppTheme.CYBERPUNK -> stringResource(R.string.theme_cyberpunk)
                            AppTheme.FANTASY -> stringResource(R.string.theme_fantasy)
                            AppTheme.SCI_FI -> stringResource(R.string.theme_scifi)
                            AppTheme.WESTERN -> stringResource(R.string.theme_western)
                            AppTheme.ANCIENT -> stringResource(R.string.theme_ancient)
                        },
                        color = when (themeType) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                            AppTheme.FANTASY -> FantasyColors.NeonYellow
                            AppTheme.SCI_FI -> SciFiColors.NeonYellow
                            AppTheme.WESTERN -> WesternColors.NeonYellow
                            AppTheme.ANCIENT -> AncientColors.NeonYellow
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = when (theme) {
                            AppTheme.CYBERPUNK -> stringResource(R.string.theme_description_cyberpunk)
                            AppTheme.FANTASY -> stringResource(R.string.theme_description_fantasy)
                            AppTheme.SCI_FI -> stringResource(R.string.theme_description_scifi)
                            AppTheme.WESTERN -> stringResource(R.string.theme_description_western)
                            AppTheme.ANCIENT -> stringResource(R.string.theme_description_ancient)
                        },
                        color = when (themeType) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
                            AppTheme.ANCIENT -> AncientColors.SecondaryText
                        },
                        fontSize = 14.sp
                    )
                }
                
                if (isSelected) {
                    Text(
                        text = "✓",
                        color = when (themeType) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                            AppTheme.FANTASY -> FantasyColors.NeonGreen
                            AppTheme.SCI_FI -> SciFiColors.NeonGreen
                            AppTheme.WESTERN -> WesternColors.NeonGreen
                            AppTheme.ANCIENT -> AncientColors.NeonGreen
                        },
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun PresetsScreen(viewModel: DiceRollViewModel) {
    val presetRolls by viewModel.presetRolls.collectAsState()
    val presetLoadedMessage by viewModel.presetLoadedMessage.collectAsState()
    val customization by viewModel.customization.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.tab_presets),
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Preset loaded message
        presetLoadedMessage?.let { message ->
            PresetLoadedNotification(
                message = message,
                onDismiss = { viewModel.clearPresetLoadedMessage() },
                theme = customization.theme
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        if (presetRolls.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.no_presets_available),
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Create favorites by rolling dice and using the 'Save to Favorites' button in the History tab",
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(presetRolls) { preset ->
                    PresetCard(
                        preset = preset,
                        onLoad = { viewModel.loadPresetRoll(preset) },
                        onEdit = { name, description -> viewModel.updatePreset(preset.id, name, description) },
                        onRemove = { viewModel.removePreset(preset.id) },
                        theme = customization.theme
                    )
                }
            }
        }
    }
}

@Composable
fun PresetCard(
    preset: PresetRoll,
    onLoad: () -> Unit,
    onEdit: (String, String) -> Unit,
    onRemove: () -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                AppTheme.FANTASY -> FantasyColors.BorderBlue
                AppTheme.SCI_FI -> SciFiColors.BorderBlue
                AppTheme.WESTERN -> WesternColors.BorderBlue
                AppTheme.ANCIENT -> AncientColors.BorderBlue
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with title and action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = preset.name,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                        AppTheme.FANTASY -> FantasyColors.NeonYellow
                        AppTheme.SCI_FI -> SciFiColors.NeonYellow
                        AppTheme.WESTERN -> WesternColors.NeonYellow
                        AppTheme.ANCIENT -> AncientColors.NeonYellow
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Edit button
                    Button(
                        onClick = { showEditDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                                AppTheme.FANTASY -> FantasyColors.NeonBlue
                                AppTheme.SCI_FI -> SciFiColors.NeonBlue
                                AppTheme.WESTERN -> WesternColors.NeonBlue
                                AppTheme.ANCIENT -> AncientColors.NeonBlue
                            }
                        ),
                        modifier = Modifier.size(32.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "✏",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    
                    // Delete button
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                                AppTheme.FANTASY -> FantasyColors.ButtonRed
                                AppTheme.SCI_FI -> SciFiColors.ButtonRed
                                AppTheme.WESTERN -> WesternColors.ButtonRed
                                AppTheme.ANCIENT -> AncientColors.ButtonRed
                            }
                        ),
                        modifier = Modifier.size(32.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "×",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
            
            // Description
            Text(
                text = preset.description,
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                },
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Dice notation
            Text(
                text = buildPresetNotation(preset),
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                    AppTheme.FANTASY -> FantasyColors.NeonBlue
                    AppTheme.SCI_FI -> SciFiColors.NeonBlue
                    AppTheme.WESTERN -> WesternColors.NeonBlue
                    AppTheme.ANCIENT -> AncientColors.NeonBlue
                },
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            // Load button
            Button(
                onClick = onLoad,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                        AppTheme.FANTASY -> FantasyColors.ButtonGreen
                        AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                        AppTheme.WESTERN -> WesternColors.ButtonGreen
                        AppTheme.ANCIENT -> AncientColors.ButtonGreen
                    }
                )
            ) {
                Text(
                    text = "Load Favorite",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
    
    // Edit Dialog
    if (showEditDialog) {
        EditPresetDialog(
            preset = preset,
            onDismiss = { showEditDialog = false },
            onSave = { name, description ->
                onEdit(name, description)
                showEditDialog = false
            },
            theme = theme
        )
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        DeletePresetDialog(
            presetName = preset.name,
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                onRemove()
                showDeleteDialog = false
            },
            theme = theme
        )
    }
}

private fun buildPresetNotation(preset: PresetRoll): String {
    val diceNotation = preset.diceSelections
        .filter { it.count > 0 }
        .joinToString("+") { "${it.count}${it.diceType.displayName}" }
    
    return when {
        diceNotation.isEmpty() -> "No dice selected"
        preset.modifier == 0 -> diceNotation
        preset.modifier > 0 -> "$diceNotation+${preset.modifier}"
        else -> "$diceNotation${preset.modifier}"
    }
}

@Composable
fun HistoryScreen(viewModel: DiceRollViewModel) {
    val rollHistory by viewModel.rollHistory.collectAsState()
    val customization by viewModel.customization.collectAsState()
    var showClearHistoryDialog by remember { mutableStateOf(false) }
    
    // Track history view for achievements
    LaunchedEffect(Unit) {
        viewModel.onHistoryViewed()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with title and clear button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.tab_history),
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                    AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            if (rollHistory.isNotEmpty()) {
                Button(
                    onClick = { showClearHistoryDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                            AppTheme.FANTASY -> FantasyColors.ButtonRed
                            AppTheme.SCI_FI -> SciFiColors.ButtonRed
                            AppTheme.WESTERN -> WesternColors.ButtonRed
                            AppTheme.ANCIENT -> AncientColors.ButtonRed
                        }
                    ),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.action_clear),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (rollHistory.isEmpty()) {
            Text(
                text = stringResource(R.string.no_rolls_yet),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                },
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(rollHistory) { roll ->
                    RollHistoryCard(roll = roll, viewModel = viewModel, theme = customization.theme)
                }
            }
        }
    }
    
    // Clear History Confirmation Dialog
    if (showClearHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showClearHistoryDialog = false },
                            title = {
                    Text(
                        text = stringResource(R.string.confirm_clear_history),
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed
                        AppTheme.FANTASY -> FantasyColors.NeonRed
                        AppTheme.SCI_FI -> SciFiColors.NeonRed
                        AppTheme.WESTERN -> WesternColors.NeonRed
                        AppTheme.ANCIENT -> AncientColors.NeonRed
                    },
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to clear all roll history? This action cannot be undone.",
                                color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                AppTheme.FANTASY -> FantasyColors.PrimaryText
                AppTheme.SCI_FI -> SciFiColors.PrimaryText
                AppTheme.WESTERN -> WesternColors.PrimaryText
                AppTheme.ANCIENT -> AncientColors.PrimaryText
            }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearRollHistory()
                        showClearHistoryDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                            AppTheme.FANTASY -> FantasyColors.ButtonRed
                            AppTheme.SCI_FI -> SciFiColors.ButtonRed
                            AppTheme.WESTERN -> WesternColors.ButtonRed
                            AppTheme.ANCIENT -> AncientColors.ButtonRed
                        }
                    )
                ) {
                    Text("Clear History")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showClearHistoryDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                            AppTheme.FANTASY -> FantasyColors.ButtonGreen
                            AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                            AppTheme.WESTERN -> WesternColors.ButtonGreen
                            AppTheme.ANCIENT -> AncientColors.ButtonGreen
                        }
                    )
                ) {
                    Text("Cancel")
                }
            },
            containerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                AppTheme.FANTASY -> FantasyColors.CardBackground
                AppTheme.SCI_FI -> SciFiColors.CardBackground
                AppTheme.WESTERN -> WesternColors.CardBackground
                AppTheme.ANCIENT -> AncientColors.CardBackground
            }
        )
    }
}

@Composable
fun RollHistoryCard(
    roll: RollResult,
    viewModel: DiceRollViewModel,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    var showSaveDialog by remember { mutableStateOf(false) }
    var presetName by remember { mutableStateOf("") }
    var presetDescription by remember { mutableStateOf("") }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                AppTheme.FANTASY -> FantasyColors.BorderBlue
                AppTheme.SCI_FI -> SciFiColors.BorderBlue
                AppTheme.WESTERN -> WesternColors.BorderBlue
                AppTheme.ANCIENT -> AncientColors.BorderBlue
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header row with result and timestamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Result: ${roll.total}",
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                            AppTheme.FANTASY -> FantasyColors.NeonYellow
                            AppTheme.SCI_FI -> SciFiColors.NeonYellow
                            AppTheme.WESTERN -> WesternColors.NeonYellow
                            AppTheme.ANCIENT -> AncientColors.NeonYellow
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(${roll.notation})",
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                            AppTheme.FANTASY -> FantasyColors.NeonBlue
                            AppTheme.SCI_FI -> SciFiColors.NeonBlue
                            AppTheme.WESTERN -> WesternColors.NeonBlue
                            AppTheme.ANCIENT -> AncientColors.NeonBlue
                        },
                        fontSize = 14.sp
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = formatTimestamp(roll.timestamp),
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
                            AppTheme.ANCIENT -> AncientColors.SecondaryText
                        },
                        fontSize = 12.sp
                    )
                    Button(
                        onClick = { showSaveDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                                AppTheme.FANTASY -> FantasyColors.ButtonGreen
                                AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                                AppTheme.WESTERN -> WesternColors.ButtonGreen
                                AppTheme.ANCIENT -> AncientColors.ButtonGreen
                            }
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.action_save),
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
            }
            
            // Show individual dice results
            if (roll.individualRolls.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.individual_rolls_label),
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                        AppTheme.FANTASY -> FantasyColors.PrimaryText
                        AppTheme.SCI_FI -> SciFiColors.PrimaryText
                        AppTheme.WESTERN -> WesternColors.PrimaryText
                        AppTheme.ANCIENT -> AncientColors.PrimaryText
                    },
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                roll.individualRolls.forEachIndexed { index, rolls ->
                    val diceType = roll.diceSelections.getOrNull(index)?.diceType
                    if (diceType != null && rolls.isNotEmpty()) {
                        val rollText = if (diceType == DiceType.D100 && roll.d100Rolls.isNotEmpty()) {
                            // Show detailed D100 information
                            val d100Details = roll.d100Rolls.map { d100Roll ->
                                "${d100Roll.result} [${d100Roll.tensDie},${d100Roll.onesDie}]"
                            }
                            "${diceType.displayName}: ${d100Details.joinToString(", ")}"
                        } else {
                            "${diceType.displayName}: ${rolls.joinToString(", ")}"
                        }
                        Text(
                            text = rollText,
                            color = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                                AppTheme.FANTASY -> FantasyColors.SecondaryText
                                AppTheme.SCI_FI -> SciFiColors.SecondaryText
                                AppTheme.WESTERN -> WesternColors.SecondaryText
                                AppTheme.ANCIENT -> AncientColors.SecondaryText
                            },
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
    
    // Save to Presets Dialog
    if (showSaveDialog) {
        SavePresetDialog(
            onDismiss = { showSaveDialog = false },
            onSave = { name, description ->
                viewModel.createPresetFromRoll(roll, name, description)
                showSaveDialog = false
            },
            defaultName = "Roll ${roll.notation}",
            defaultDescription = "Saved as favorite from roll history",
            theme = theme
        )
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("MMM dd, yyyy HH:mm:ss", java.util.Locale.US)
    return formatter.format(date)
}

@Composable
fun SavePresetDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    defaultName: String,
    defaultDescription: String,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    var name by remember { mutableStateOf(defaultName) }
    var description by remember { mutableStateOf(defaultDescription) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
                    title = {
                Text(
                    text = stringResource(R.string.save_preset_dialog_title),
                color = getThemeColor(theme, "NeonYellow"),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Favorite Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedBorderColor = getThemeColor(theme, "BorderBlue"),
                        focusedLabelColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedLabelColor = getThemeColor(theme, "SecondaryText"),
                        focusedTextColor = getThemeColor(theme, "PrimaryText"),
                        unfocusedTextColor = getThemeColor(theme, "PrimaryText")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedBorderColor = getThemeColor(theme, "BorderBlue"),
                        focusedLabelColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedLabelColor = getThemeColor(theme, "SecondaryText"),
                        focusedTextColor = getThemeColor(theme, "PrimaryText"),
                        unfocusedTextColor = getThemeColor(theme, "PrimaryText")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, description) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "ButtonGreen")
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "ButtonRed")
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = getThemeColor(theme, "CardBackground"),
        titleContentColor = getThemeColor(theme, "NeonYellow"),
        textContentColor = getThemeColor(theme, "PrimaryText")
    )
}

@Composable
fun EditPresetDialog(
    preset: PresetRoll,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    var name by remember { mutableStateOf(preset.name) }
    var description by remember { mutableStateOf(preset.description) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Favorite",
                color = getThemeColor(theme, "NeonYellow"),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Favorite Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedBorderColor = getThemeColor(theme, "BorderBlue"),
                        focusedLabelColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedLabelColor = getThemeColor(theme, "SecondaryText"),
                        focusedTextColor = getThemeColor(theme, "PrimaryText"),
                        unfocusedTextColor = getThemeColor(theme, "PrimaryText")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedBorderColor = getThemeColor(theme, "BorderBlue"),
                        focusedLabelColor = getThemeColor(theme, "NeonBlue"),
                        unfocusedLabelColor = getThemeColor(theme, "SecondaryText"),
                        focusedTextColor = getThemeColor(theme, "PrimaryText"),
                        unfocusedTextColor = getThemeColor(theme, "PrimaryText")
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, description) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "ButtonGreen")
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "ButtonRed")
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = getThemeColor(theme, "CardBackground"),
        titleContentColor = getThemeColor(theme, "NeonYellow"),
        textContentColor = getThemeColor(theme, "PrimaryText")
    )
}

@Composable
fun DeletePresetDialog(
    presetName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete Favorite",
                color = getThemeColor(theme, "NeonRed"),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$presetName\"? This action cannot be undone.",
                color = getThemeColor(theme, "PrimaryText")
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "ButtonRed")
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = getThemeColor(theme, "NeonBlue")
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = getThemeColor(theme, "CardBackground"),
        titleContentColor = getThemeColor(theme, "NeonRed"),
        textContentColor = getThemeColor(theme, "PrimaryText")
    )
}

@Composable
fun PresetLoadedNotification(
    message: String,
    onDismiss: () -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getThemeColor(theme, "ButtonGreen").copy(alpha = 0.2f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = getThemeColor(theme, "ButtonGreen")
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Success icon
                Text(
                    text = "✓",
                    color = getThemeColor(theme, "ButtonGreen"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                
                // Message
                Text(
                    text = message,
                    color = getThemeColor(theme, "PrimaryText"),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Dismiss button
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.size(24.dp)
            ) {
                Text(
                    text = "×",
                    color = getThemeColor(theme, "SecondaryText"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AppTitle(theme: AppTheme) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            })
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HEXAROLL",
            color = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
    }
} 