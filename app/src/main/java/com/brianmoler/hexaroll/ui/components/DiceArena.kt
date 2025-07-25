package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.ui.theme.CyberpunkColors
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

@Composable
fun DiceArena(viewModel: DiceRollViewModel) {
    val diceSelections by viewModel.diceSelections.collectAsState()
    val modifier by viewModel.modifier.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CyberpunkColors.CardBackground,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = CyberpunkColors.BorderBlue,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        // Title
        Text(
            text = "DICE ARENA",
            color = CyberpunkColors.NeonYellow,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Dice Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(DiceType.values()) { diceType ->
                DiceCard(
                    diceType = diceType,
                    count = diceSelections[diceType]?.count ?: 0,
                    onIncrement = { viewModel.incrementDice(diceType) },
                    onDecrement = { viewModel.decrementDice(diceType) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Modifier Section
        ModifierSection(
            modifier = modifier,
            onIncrement = { viewModel.incrementModifier() },
            onDecrement = { viewModel.decrementModifier() }
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Total and Result Row
        Row(
            modifier = Modifier.fillMaxWidth(),
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
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Clear Arena Button
            ClearArenaButton(
                onClick = { viewModel.clearArena() },
                modifier = Modifier.weight(1f)
            )
            
            // Roll Button
            RollButton(
                onClick = { viewModel.rollDice() },
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DiceCard(
    diceType: DiceType,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderBlue
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Dice Shape
            DiceShape(
                diceType = diceType,
                isSelected = count > 0
            )
            
            // Dice Type Label
            Text(
                text = diceType.displayName,
                color = CyberpunkColors.PrimaryText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Count Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onDecrement,
                    modifier = Modifier.size(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyberpunkColors.NeonRed
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("-", color = Color.White, fontSize = 16.sp)
                }
                
                Text(
                    text = count.toString(),
                    color = CyberpunkColors.NeonYellow,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )
                
                Button(
                    onClick = onIncrement,
                    modifier = Modifier.size(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CyberpunkColors.NeonGreen
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("+", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun ModifierSection(
    modifier: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
        Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CyberpunkColors.ElevatedCardBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = CyberpunkColors.BorderBlue,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MODIFIER",
            color = CyberpunkColors.NeonBlue,
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
                    containerColor = CyberpunkColors.NeonRed
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("-", color = Color.White, fontSize = 16.sp)
            }

            Text(
                text = if (modifier >= 0) "+$modifier" else "$modifier",
                color = CyberpunkColors.NeonYellow,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onIncrement,
                modifier = Modifier.size(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.NeonGreen
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("+", color = Color.White, fontSize = 16.sp)
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
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderYellow
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOTAL",
                color = CyberpunkColors.NeonYellow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            if (notation.isNotBlank()) {
                Text(
                    text = notation + (if (modifierText.isNotBlank()) " $modifierText" else ""),
                    color = CyberpunkColors.NeonBlue,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    text = "No dice selected",
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = "$totalDice dice",
                color = CyberpunkColors.PrimaryText,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            if (modifierValue != 0) {
                Text(
                    text = if (modifierValue > 0) "+$modifierValue modifier" else "$modifierValue modifier",
                    color = CyberpunkColors.NeonBlue,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
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

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderMagenta
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "RESULT",
                color = CyberpunkColors.NeonMagenta,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            
            currentResult?.let { result ->
                Text(
                    text = "${result.total}",
                    color = CyberpunkColors.NeonYellow,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "(${result.notation})",
                    color = CyberpunkColors.NeonBlue,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                )
                if (result.individualRolls.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    val breakdown = result.diceSelections.zip(result.individualRolls)
                        .joinToString("\n") { (sel, rolls) ->
                            if (sel.diceType == DiceType.D100 && result.d100Rolls.isNotEmpty()) {
                                // Show D10 breakdown for D100
                                val d100Roll = result.d100Rolls.first()
                                "${sel.diceType.displayName}: ${d100Roll.result} [${d100Roll.tensDie},${d100Roll.onesDie}]"
                            } else {
                                "${sel.diceType.displayName}: [${rolls.joinToString(", ")}]"
                            }
                        }
                    Text(
                        text = breakdown,
                        color = CyberpunkColors.SecondaryText,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } ?: run {
                Text(
                    text = "No rolls yet",
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ClearArenaButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = CyberpunkColors.NeonRed
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = CyberpunkColors.GlowRed
        )
    ) {
        Text(
            text = "CLEAR",
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
    val isEnabled = diceSelections.values.any { it.count > 0 }
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) CyberpunkColors.NeonOrange else CyberpunkColors.CardBackground,
            disabledContainerColor = CyberpunkColors.CardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = if (isEnabled) CyberpunkColors.GlowYellow else CyberpunkColors.BorderBlue
        )
    ) {
        Text(
            text = "ROLL",
            color = if (isEnabled) Color.White else CyberpunkColors.SecondaryText,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
} 