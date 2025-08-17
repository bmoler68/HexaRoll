package com.brianmoler.hexaroll.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.ui.components.DiceArena
import com.brianmoler.hexaroll.ui.theme.ThemeColorUtils
import com.brianmoler.hexaroll.ui.theme.ColorType
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

/**
 * RollScreen - The main dice rolling interface
 * 
 * This screen provides the primary dice rolling functionality including:
 * - Dice selection and counting
 * - Roll modifier controls
 * - Roll execution and result display
 * - Preset loading notifications
 * 
 * Features:
 * - Clean, focused UI for dice rolling
 * - Real-time feedback for preset loading
 * - Integration with DiceArena component
 */
@Composable
fun RollScreen(
    viewModel: DiceRollViewModel,
    modifier: Modifier = Modifier
) {
    val presetLoadedMessage by viewModel.presetLoadedMessage.collectAsState()
    val customization by viewModel.customization.collectAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Preset loaded message notification
        presetLoadedMessage?.let { message ->
            PresetLoadedNotification(
                message = message,
                onDismiss = { viewModel.clearPresetLoadedMessage() },
                customization = customization
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // Main dice rolling interface
        DiceArena(viewModel)
    }
}

/**
 * Notification component for when a preset is loaded
 * 
 * @param message The message to display
 * @param onDismiss Callback when the notification is dismissed
 * @param customization The current dice customization for consistent styling
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
