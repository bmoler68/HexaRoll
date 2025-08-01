package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.res.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.ui.theme.CyberpunkColors
import com.brianmoler.hexaroll.ui.theme.FantasyColors
import com.brianmoler.hexaroll.ui.theme.SciFiColors
import com.brianmoler.hexaroll.ui.theme.WesternColors
import com.brianmoler.hexaroll.ui.theme.AncientColors
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel
import com.brianmoler.hexaroll.R

@Composable
fun DiceArena(viewModel: DiceRollViewModel) {
    val diceSelections by viewModel.diceSelections.collectAsState()
    val modifier by viewModel.modifier.collectAsState()
    val customization by viewModel.customization.collectAsState()
    
    // Collapsible state management
    var isCardsExpanded by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                    AppTheme.FANTASY -> FantasyColors.CardBackground
                    AppTheme.SCI_FI -> SciFiColors.CardBackground
                    AppTheme.WESTERN -> WesternColors.CardBackground
                    AppTheme.ANCIENT -> AncientColors.CardBackground
                },
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                    AppTheme.FANTASY -> FantasyColors.BorderBlue
                    AppTheme.SCI_FI -> SciFiColors.BorderBlue
                    AppTheme.WESTERN -> WesternColors.BorderBlue
                    AppTheme.ANCIENT -> AncientColors.BorderBlue
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        // Title
        Text(
            text = stringResource(R.string.tab_dice_arena),
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Dice Grid with Scroll Indicator
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(DiceType.values()) { diceType ->
                    DiceCard(
                        diceType = diceType,
                        count = diceSelections[diceType]?.count ?: 0,
                        onIncrement = { viewModel.incrementDice(diceType) },
                        onDecrement = { viewModel.decrementDice(diceType) },
                        theme = customization.theme
                    )
                }
            }
            
            // Scroll indicator gradient overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                when (customization.theme) {
                                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground.copy(alpha = 0.8f)
                                    AppTheme.FANTASY -> FantasyColors.CardBackground.copy(alpha = 0.8f)
                                    AppTheme.SCI_FI -> SciFiColors.CardBackground.copy(alpha = 0.8f)
                                    AppTheme.WESTERN -> WesternColors.CardBackground.copy(alpha = 0.8f)
                                    AppTheme.ANCIENT -> AncientColors.CardBackground.copy(alpha = 0.8f)
                                }
                            )
                        )
                    )
                    .zIndex(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Modifier Section
        ModifierSection(
            modifier = modifier,
            onIncrement = { viewModel.incrementModifier() },
            onDecrement = { viewModel.decrementModifier() },
            theme = customization.theme
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Clear Arena Button
            ClearArenaButton(
                onClick = { viewModel.clearArena() },
                modifier = Modifier.weight(1f),
                viewModel = viewModel
            )
            
            // Roll Button
            RollButton(
                onClick = { viewModel.rollDice() },
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Collapsible Results Section
        CollapsibleResultsSection(
            viewModel = viewModel,
            isExpanded = isCardsExpanded,
            onToggle = { isCardsExpanded = !isCardsExpanded },
            theme = customization.theme
        )
    }
}

@Composable
fun CollapsibleResultsSection(
    viewModel: DiceRollViewModel,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    theme: AppTheme
) {
    val currentResult by viewModel.currentResult.collectAsState()
    val diceSelections by viewModel.diceSelections.collectAsState()
    val modifierValue by viewModel.modifier.collectAsState()
    
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
        Column {
            // Toggle Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Compact Summary
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Dice Summary (truncated)
                    val totalDice = diceSelections.values.sumOf { it.count }
                    val diceTypes = diceSelections.values.filter { it.count > 0 }
                    
                    val summaryText = when {
                        diceTypes.isEmpty() -> stringResource(R.string.no_dice_selected)
                        diceTypes.size <= 2 -> {
                            val notation = diceTypes.joinToString(" + ") { selection ->
                                if (selection.diceType == DiceType.D100) {
                                    "${selection.count}x100"
                                } else {
                                    "${selection.count}${selection.diceType.displayName}"
                                }
                            }
                            val modifierText = if (modifierValue != 0) (if (modifierValue > 0) "+$modifierValue" else "$modifierValue") else ""
                            notation + (if (modifierText.isNotBlank()) " $modifierText" else "")
                        }
                        else -> {
                            val totalCount = diceTypes.sumOf { it.count }
                            val modifierText = if (modifierValue != 0) (if (modifierValue > 0) "+$modifierValue" else "$modifierValue") else ""
                            "$totalCount dice" + (if (modifierText.isNotBlank()) " $modifierText" else "")
                        }
                    }
                    
                    Text(
                        text = summaryText,
                        color = when (theme) {
                            AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                            AppTheme.FANTASY -> FantasyColors.NeonBlue
                            AppTheme.SCI_FI -> SciFiColors.NeonBlue
                            AppTheme.WESTERN -> WesternColors.NeonBlue
                            AppTheme.ANCIENT -> AncientColors.NeonBlue
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    
                    // Result Summary
                    currentResult?.let { result ->
                        Text(
                            text = "= ${result.total}",
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
                    } ?: run {
                        Text(
                            text = stringResource(R.string.no_rolls_yet),
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
                
                // Toggle Icon
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = if (isExpanded) "Hide details" else "Show details",
                    tint = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                        AppTheme.FANTASY -> FantasyColors.PrimaryText
                        AppTheme.SCI_FI -> SciFiColors.PrimaryText
                        AppTheme.WESTERN -> WesternColors.PrimaryText
                        AppTheme.ANCIENT -> AncientColors.PrimaryText
                    },
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            // Expanded Content
            if (isExpanded) {
                Divider(
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                        AppTheme.FANTASY -> FantasyColors.BorderBlue
                        AppTheme.SCI_FI -> SciFiColors.BorderBlue
                        AppTheme.WESTERN -> WesternColors.BorderBlue
                        AppTheme.ANCIENT -> AncientColors.BorderBlue
                    },
                    thickness = 1.dp
                )
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Total Display
                    TotalDisplay(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Result Display
                    ResultDisplay(
                        viewModel = viewModel,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun DiceCard(
    diceType: DiceType,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
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
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Dice Shape
            DiceShape(
                diceType = diceType,
                theme = theme,
                isSelected = count > 0
            )

            // Dice Type Label
            Text(
                text = diceType.displayName,
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                    AppTheme.FANTASY -> FantasyColors.PrimaryText
                    AppTheme.SCI_FI -> SciFiColors.PrimaryText
                    AppTheme.ANCIENT -> AncientColors.PrimaryText
                    AppTheme.WESTERN -> WesternColors.PrimaryText
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            // Count Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                                AppTheme.FANTASY -> FantasyColors.ButtonRed
                                AppTheme.SCI_FI -> SciFiColors.ButtonRed
                                AppTheme.WESTERN -> WesternColors.ButtonRed
                                AppTheme.ANCIENT -> AncientColors.ButtonRed
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed
                                AppTheme.FANTASY -> FantasyColors.NeonRed
                                AppTheme.SCI_FI -> SciFiColors.NeonRed
                                AppTheme.WESTERN -> WesternColors.NeonRed
                                AppTheme.ANCIENT -> AncientColors.NeonRed
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { onDecrement() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("-", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = count.toString(),
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                        AppTheme.FANTASY -> FantasyColors.NeonYellow
                        AppTheme.SCI_FI -> SciFiColors.NeonYellow
                        AppTheme.WESTERN -> WesternColors.NeonYellow
                        AppTheme.ANCIENT -> AncientColors.NeonYellow
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )

                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                                AppTheme.FANTASY -> FantasyColors.ButtonGreen
                                AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                                AppTheme.WESTERN -> WesternColors.ButtonGreen
                                AppTheme.ANCIENT -> AncientColors.ButtonGreen
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = when (theme) {
                                AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                                AppTheme.FANTASY -> FantasyColors.NeonGreen
                                AppTheme.SCI_FI -> SciFiColors.NeonGreen
                                AppTheme.WESTERN -> WesternColors.NeonGreen
                                AppTheme.ANCIENT -> AncientColors.NeonGreen
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable { onIncrement() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ModifierSection(
    modifier: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                    AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                    AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                    AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                    AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
                },
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                    AppTheme.FANTASY -> FantasyColors.BorderBlue
                    AppTheme.SCI_FI -> SciFiColors.BorderBlue
                    AppTheme.WESTERN -> WesternColors.BorderBlue
                    AppTheme.ANCIENT -> AncientColors.BorderBlue
                },
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.modifier_label),
            color = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                AppTheme.FANTASY -> FantasyColors.NeonBlue
                AppTheme.SCI_FI -> SciFiColors.NeonBlue
                AppTheme.WESTERN -> WesternColors.NeonBlue
                AppTheme.ANCIENT -> AncientColors.NeonBlue
            },
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.size(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                        AppTheme.FANTASY -> FantasyColors.ButtonRed
                        AppTheme.SCI_FI -> SciFiColors.ButtonRed
                        AppTheme.WESTERN -> WesternColors.ButtonRed
                        AppTheme.ANCIENT -> AncientColors.ButtonRed
                    }
                ),
                contentPadding = PaddingValues(0.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed
                        AppTheme.FANTASY -> FantasyColors.NeonRed
                        AppTheme.SCI_FI -> SciFiColors.NeonRed
                        AppTheme.WESTERN -> WesternColors.NeonRed
                        AppTheme.ANCIENT -> AncientColors.NeonRed
                    }
                )
            ) {
                Text("-", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                text = if (modifier >= 0) "+$modifier" else "$modifier",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                    AppTheme.ANCIENT -> AncientColors.NeonYellow
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onIncrement,
                modifier = Modifier.size(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                        AppTheme.FANTASY -> FantasyColors.ButtonGreen
                        AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                        AppTheme.WESTERN -> WesternColors.ButtonGreen
                        AppTheme.ANCIENT -> AncientColors.ButtonGreen
                    }
                ),
                contentPadding = PaddingValues(0.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = when (theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                        AppTheme.FANTASY -> FantasyColors.NeonGreen
                        AppTheme.SCI_FI -> SciFiColors.NeonGreen
                        AppTheme.WESTERN -> WesternColors.NeonGreen
                        AppTheme.ANCIENT -> AncientColors.NeonGreen
                    }
                )
            ) {
                Text("+", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TotalDisplay(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val diceSelections by viewModel.diceSelections.collectAsState()
    val modifierValue by viewModel.modifier.collectAsState()
    val customization by viewModel.customization.collectAsState()

    val totalDice = diceSelections.values.sumOf { it.count }
    val notation = diceSelections.values
        .filter { it.count > 0 }
        .joinToString(" + ") { selection ->
            if (selection.diceType == DiceType.D100) {
                "${selection.count}x100"
            } else {
                "${selection.count}${selection.diceType.displayName}"
            }
        }
    val modifierText = if (modifierValue != 0) (if (modifierValue > 0) "+$modifierValue" else "$modifierValue") else ""

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                AppTheme.FANTASY -> FantasyColors.NeonYellow
                AppTheme.SCI_FI -> SciFiColors.NeonYellow
                AppTheme.WESTERN -> WesternColors.NeonYellow
                AppTheme.ANCIENT -> AncientColors.NeonYellow
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.total_label),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                    AppTheme.ANCIENT -> AncientColors.NeonYellow
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            if (notation.isNotBlank()) {
                Text(
                    text = notation + (if (modifierText.isNotBlank()) " $modifierText" else ""),
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    text = stringResource(R.string.no_dice_selected),
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = if (totalDice == 1) stringResource(R.string.dice_count_single) else stringResource(R.string.dice_count_plural, totalDice),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                    AppTheme.FANTASY -> FantasyColors.PrimaryText
                    AppTheme.SCI_FI -> SciFiColors.PrimaryText
                    AppTheme.WESTERN -> WesternColors.PrimaryText
                    AppTheme.ANCIENT -> AncientColors.PrimaryText
                },
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            if (modifierValue != 0) {
                Text(
                    text = if (modifierValue > 0) "+$modifierValue modifier" else "$modifierValue modifier",
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ResultDisplay(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val currentResult by viewModel.currentResult.collectAsState()
    val customization by viewModel.customization.collectAsState()

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonPurple
                AppTheme.FANTASY -> FantasyColors.NeonPurple
                AppTheme.SCI_FI -> SciFiColors.NeonPurple
                AppTheme.WESTERN -> WesternColors.NeonPurple
                AppTheme.ANCIENT -> AncientColors.NeonPurple
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.result_label),
                color = when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonPurple
                    AppTheme.FANTASY -> FantasyColors.NeonPurple
                    AppTheme.SCI_FI -> SciFiColors.NeonPurple
                    AppTheme.WESTERN -> WesternColors.NeonPurple
                    AppTheme.ANCIENT -> AncientColors.NeonPurple
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            currentResult?.let { result ->
                Text(
                    text = "${result.total}",
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
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "(${result.notation})",
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.NeonBlue
                        AppTheme.FANTASY -> FantasyColors.NeonBlue
                        AppTheme.SCI_FI -> SciFiColors.NeonBlue
                        AppTheme.WESTERN -> WesternColors.NeonBlue
                        AppTheme.ANCIENT -> AncientColors.NeonBlue
                    },
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                if (result.individualRolls.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Scrollable breakdown section
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        androidx.compose.foundation.lazy.LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            val breakdown = result.diceSelections.zip(result.individualRolls)
                            
                            items(breakdown.size) { index ->
                                val (sel, rolls) = breakdown[index]
                                val breakdownText = if (sel.diceType == DiceType.D100 && result.d100Rolls.isNotEmpty()) {
                                    // Show D10 breakdown for all D100 rolls
                                    val d100Details = result.d100Rolls.map { d100Roll ->
                                        "${d100Roll.result} [${d100Roll.tensDie},${d100Roll.onesDie}]"
                                    }
                                    "${sel.diceType.displayName}: ${d100Details.joinToString(", ")}"
                                } else {
                                    "${sel.diceType.displayName}: [${rolls.joinToString(", ")}]"
                                }
                                
                                Text(
                                    text = breakdownText,
                                    color = when (customization.theme) {
                                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                                        AppTheme.WESTERN -> WesternColors.SecondaryText
                                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                                    },
                                    fontSize = 9.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        
                        // Scroll indicator gradient overlay at bottom
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(12.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            when (customization.theme) {
                                                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground.copy(alpha = 0.8f)
                                                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground.copy(alpha = 0.8f)
                                                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground.copy(alpha = 0.8f)
                                                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground.copy(alpha = 0.8f)
                                                AppTheme.ANCIENT -> AncientColors.ElevatedCardBackground.copy(alpha = 0.8f)
                                            }
                                        )
                                    )
                                )
                                .zIndex(1f)
                        )
                    }
                }
            } ?: run {
                Text(
                    text = stringResource(R.string.no_rolls_yet),
                    color = when (customization.theme) {
                        AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                        AppTheme.FANTASY -> FantasyColors.SecondaryText
                        AppTheme.SCI_FI -> SciFiColors.SecondaryText
                        AppTheme.WESTERN -> WesternColors.SecondaryText
                        AppTheme.ANCIENT -> AncientColors.SecondaryText
                    },
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun ClearArenaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DiceRollViewModel
) {
    val customization by viewModel.customization.collectAsState()

    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ButtonRed
                AppTheme.FANTASY -> FantasyColors.ButtonRed
                AppTheme.SCI_FI -> SciFiColors.ButtonRed
                AppTheme.WESTERN -> WesternColors.ButtonRed
                AppTheme.ANCIENT -> AncientColors.ButtonRed
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.NeonRed
                AppTheme.FANTASY -> FantasyColors.NeonRed
                AppTheme.SCI_FI -> SciFiColors.NeonRed
                AppTheme.WESTERN -> WesternColors.NeonRed
                AppTheme.ANCIENT -> AncientColors.NeonRed
            }
        )
    ) {
        Text(
            text = stringResource(R.string.action_clear),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RollButton(
    onClick: () -> Unit,
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val diceSelections by viewModel.diceSelections.collectAsState()
    val customization by viewModel.customization.collectAsState()
    val isEnabled = diceSelections.values.any { it.count > 0 }

    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) {
                when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.ButtonGreen
                    AppTheme.FANTASY -> FantasyColors.ButtonGreen
                    AppTheme.SCI_FI -> SciFiColors.ButtonGreen
                    AppTheme.WESTERN -> WesternColors.ButtonGreen
                    AppTheme.ANCIENT -> AncientColors.ButtonGreen
                }
            } else {
                when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                    AppTheme.FANTASY -> FantasyColors.CardBackground
                    AppTheme.SCI_FI -> SciFiColors.CardBackground
                    AppTheme.WESTERN -> WesternColors.CardBackground
                    AppTheme.ANCIENT -> AncientColors.CardBackground
                }
            },
            disabledContainerColor = when (customization.theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.CardBackground
                AppTheme.FANTASY -> FantasyColors.CardBackground
                AppTheme.SCI_FI -> SciFiColors.CardBackground
                AppTheme.WESTERN -> WesternColors.CardBackground
                AppTheme.ANCIENT -> AncientColors.CardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = if (isEnabled) {
                when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonGreen
                    AppTheme.FANTASY -> FantasyColors.NeonGreen
                    AppTheme.SCI_FI -> SciFiColors.NeonGreen
                    AppTheme.WESTERN -> WesternColors.NeonGreen
                    AppTheme.ANCIENT -> AncientColors.NeonGreen
                }
            } else {
                when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.BorderBlue
                    AppTheme.FANTASY -> FantasyColors.BorderBlue
                    AppTheme.SCI_FI -> SciFiColors.BorderBlue
                    AppTheme.WESTERN -> WesternColors.BorderBlue
                    AppTheme.ANCIENT -> AncientColors.BorderBlue
                }
            }
        )
    ) {
        Text(
            text = stringResource(R.string.roll_button),
            color = if (isEnabled) Color.White else {
                when (customization.theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.SecondaryText
                    AppTheme.FANTASY -> FantasyColors.SecondaryText
                    AppTheme.SCI_FI -> SciFiColors.SecondaryText
                    AppTheme.WESTERN -> WesternColors.SecondaryText
                    AppTheme.ANCIENT -> AncientColors.SecondaryText
                }
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
} 