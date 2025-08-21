package com.brianmoler.hexaroll.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.AppInfoData
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.ui.theme.*
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel
import androidx.core.net.toUri

/**
 * Settings screen that provides access to app information, legal documents, and app controls
 * 
 * Features:
 * - App Settings section with achievement reset functionality
 * - Sound Settings section with dice rolling sound toggle
 * - About page link that opens in browser
 * - Privacy Policy link that opens in browser
 * - Theme-aware styling consistent with app design
 * - Clean, organized layout with clear descriptions
 */
@Composable
fun SettingsScreen(viewModel: DiceRollViewModel) {
    val customization by viewModel.customization.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    var showResetConfirmation by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(12.dp)
    ) {
        // Settings Header
        Text(
            text = "Settings",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Text(
            text = "App information, legal documents, and app controls",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                AppTheme.FANTASY -> FantasyColors.SecondaryText
                AppTheme.SCI_FI -> SciFiColors.SecondaryText
                AppTheme.WESTERN -> WesternColors.SecondaryText
                AppTheme.ANCIENT -> AncientColors.SecondaryText
            },
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // App Settings Section
        Text(
            text = "App Settings",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Reset Achievement Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.WESTERN -> WesternColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon with background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = when (customization.theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                                AppTheme.FANTASY -> FantasyColors.NeonBlue
                                AppTheme.SCI_FI -> SciFiColors.NeonBlue
                                AppTheme.WESTERN -> WesternColors.NeonBlue
                                AppTheme.ANCIENT -> AncientColors.NeonBlue
                            },
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Reset Achievement Progress",
                        color = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                            AppTheme.FANTASY -> FantasyColors.PrimaryText
                            AppTheme.SCI_FI -> SciFiColors.PrimaryText
                            AppTheme.WESTERN -> WesternColors.PrimaryText
                            AppTheme.ANCIENT -> AncientColors.PrimaryText
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Reset all achievement progress and statistics",
                        color = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
                            AppTheme.ANCIENT -> AncientColors.SecondaryText
                        },
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
                
                // Clickable area
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { showResetConfirmation = true }
                        .background(
                            color = when (customization.theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed.copy(alpha = 0.2f)
                                AppTheme.FANTASY -> FantasyColors.NeonRed.copy(alpha = 0.2f)
                                AppTheme.SCI_FI -> SciFiColors.NeonRed.copy(alpha = 0.2f)
                                AppTheme.WESTERN -> WesternColors.NeonRed.copy(alpha = 0.2f)
                                AppTheme.ANCIENT -> AncientColors.NeonRed.copy(alpha = 0.2f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Reset achievements",
                        tint = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed
                            AppTheme.FANTASY -> FantasyColors.NeonRed
                            AppTheme.SCI_FI -> SciFiColors.NeonRed
                            AppTheme.WESTERN -> WesternColors.NeonRed
                            AppTheme.ANCIENT -> AncientColors.NeonRed
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Sound Settings Section
        Text(
            text = "Sound Settings",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Sound Toggle Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.WESTERN -> WesternColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon with background
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = when (customization.theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                                AppTheme.FANTASY -> FantasyColors.NeonBlue
                                AppTheme.SCI_FI -> SciFiColors.NeonBlue
                                AppTheme.WESTERN -> WesternColors.NeonBlue
                                AppTheme.ANCIENT -> AncientColors.NeonBlue
                            },
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Content
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Dice Rolling Sound",
                        color = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                            AppTheme.FANTASY -> FantasyColors.PrimaryText
                            AppTheme.SCI_FI -> SciFiColors.PrimaryText
                            AppTheme.WESTERN -> WesternColors.PrimaryText
                            AppTheme.ANCIENT -> AncientColors.PrimaryText
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "Play sound effects when rolling dice",
                        color = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
                            AppTheme.ANCIENT -> AncientColors.SecondaryText
                        },
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
                
                // Toggle Switch
                val soundEnabled by viewModel.soundEnabled.collectAsState()
                Switch(
                    checked = soundEnabled,
                    onCheckedChange = { enabled ->
                        viewModel.setSoundEnabled(enabled)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                            AppTheme.FANTASY -> FantasyColors.NeonGreen
                            AppTheme.SCI_FI -> SciFiColors.NeonGreen
                            AppTheme.WESTERN -> WesternColors.NeonGreen
                            AppTheme.ANCIENT -> AncientColors.NeonGreen
                        },
                        checkedTrackColor = when (customization.theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen.copy(alpha = 0.2f)
                            AppTheme.FANTASY -> FantasyColors.NeonGreen.copy(alpha = 0.2f)
                            AppTheme.SCI_FI -> SciFiColors.NeonGreen.copy(alpha = 0.2f)
                            AppTheme.WESTERN -> WesternColors.NeonGreen.copy(alpha = 0.2f)
                            AppTheme.ANCIENT -> AncientColors.NeonGreen.copy(alpha = 0.2f)
                        }
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // App Information Section
        Text(
            text = "App Information",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Settings Options
        val settingsOptions = listOf(
            SettingsOption(
                id = "about",
                title = stringResource(R.string.settings_about),
                description = stringResource(R.string.settings_about_description),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            ),
            SettingsOption(
                id = "privacy",
                title = stringResource(R.string.settings_privacy_policy),
                description = stringResource(R.string.settings_privacy_description),
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
        )
        
        // Settings Options
        settingsOptions.forEach { option ->
            SettingsOptionCard(
                option = option,
                theme = customization.theme,
                customization = customization,
                onClick = {
                    when (option.id) {
                        "about" -> {
                            val intent = Intent(Intent.ACTION_VIEW, AppInfoData.Urls.ABOUT_PAGE.toUri())
                            context.startActivity(intent)
                        }
                        "privacy" -> {
                            val intent = Intent(Intent.ACTION_VIEW, AppInfoData.Urls.PRIVACY_POLICY.toUri())
                            context.startActivity(intent)
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Footer information
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                    AppTheme.ANCIENT -> AncientColors.NeonYellow
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = AppInfoData.getVersionString(),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                },
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Text(
                text = AppInfoData.getCopyrightString(),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        // Extra bottom padding for comfortable scrolling
        Spacer(modifier = Modifier.height(16.dp))
    }
    
    // Reset Achievement Progress Confirmation Dialog
    if (showResetConfirmation) {
        AlertDialog(
            onDismissRequest = { showResetConfirmation = false },
            containerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.confirm_reset_achievements),
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
                    text = "This will permanently delete all achievement progress, unlocked achievements, and statistics. This action cannot be undone.",
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetAllProgress()
                        showResetConfirmation = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC62828), // Dark Red
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.action_reset))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showResetConfirmation = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1565C0), // Dark Blue
                        contentColor = Color.White
                    )
                ) {
                    Text(stringResource(R.string.action_cancel))
                }
            }
        )
    }
}

/**
 * Data class representing a settings option
 */
data class SettingsOption(
    val id: String,
    val title: String,
    val description: String,
    val icon: @Composable () -> Unit
)

// Settings options will be created inside the composable function

/**
 * Composable for a settings option card
 * 
 * @param option The settings option to display
 * @param theme The current app theme for styling
 * @param customization The current dice customization for dynamic transparency
 * @param onClick Callback when the option is clicked
 */
@Composable
fun SettingsOptionCard(
    option: SettingsOption,
    theme: AppTheme,
    customization: DiceCustomization,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        // Make cards more transparent as background opacity increases
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f // Full opacity when background is disabled
                    }
                )
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground.copy(
                    alpha = if (customization.backgroundEnabled) {
                        (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                    } else {
                        1.0f
                    }
                )
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                            AppTheme.FANTASY -> FantasyColors.NeonBlue
                            AppTheme.SCI_FI -> SciFiColors.NeonBlue
                            AppTheme.WESTERN -> WesternColors.NeonBlue
                            AppTheme.ANCIENT -> AncientColors.NeonBlue
                        },
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                option.icon()
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = option.title,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                        AppTheme.FANTASY -> FantasyColors.PrimaryText
                        AppTheme.SCI_FI -> SciFiColors.PrimaryText
                        AppTheme.WESTERN -> WesternColors.PrimaryText
                        AppTheme.ANCIENT -> AncientColors.PrimaryText
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = option.description,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
            
            // External link icon
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Opens in browser",
                tint = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                    AppTheme.ANCIENT -> AncientColors.NeonYellow
                },
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

 