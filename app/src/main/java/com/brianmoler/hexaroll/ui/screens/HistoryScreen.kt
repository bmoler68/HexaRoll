package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.data.RollResult
import com.brianmoler.hexaroll.ui.theme.ThemeColorUtils
import com.brianmoler.hexaroll.ui.theme.ColorType
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * HistoryScreen - Interface for viewing dice roll history
 * 
 * This screen allows users to:
 * - View previous dice roll results
 * - Create presets from past rolls
 * - Clear roll history
 * - See detailed roll breakdowns
 * 
 * Features:
 * - Chronological roll history display
 * - One-click preset creation from history
 * - Detailed roll information including individual dice results
 * - Clean, organized interface with theme consistency
 */
@Composable
fun HistoryScreen(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val rollHistory by viewModel.rollHistory.collectAsState()
    val customization by viewModel.customization.collectAsState()
    var showClearDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Screen header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.tab_history),
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Clear history button
            if (rollHistory.isNotEmpty()) {
                Button(
                    onClick = { showClearDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC62828), // Dark Red
                        contentColor = Color.White
                    )
                ) {
                    Text("Clear")
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Content based on history availability
        if (rollHistory.isEmpty()) {
            EmptyHistoryContent(customization)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(rollHistory) { rollResult ->
                    HistoryCard(
                        rollResult = rollResult,
                        onCreatePreset = { name, description ->
                            viewModel.createPresetFromRoll(rollResult, name, description)
                        },
                        customization = customization
                    )
                }
            }
        }
    }
    
    // Clear history confirmation dialog
    if (showClearDialog) {
        ClearHistoryDialog(
            onConfirm = {
                viewModel.clearRollHistory()
                showClearDialog = false
            },
            onDismiss = { showClearDialog = false },
            customization = customization
        )
    }
}

/**
 * Content displayed when no roll history is available
 */
@Composable
private fun EmptyHistoryContent(customization: DiceCustomization) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No Roll History",
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Start rolling dice to see your history here",
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * Card component for individual roll history entries
 */
@Composable
private fun HistoryCard(
    rollResult: RollResult,
    onCreatePreset: (String, String) -> Unit,
    customization: DiceCustomization
) {
    var showPresetDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = ThemeColorUtils.getCardBackgroundColor(
                customization.theme,
                customization.backgroundEnabled,
                customization.backgroundOpacity
            )
        ),
        border = BorderStroke(
            1.dp,
            ThemeColorUtils.getThemeColor(customization.theme, ColorType.BORDER_BLUE)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Left column: Result and dice notation
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Result
                Text(
                    text = "Result: ${rollResult.total}",
                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Dice notation in blue parentheses format
                val diceNotation = rollResult.diceSelections
                    .filter { it.count > 0 }
                    .joinToString("+") { "${it.count}${it.diceType.displayName}" }
                
                Text(
                    text = "($diceNotation)",
                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Individual dice results
                if (rollResult.individualRolls.isNotEmpty()) {
                    Text(
                        text = "Individual Results:",
                        color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    rollResult.diceSelections.forEachIndexed { index, selection ->
                        if (index < rollResult.individualRolls.size) {
                            val rolls = rollResult.individualRolls[index]
                            
                            // Special formatting for D100 dice
                            if (selection.diceType == DiceType.D100) {
                                // Find corresponding D100 rolls for this selection
                                val d100Count = selection.count
                                val startIndex = rollResult.diceSelections
                                    .take(index)
                                    .filter { it.diceType == DiceType.D100 }
                                    .sumOf { it.count }
                                
                                val d100RollsForThisSelection = rollResult.d100Rolls
                                    .drop(startIndex)
                                    .take(d100Count)
                                
                                val formattedResults = d100RollsForThisSelection.map { d100Roll ->
                                    "${d100Roll.result} [${d100Roll.tensDie},${d100Roll.onesDie}]"
                                }
                                
                                Text(
                                    text = "${selection.diceType.displayName}: ${formattedResults.joinToString(", ")}",
                                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            } else {
                                // Standard formatting for other dice
                                Text(
                                    text = "${selection.diceType.displayName}: ${rolls.joinToString(", ")}",
                                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
            
            // Right column: Fixed width for timestamp and save button
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(140.dp)
            ) {
                Text(
                    text = formatTimestamp(rollResult.timestamp),
                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
                    fontSize = 11.sp,
                    textAlign = TextAlign.End
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Save button positioned below timestamp
                Button(
                    onClick = { showPresetDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32), // Dark Green
                        contentColor = Color.White
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
    
    // Preset creation dialog
    if (showPresetDialog) {
        PresetCreationDialog(
            rollResult = rollResult,
            onConfirm = { name, description ->
                onCreatePreset(name, description)
                showPresetDialog = false
            },
            onDismiss = { showPresetDialog = false },
            customization = customization
        )
    }
}

/**
 * Dialog for creating a preset from a roll result
 */
@Composable
private fun PresetCreationDialog(
    rollResult: RollResult,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit,
    customization: DiceCustomization
) {
    // Generate default name and description from roll result
    val diceList = rollResult.diceSelections
        .filter { it.count > 0 }
        .joinToString("+") { "${it.count}${it.diceType.displayName}" }
    val defaultName = "Roll $diceList"
    val defaultDescription = "Saved as favorite from roll history"
    
    var name by remember { mutableStateOf(defaultName) }
    var description by remember { mutableStateOf(defaultDescription) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ThemeColorUtils.getCardBackgroundColor(
            customization.theme,
            customization.backgroundEnabled,
            customization.backgroundOpacity
        ),
        title = {
            Text(
                "Create Preset",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW)
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Preset Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                        unfocusedTextColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                        focusedBorderColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE),
                        unfocusedBorderColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.BORDER_BLUE),
                        focusedLabelColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE),
                        unfocusedLabelColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
                        cursorColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE)
                    )
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                        unfocusedTextColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                        focusedBorderColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE),
                        unfocusedBorderColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.BORDER_BLUE),
                        focusedLabelColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE),
                        unfocusedLabelColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
                        cursorColor = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(name, description) },
                enabled = name.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32), // Dark Green
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF757575), // Gray
                    disabledContentColor = Color(0xFFBDBDBD) // Light Gray
                )
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0), // Dark Blue
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Dialog for confirming history clear operation
 */
@Composable
private fun ClearHistoryDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    customization: DiceCustomization
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ThemeColorUtils.getCardBackgroundColor(
            customization.theme,
            customization.backgroundEnabled,
            customization.backgroundOpacity
        ),
        title = {
            Text(
                "Clear History",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_RED)
            )
        },
        text = {
            Text(
                "Are you sure you want to clear all roll history? This action cannot be undone.",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFC62828), // Dark Red
                    contentColor = Color.White
                )
            ) {
                Text("Clear")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1565C0), // Dark Blue
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

/**
 * Format timestamp for display
 */
private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}
