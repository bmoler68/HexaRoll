package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.data.PresetRoll
import com.brianmoler.hexaroll.ui.theme.ThemeColorUtils
import com.brianmoler.hexaroll.ui.theme.ColorType
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

/**
 * PresetsScreen - Interface for managing dice roll presets
 * 
 * This screen allows users to:
 * - View saved preset configurations
 * - Load presets for quick dice rolling
 * - Edit preset names and descriptions
 * - Delete unwanted presets
 * 
 * Features:
 * - Clean preset management interface
 * - One-click preset loading
 * - In-place editing capabilities
 * - Confirmation dialogs for destructive actions
 */
@Composable
fun PresetsScreen(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val presetRolls by viewModel.presetRolls.collectAsState()
    val presetLoadedMessage by viewModel.presetLoadedMessage.collectAsState()
    val customization by viewModel.customization.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Screen title
        Text(
            text = stringResource(R.string.tab_presets),
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Preset loaded notification
        presetLoadedMessage?.let { message ->
            PresetLoadedNotification(
                message = message,
                onDismiss = { viewModel.clearPresetLoadedMessage() },
                customization = customization
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Content based on preset availability
        if (presetRolls.isEmpty()) {
            EmptyPresetsContent(customization)
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(presetRolls) { preset ->
                    PresetCard(
                        preset = preset,
                        onLoad = { viewModel.loadPresetRoll(preset) },
                        onEdit = { name, description -> 
                            viewModel.updatePreset(preset.id, name, description) 
                        },
                        onRemove = { viewModel.removePreset(preset.id) },
                        customization = customization
                    )
                }
            }
        }
    }
}

/**
 * Content displayed when no presets are available
 */
@Composable
private fun EmptyPresetsContent(customization: DiceCustomization) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_presets_available),
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Create favorites by rolling dice and using the 'Save to Favorites' button in the History tab",
            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * Notification card for when a preset is loaded
 */
@Composable
private fun PresetLoadedNotification(
    message: String,
    onDismiss: () -> Unit,
    customization: DiceCustomization
) {
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
            ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_GREEN)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDismiss) {
                Text(
                    "Dismiss",
                    color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE)
                )
            }
        }
    }
}

/**
 * Card component for individual presets
 */
@Composable
private fun PresetCard(
    preset: PresetRoll,
    onLoad: () -> Unit,
    onEdit: (String, String) -> Unit,
    onRemove: () -> Unit,
    customization: DiceCustomization
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Preset header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = preset.name,
                        color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (preset.description.isNotBlank()) {
                        Text(
                            text = preset.description,
                            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT),
                            fontSize = 14.sp
                        )
                    }
                }
                
                // Action buttons
                Row {
                    IconButton(onClick = onLoad) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Load Preset",
                            tint = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_GREEN)
                        )
                    }
                    IconButton(onClick = { showEditDialog = true }) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Preset",
                            tint = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_BLUE)
                        )
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete Preset",
                            tint = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_RED)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Preset configuration display
            Text(
                text = preset.diceSelections.joinToString(" + ") { selection ->
                    "${selection.count}${selection.diceType.displayName}"
                } + if (preset.modifier != 0) {
                    if (preset.modifier > 0) " +${preset.modifier}" else " ${preset.modifier}"
                } else "",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.PRIMARY_TEXT),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
    
    // Edit dialog
    if (showEditDialog) {
        PresetEditDialog(
            preset = preset,
            onConfirm = { name, description ->
                onEdit(name, description)
                showEditDialog = false
            },
            onDismiss = { showEditDialog = false },
            customization = customization
        )
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog) {
        PresetDeleteDialog(
            presetName = preset.name,
            onConfirm = {
                onRemove()
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false },
            customization = customization
        )
    }
}

/**
 * Dialog for editing preset details
 */
@Composable
private fun PresetEditDialog(
    preset: PresetRoll,
    onConfirm: (String, String) -> Unit,
    onDismiss: () -> Unit,
    customization: DiceCustomization
) {
    var name by remember { mutableStateOf(preset.name) }
    var description by remember { mutableStateOf(preset.description) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ThemeColorUtils.getCardBackgroundColor(
            customization.theme,
            customization.backgroundEnabled,
            customization.backgroundOpacity
        ),
        title = {
            Text(
                "Edit Preset",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_YELLOW)
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { 
                        Text(
                            "Name",
                            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT)
                        ) 
                    },
                    singleLine = true,
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
                    label = { 
                        Text(
                            "Description",
                            color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.SECONDARY_TEXT)
                        ) 
                    },
                    maxLines = 3,
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
 * Dialog for confirming preset deletion
 */
@Composable
private fun PresetDeleteDialog(
    presetName: String,
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
                "Delete Preset",
                color = ThemeColorUtils.getThemeColor(customization.theme, ColorType.NEON_RED)
            )
        },
        text = {
            Text(
                "Are you sure you want to delete \"$presetName\"? This action cannot be undone.",
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
                Text("Delete")
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
