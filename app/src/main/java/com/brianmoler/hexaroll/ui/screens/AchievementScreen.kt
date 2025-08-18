package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.*
import com.brianmoler.hexaroll.ui.theme.*
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel
import androidx.compose.material.icons.Icons

@Composable
fun AchievementScreen(viewModel: DiceRollViewModel) {
    val achievements by viewModel.achievements.collectAsState()
    val customization by viewModel.customization.collectAsState()
    val completionPercentage by viewModel.getCompletionPercentage().collectAsState()
    
    var selectedCategory by remember { mutableStateOf<AchievementCategory?>(null) }
    
    // Filter achievements based on selected category
    val filteredAchievements = if (selectedCategory != null) {
        achievements.filter { it.category == selectedCategory }
    } else {
        achievements
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AchievementHeader(
            completionPercentage = completionPercentage,
            totalAchievements = achievements.size,
            unlockedCount = achievements.count { it.isUnlocked }
        )
        
        CategoryFilterButtons(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            theme = customization.theme,
            customization = customization
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredAchievements) { achievement ->
                AchievementCard(
                    achievement = achievement,
                    theme = customization.theme,
                    customization = customization
                )
            }
        }
    }
}

@Composable
fun AchievementHeader(
    completionPercentage: Float,
    totalAchievements: Int,
    unlockedCount: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Achievements",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = "$unlockedCount / $totalAchievements Unlocked",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = "${(completionPercentage * 100).toInt()}% Complete",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        
    }
}

@Composable
fun CategoryFilterButtons(
    selectedCategory: AchievementCategory?,
    onCategorySelected: (AchievementCategory?) -> Unit,
    theme: AppTheme,
    customization: DiceCustomization
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // All categories button
        FilterButton(
            text = "All",
            isSelected = selectedCategory == null,
            onClick = { onCategorySelected(null) },
            theme = theme,
            customization = customization
        )
        
        // Individual category buttons
        AchievementCategory.entries.forEach { category ->
            FilterButton(
                text = category.displayName,
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                theme = theme,
                customization = customization
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    theme: AppTheme,
    customization: DiceCustomization
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow.copy(
                        alpha = if (customization.backgroundEnabled) {
                            // Keep selected buttons more opaque for visibility
                            (1.0f - customization.backgroundOpacity * 0.3f).coerceAtLeast(0.7f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.FANTASY -> FantasyColors.NeonYellow.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.3f).coerceAtLeast(0.7f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.3f).coerceAtLeast(0.7f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.WESTERN -> WesternColors.NeonYellow.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.3f).coerceAtLeast(0.7f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.ANCIENT -> AncientColors.NeonYellow.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.3f).coerceAtLeast(0.7f)
                        } else {
                            1.0f
                        }
                    )
                }
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.FANTASY -> FantasyColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.SCI_FI -> SciFiColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.WESTERN -> WesternColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.ANCIENT -> AncientColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                }
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
        ),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) {
                Color.Black
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                    AppTheme.FANTASY -> FantasyColors.PrimaryText
                    AppTheme.SCI_FI -> SciFiColors.PrimaryText
                    AppTheme.WESTERN -> WesternColors.PrimaryText
                    AppTheme.ANCIENT -> AncientColors.PrimaryText
                }
            },
            fontSize = 12.sp
        )
    }
}

@Composable
fun AchievementCard(
    achievement: Achievement,
    theme: AppTheme,
    customization: DiceCustomization
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked) {
                when (theme) {
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
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.FANTASY -> FantasyColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.SCI_FI -> SciFiColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.WESTERN -> WesternColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                    AppTheme.ANCIENT -> AncientColors.CardBackground.copy(
                        alpha = if (customization.backgroundEnabled) {
                            (1.0f - customization.backgroundOpacity * 0.7f).coerceAtLeast(0.2f)
                        } else {
                            1.0f
                        }
                    )
                }
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (achievement.isUnlocked) 2.dp else 1.dp,
            color = if (achievement.isUnlocked) {
                achievement.tier.color
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                    AppTheme.FANTASY -> FantasyColors.BorderBlue
                    AppTheme.SCI_FI -> SciFiColors.BorderBlue
                    AppTheme.WESTERN -> WesternColors.BorderBlue
                    AppTheme.ANCIENT -> AncientColors.BorderBlue
                }
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Achievement icon
            Text(
                text = achievement.icon,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            
            // Achievement details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = achievement.name,
                        color = if (achievement.isUnlocked) {
                            achievement.tier.color
                        } else {
                            when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                                AppTheme.FANTASY -> FantasyColors.SecondaryText
                                AppTheme.SCI_FI -> SciFiColors.SecondaryText
                                AppTheme.WESTERN -> WesternColors.SecondaryText
                                AppTheme.ANCIENT -> AncientColors.SecondaryText
                            }
                        },
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    // Tier badge
                    Text(
                        text = achievement.tier.displayName,
                        color = achievement.tier.color,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(
                                color = achievement.tier.color.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(2.dp))
                
                Text(
                    text = achievement.description,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 12.sp
                )
                
                // Progress bar for achievements with progress
                if (achievement.maxProgress > 1) {
                    Spacer(modifier = Modifier.height(2.dp))
                    LinearProgressIndicator(
                        progress = { (achievement.progress.toFloat() / achievement.maxProgress) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp),
                        color = if (achievement.isUnlocked) {
                            achievement.tier.color
                        } else {
                            when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                                AppTheme.FANTASY -> FantasyColors.NeonBlue
                                AppTheme.SCI_FI -> SciFiColors.NeonBlue
                                AppTheme.WESTERN -> WesternColors.NeonBlue
                                AppTheme.ANCIENT -> AncientColors.NeonBlue
                            }
                        },
                        trackColor = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                            AppTheme.FANTASY -> FantasyColors.BorderBlue
                            AppTheme.SCI_FI -> SciFiColors.BorderBlue
                            AppTheme.WESTERN -> WesternColors.BorderBlue
                            AppTheme.ANCIENT -> AncientColors.BorderBlue
                        },
                    )
                    
                    Text(
                        text = "${achievement.progress}/${achievement.maxProgress}",
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
                            AppTheme.ANCIENT -> AncientColors.SecondaryText
                        },
                        fontSize = 10.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Unlock status
            if (achievement.isUnlocked) {
                Text(
                    text = "✓",
                    color = achievement.tier.color,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
} 