package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brianmoler.hexaroll.data.PresetRoll
import com.brianmoler.hexaroll.data.RollResult
import com.brianmoler.hexaroll.ui.components.DiceArena
import com.brianmoler.hexaroll.ui.theme.CyberpunkColors
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: DiceRollViewModel
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    
    val tabs = listOf(
        TabItem(Icons.Filled.PlayArrow, "Roll"),
        TabItem(Icons.Filled.Edit, "Customize"),
        TabItem(Icons.Filled.Settings, "Presets"),
        TabItem(Icons.Filled.List, "History")
    )
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CyberpunkColors.DarkBackground)
    ) {
        // App Title
        AppTitle()

        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.background(CyberpunkColors.DarkerBackground),
            containerColor = Color.Transparent,
            contentColor = CyberpunkColors.PrimaryText
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (selectedTabIndex == index) CyberpunkColors.NeonYellow else CyberpunkColors.NeonBlue,
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
        }
    }
}

data class TabItem(
    val icon: ImageVector,
    val label: String
)

@Composable
fun RollScreen(viewModel: DiceRollViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
            text = "DICE CUSTOMIZATION",
            color = CyberpunkColors.NeonYellow,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CustomizationCard(
                    title = "DICE COLOR",
                    description = "Choose the color of your dice",
                    onClick = { /* TODO: Implement color picker */ }
                )
            }
            
            item {
                CustomizationCard(
                    title = "ARENA BACKGROUND",
                    description = "Select your dice arena background",
                    onClick = { /* TODO: Implement background picker */ }
                )
            }
            
            item {
                CustomizationCard(
                    title = "UNIQUE DICE",
                    description = "Customize special dice effects",
                    onClick = { /* TODO: Implement unique dice */ }
                )
            }
        }
    }
}

@Composable
fun CustomizationCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderBlue
        )
    ) {
        Button(
            onClick = onClick,
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
                        text = title,
                        color = CyberpunkColors.NeonYellow,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        color = CyberpunkColors.SecondaryText,
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "→",
                    color = CyberpunkColors.NeonBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PresetsScreen(viewModel: DiceRollViewModel) {
    val presetRolls by viewModel.presetRolls.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "PRESET ROLLS",
            color = CyberpunkColors.NeonYellow,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (presetRolls.isEmpty()) {
            Text(
                text = "No preset rolls available",
                color = CyberpunkColors.SecondaryText,
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
                items(presetRolls) { preset ->
                    PresetCard(
                        preset = preset,
                        onLoad = { viewModel.loadPresetRoll(preset) }
                    )
                }
            }
        }
    }
}

@Composable
fun PresetCard(
    preset: PresetRoll,
    onLoad: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderBlue
        )
    ) {
        Button(
            onClick = onLoad,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = preset.name,
                    color = CyberpunkColors.NeonYellow,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = preset.description,
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = buildPresetNotation(preset),
                    color = CyberpunkColors.NeonBlue,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
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
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "ROLL HISTORY",
            color = CyberpunkColors.NeonYellow,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (rollHistory.isEmpty()) {
            Text(
                text = "No rolls yet",
                color = CyberpunkColors.SecondaryText,
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
                    RollHistoryCard(roll = roll)
                }
            }
        }
    }
}

@Composable
fun RollHistoryCard(roll: RollResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ElevatedCardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.BorderBlue
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Result: ${roll.total}",
                        color = CyberpunkColors.NeonYellow,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "(${roll.notation})",
                        color = CyberpunkColors.NeonBlue,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = formatTimestamp(roll.timestamp),
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 12.sp
                )
            }
            
            // Show individual dice results
            if (roll.individualRolls.isNotEmpty()) {
                Text(
                    text = "Individual Rolls:",
                    color = CyberpunkColors.PrimaryText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                roll.individualRolls.forEachIndexed { index, rolls ->
                    val diceType = roll.diceSelections.getOrNull(index)?.diceType
                    if (diceType != null && rolls.isNotEmpty()) {
                        Text(
                            text = "${diceType.displayName}: ${rolls.joinToString(", ")}",
                            color = CyberpunkColors.SecondaryText,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
    return formatter.format(date)
}

@Composable
fun AppTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CyberpunkColors.DarkerBackground)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "HEXAROLL",
            color = CyberpunkColors.NeonYellow,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 3.sp
        )
    }
} 