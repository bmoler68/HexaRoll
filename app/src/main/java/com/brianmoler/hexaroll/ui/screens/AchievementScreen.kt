package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.data.*
import com.brianmoler.hexaroll.ui.theme.*
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

@Composable
fun AchievementScreen(viewModel: DiceRollViewModel) {
    val achievements by viewModel.achievements.collectAsState()
    val achievementStats by viewModel.achievementStats.collectAsState()
    val customization by viewModel.customization.collectAsState()
    var selectedCategory by remember { mutableStateOf<AchievementCategory?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header with completion percentage
        AchievementHeader(
            completionPercentage = viewModel.getCompletionPercentage(),
            totalAchievements = achievements.size,
            unlockedAchievements = achievements.count { it.isUnlocked },
            theme = customization.theme
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category filter buttons
        CategoryFilterButtons(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            theme = customization.theme
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Achievement list
        val filteredAchievements = if (selectedCategory != null) {
            achievements.filter { it.category == selectedCategory }
        } else {
            achievements
        }
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredAchievements) { achievement ->
                AchievementCard(
                    achievement = achievement,
                    theme = customization.theme
                )
            }
        }
    }
}

@Composable
fun AchievementHeader(
    completionPercentage: Double,
    totalAchievements: Int,
    unlockedAchievements: Int,
    theme: AppTheme
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🏆 ACHIEVEMENTS",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                },
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${unlockedAchievements}/${totalAchievements} Unlocked",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                    AppTheme.FANTASY -> FantasyColors.PrimaryText
                    AppTheme.SCI_FI -> SciFiColors.PrimaryText
                    AppTheme.WESTERN -> WesternColors.PrimaryText
                },
                fontSize = 16.sp
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "${String.format("%.1f", completionPercentage)}% Complete",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                    AppTheme.FANTASY -> FantasyColors.NeonGreen
                    AppTheme.SCI_FI -> SciFiColors.NeonGreen
                    AppTheme.WESTERN -> WesternColors.NeonGreen
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Progress bar
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = (completionPercentage / 100).toFloat(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                    AppTheme.FANTASY -> FantasyColors.NeonGreen
                    AppTheme.SCI_FI -> SciFiColors.NeonGreen
                    AppTheme.WESTERN -> WesternColors.NeonGreen
                },
                trackColor = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                    AppTheme.FANTASY -> FantasyColors.BorderBlue
                    AppTheme.SCI_FI -> SciFiColors.BorderBlue
                    AppTheme.WESTERN -> WesternColors.BorderBlue
                }
            )
        }
    }
}

@Composable
fun CategoryFilterButtons(
    selectedCategory: AchievementCategory?,
    onCategorySelected: (AchievementCategory?) -> Unit,
    theme: AppTheme
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
            theme = theme
        )
        
        // Individual category buttons
        AchievementCategory.values().forEach { category ->
            FilterButton(
                text = category.displayName,
                isSelected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                theme = theme
            )
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    theme: AppTheme
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                }
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                    AppTheme.FANTASY -> FantasyColors.CardBackground
                    AppTheme.SCI_FI -> SciFiColors.CardBackground
                    AppTheme.WESTERN -> WesternColors.CardBackground
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
                }
            },
            fontSize = 12.sp
        )
    }
}

@Composable
fun AchievementCard(
    achievement: Achievement,
    theme: AppTheme
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked) {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                    AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                    AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                    AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                }
            } else {
                when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                    AppTheme.FANTASY -> FantasyColors.CardBackground
                    AppTheme.SCI_FI -> SciFiColors.CardBackground
                    AppTheme.WESTERN -> WesternColors.CardBackground
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
                }
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Achievement icon
            Text(
                text = achievement.icon,
                fontSize = 32.sp,
                modifier = Modifier.padding(end = 12.dp)
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
                            }
                        },
                        fontSize = 16.sp,
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
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = achievement.description,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                    },
                    fontSize = 12.sp
                )
                
                // Progress bar for achievements with progress
                if (achievement.maxProgress > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = (achievement.progress.toFloat() / achievement.maxProgress),
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
                            }
                        },
                        trackColor = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                            AppTheme.FANTASY -> FantasyColors.BorderBlue
                            AppTheme.SCI_FI -> SciFiColors.BorderBlue
                            AppTheme.WESTERN -> WesternColors.BorderBlue
                        }
                    )
                    
                    Text(
                        text = "${achievement.progress}/${achievement.maxProgress}",
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                            AppTheme.FANTASY -> FantasyColors.SecondaryText
                            AppTheme.SCI_FI -> SciFiColors.SecondaryText
                            AppTheme.WESTERN -> WesternColors.SecondaryText
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