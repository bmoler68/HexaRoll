package com.brianmoler.hexaroll.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
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
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.ui.theme.*
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel
import androidx.core.net.toUri

/**
 * Settings screen that provides access to app information and legal documents
 * 
 * Features:
 * - About page link that opens in browser
 * - Privacy Policy link that opens in browser
 * - Theme-aware styling consistent with app design
 * - Clean, organized layout with clear descriptions
 */
@Composable
fun SettingsScreen(viewModel: DiceRollViewModel) {
    val customization by viewModel.customization.collectAsState()
    val context = LocalContext.current
    
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "App information and legal documents",
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
        
        // Background Controls Section
        Text(
            text = "Background Settings",
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )
        
        // Background Enable/Disable Toggle
        BackgroundToggleCard(
            enabled = customization.backgroundEnabled,
            theme = customization.theme,
            customization = customization,
            onToggle = { viewModel.updateBackgroundEnabled(it) }
        )
        
        // Background Opacity Slider (only shown if backgrounds are enabled)
        if (customization.backgroundEnabled) {
            Spacer(modifier = Modifier.height(12.dp))
            BackgroundOpacityCard(
                opacity = customization.backgroundOpacity,
                theme = customization.theme,
                customization = customization,
                onOpacityChange = { viewModel.updateBackgroundOpacity(it) }
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
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
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
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
        
        // Main content
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(settingsOptions) { option ->
                SettingsOptionCard(
                    option = option,
                    theme = customization.theme,
                    customization = customization,
                    onClick = {
                        when (option.id) {
                            "about" -> {
                                val intent = Intent(Intent.ACTION_VIEW, "https://www.brianmoler.com/appdocs/HexaRoll/HexaRollDetails.html".toUri())
                                context.startActivity(intent)
                            }
                            "privacy" -> {
                                val intent = Intent(Intent.ACTION_VIEW, "https://www.brianmoler.com/appdocs/HexaRoll/HexaRollPrivacyPolicy.html".toUri())
                                context.startActivity(intent)
                            }
                        }
                    }
                )
            }
        }
        
        // Footer information
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
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
                text = "Version 1.0.0",
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
                text = "© 2025 Brian Moler. All rights reserved.",
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

/**
 * Card component for toggling background images on/off
 */
@Composable
fun BackgroundToggleCard(
    enabled: Boolean,
    theme: AppTheme,
    customization: DiceCustomization,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Theme Backgrounds",
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
                Text(
                    text = if (enabled) "Background images are enabled" else "Background images are disabled",
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
            }
            
            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    uncheckedThumbColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    uncheckedTrackColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                        AppTheme.FANTASY -> FantasyColors.BorderBlue
                        AppTheme.SCI_FI -> SciFiColors.BorderBlue
                        AppTheme.WESTERN -> WesternColors.BorderBlue
                        AppTheme.ANCIENT -> AncientColors.BorderBlue
                    }
                )
            )
        }
    }
}

/**
 * Card component for adjusting background opacity
 */
@Composable
fun BackgroundOpacityCard(
    opacity: Float,
    theme: AppTheme,
    customization: DiceCustomization,
    onOpacityChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Background Opacity",
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
                Text(
                    text = "${(opacity * 100).toInt()}%",
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                        AppTheme.FANTASY -> FantasyColors.NeonYellow
                        AppTheme.SCI_FI -> SciFiColors.NeonYellow
                        AppTheme.WESTERN -> WesternColors.NeonYellow
                        AppTheme.ANCIENT -> AncientColors.NeonYellow
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                text = "Adjust how visible the background image is",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                },
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            
            Slider(
                value = opacity,
                onValueChange = onOpacityChange,
                valueRange = 0.1f..1.0f,
                steps = 8, // 10%, 20%, 30%, ..., 100%
                colors = SliderDefaults.colors(
                    thumbColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    activeTrackColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    inactiveTrackColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                        AppTheme.FANTASY -> FantasyColors.BorderBlue
                        AppTheme.SCI_FI -> SciFiColors.BorderBlue
                        AppTheme.WESTERN -> WesternColors.BorderBlue
                        AppTheme.ANCIENT -> AncientColors.BorderBlue
                    }
                )
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtle",
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 12.sp
                )
                Text(
                    text = "Vivid",
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
} 