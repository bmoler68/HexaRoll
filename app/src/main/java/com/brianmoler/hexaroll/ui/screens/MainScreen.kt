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
    val presetLoadedMessage by viewModel.presetLoadedMessage.collectAsState()
    
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
        
        // Preset loaded message
        presetLoadedMessage?.let { message ->
            PresetLoadedNotification(
                message = message,
                onDismiss = { viewModel.clearPresetLoadedMessage() }
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
                    text = "No preset rolls yet",
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Create presets by rolling dice and using the 'Save to Presets' button in the History tab",
                    color = CyberpunkColors.SecondaryText,
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
                        onRemove = { viewModel.removePreset(preset.id) }
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
    onRemove: () -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
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
            // Header with title and action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = preset.name,
                    color = CyberpunkColors.NeonYellow,
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
                            containerColor = CyberpunkColors.ButtonBlue
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
                            containerColor = CyberpunkColors.ButtonRed
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
                color = CyberpunkColors.SecondaryText,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            // Dice notation
            Text(
                text = buildPresetNotation(preset),
                color = CyberpunkColors.NeonBlue,
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
                    containerColor = CyberpunkColors.ButtonGreen
                )
            ) {
                Text(
                    text = "Load Preset",
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
            }
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
            }
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
                    RollHistoryCard(roll = roll, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun RollHistoryCard(
    roll: RollResult,
    viewModel: DiceRollViewModel
) {
    var showSaveDialog by remember { mutableStateOf(false) }
    var presetName by remember { mutableStateOf("") }
    var presetDescription by remember { mutableStateOf("") }
    
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
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formatTimestamp(roll.timestamp),
                        color = CyberpunkColors.SecondaryText,
                        fontSize = 12.sp
                    )
                    Button(
                        onClick = { showSaveDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberpunkColors.ButtonGreen
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = "Save to Presets",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                }
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
    
    // Save to Presets Dialog
    if (showSaveDialog) {
        SavePresetDialog(
            onDismiss = { showSaveDialog = false },
            onSave = { name, description ->
                viewModel.createPresetFromRoll(roll, name, description)
                showSaveDialog = false
            },
            defaultName = "Roll ${roll.notation}",
            defaultDescription = "Saved from roll history"
        )
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val date = java.util.Date(timestamp)
    val formatter = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
    return formatter.format(date)
}

@Composable
fun SavePresetDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    defaultName: String,
    defaultDescription: String
) {
    var name by remember { mutableStateOf(defaultName) }
    var description by remember { mutableStateOf(defaultDescription) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Save to Presets",
                color = CyberpunkColors.NeonYellow,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Preset Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberpunkColors.NeonBlue,
                        unfocusedBorderColor = CyberpunkColors.BorderBlue,
                        focusedLabelColor = CyberpunkColors.NeonBlue,
                        unfocusedLabelColor = CyberpunkColors.SecondaryText,
                        focusedTextColor = CyberpunkColors.PrimaryText,
                        unfocusedTextColor = CyberpunkColors.PrimaryText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberpunkColors.NeonBlue,
                        unfocusedBorderColor = CyberpunkColors.BorderBlue,
                        focusedLabelColor = CyberpunkColors.NeonBlue,
                        unfocusedLabelColor = CyberpunkColors.SecondaryText,
                        focusedTextColor = CyberpunkColors.PrimaryText,
                        unfocusedTextColor = CyberpunkColors.PrimaryText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, description) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonGreen
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonRed
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = CyberpunkColors.CardBackground,
        titleContentColor = CyberpunkColors.NeonYellow,
        textContentColor = CyberpunkColors.PrimaryText
    )
}

@Composable
fun EditPresetDialog(
    preset: PresetRoll,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(preset.name) }
    var description by remember { mutableStateOf(preset.description) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Preset",
                color = CyberpunkColors.NeonYellow,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Preset Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberpunkColors.NeonBlue,
                        unfocusedBorderColor = CyberpunkColors.BorderBlue,
                        focusedLabelColor = CyberpunkColors.NeonBlue,
                        unfocusedLabelColor = CyberpunkColors.SecondaryText,
                        focusedTextColor = CyberpunkColors.PrimaryText,
                        unfocusedTextColor = CyberpunkColors.PrimaryText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CyberpunkColors.NeonBlue,
                        unfocusedBorderColor = CyberpunkColors.BorderBlue,
                        focusedLabelColor = CyberpunkColors.NeonBlue,
                        unfocusedLabelColor = CyberpunkColors.SecondaryText,
                        focusedTextColor = CyberpunkColors.PrimaryText,
                        unfocusedTextColor = CyberpunkColors.PrimaryText
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(name, description) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonGreen
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonRed
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = CyberpunkColors.CardBackground,
        titleContentColor = CyberpunkColors.NeonYellow,
        textContentColor = CyberpunkColors.PrimaryText
    )
}

@Composable
fun DeletePresetDialog(
    presetName: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Delete Preset",
                color = CyberpunkColors.NeonRed,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete \"$presetName\"? This action cannot be undone.",
                color = CyberpunkColors.PrimaryText
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonRed
                )
            ) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyberpunkColors.ButtonBlue
                )
            ) {
                Text("Cancel")
            }
        },
        containerColor = CyberpunkColors.CardBackground,
        titleContentColor = CyberpunkColors.NeonRed,
        textContentColor = CyberpunkColors.PrimaryText
    )
}

@Composable
fun PresetLoadedNotification(
    message: String,
    onDismiss: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = CyberpunkColors.ButtonGreen.copy(alpha = 0.2f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CyberpunkColors.ButtonGreen
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
                    color = CyberpunkColors.ButtonGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp)
                )
                
                // Message
                Text(
                    text = message,
                    color = CyberpunkColors.PrimaryText,
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
                    color = CyberpunkColors.SecondaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
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