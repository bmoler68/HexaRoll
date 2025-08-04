package com.brianmoler.hexaroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import com.brianmoler.hexaroll.ui.screens.MainScreen
import com.brianmoler.hexaroll.ui.theme.HexaRollTheme
import com.brianmoler.hexaroll.viewmodel.DiceRollViewModel

/**
 * MainActivity - Entry point for the HexaRoll dice rolling application
 * 
 * This activity serves as the main container for the Compose UI and manages
 * the application lifecycle. It sets up the theme, ViewModel, and main screen.
 * 
 * Features:
 * - Edge-to-edge display support
 * - Material 3 theming
 * - ViewModel integration for state management
 * - Compose-based UI architecture
 */
class MainActivity : ComponentActivity() {
    
    /**
     * Called when the activity is first created
     * 
     * Sets up the Compose UI with the following components:
     * - Enables edge-to-edge display for modern Android devices
     * - Applies the HexaRoll theme
     * - Creates and injects the DiceRollViewModel
     * - Sets up the main screen with proper padding
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display for immersive experience
        enableEdgeToEdge()
        
        // Set up the Compose UI content
        setContent {
            // Apply the HexaRoll theme to all child composables
            HexaRollTheme {
                // Create and inject the ViewModel for state management
                val viewModel: DiceRollViewModel = viewModel()
                
                // Main scaffold that provides the basic app structure
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Main screen with proper padding to account for system UI
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}