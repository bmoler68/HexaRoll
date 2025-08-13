package com.brianmoler.hexaroll

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for MainActivity using Compose Test
 * 
 * Tests the main user interface functionality including:
 * - App launch and main screen display
 * - Tab navigation between different screens
 * - Dice selection and manipulation
 * - Basic UI element presence and interaction
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    @Test
    fun testAppLaunchesSuccessfully() {
        // Verify the app launches and shows the main screen
        composeTestRule.onNodeWithText("Dice Arena").assertExists()
        composeTestRule.onNodeWithText("Dice Arena").assertIsDisplayed()
    }
    
    @Test
    fun testTabNavigation() {
        // Test navigation between all tabs
        // Start with Dice Arena (should be default)
        composeTestRule.onNodeWithText("Dice Arena").assertExists()
        
        // Navigate to Customization tab
        composeTestRule.onNodeWithText("Customization").performClick()
        composeTestRule.onNodeWithText("Customization").assertExists()
        
        // Navigate to Presets tab
        composeTestRule.onNodeWithText("Presets").performClick()
        composeTestRule.onNodeWithText("Presets").assertExists()
        
        // Navigate to History tab
        composeTestRule.onNodeWithText("History").performClick()
        composeTestRule.onNodeWithText("History").assertExists()
        
        // Navigate to Achievements tab
        composeTestRule.onNodeWithText("Achievements").performClick()
        composeTestRule.onNodeWithText("Achievements").assertExists()
        
        // Navigate to Settings tab
        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.onNodeWithText("Settings").assertExists()
        
        // Return to Dice Arena
        composeTestRule.onNodeWithText("Dice Arena").performClick()
        composeTestRule.onNodeWithText("Dice Arena").assertExists()
    }
    
    @Test
    fun testDiceSelection() {
        // Test dice selection functionality
        // Start with D6 dice (first in the grid)
        val d6Card = composeTestRule.onNodeWithText("D6")
        d6Card.assertExists()
        d6Card.assertIsDisplayed()
        
        // Find the increment and decrement buttons for D6
        val incrementButton = composeTestRule.onNodeWithText("+")
        val decrementButton = composeTestRule.onNodeWithText("-")
        
        incrementButton.assertExists()
        decrementButton.assertExists()
        
        // Initially count should be 0
        composeTestRule.onNodeWithText("0").assertExists()
        
        // Click increment button to add a D6
        incrementButton.performClick()
        
        // Count should now be 1
        composeTestRule.onNodeWithText("1").assertExists()
        
        // Click increment again to add another D6
        incrementButton.performClick()
        
        // Count should now be 2
        composeTestRule.onNodeWithText("2").assertExists()
        
        // Click decrement to remove one D6
        decrementButton.performClick()
        
        // Count should be back to 1
        composeTestRule.onNodeWithText("1").assertExists()
        
        // Click decrement again to remove the last D6
        decrementButton.performClick()
        
        // Count should be back to 0
        composeTestRule.onNodeWithText("0").assertExists()
        
        // Test that decrement doesn't go below 0
        decrementButton.performClick()
        composeTestRule.onNodeWithText("0").assertExists()
    }
    
    @Test
    fun testAllDiceTypesArePresent() {
        // Verify all dice types are displayed
        val expectedDiceTypes = listOf("D4", "D6", "D8", "D10", "D12", "D20", "D30", "D100")
        
        expectedDiceTypes.forEach { diceType ->
            composeTestRule.onNodeWithText(diceType).assertExists()
            composeTestRule.onNodeWithText(diceType).assertIsDisplayed()
        }
    }
    
    @Test
    fun testRollButtonPresence() {
        // Verify the roll button is present
        composeTestRule.onNodeWithText("Roll Dice").assertExists()
        composeTestRule.onNodeWithText("Roll Dice").assertIsDisplayed()
    }
    
    @Test
    fun testModifierControls() {
        // Verify modifier controls are present
        composeTestRule.onNodeWithText("+").assertExists()
        composeTestRule.onNodeWithText("-").assertExists()
        
        // Should be able to find modifier value display
        // This might be "0" or similar depending on the UI
        composeTestRule.onNode(hasText("0")).assertExists()
    }
    
    @Test
    fun testSettingsScreenContent() {
        // Navigate to Settings tab
        composeTestRule.onNodeWithText("Settings").performClick()
        
        // Verify Settings screen content
        composeTestRule.onNodeWithText("About HexaRoll").assertExists()
        composeTestRule.onNodeWithText("Privacy Policy").assertExists()
        
        // Verify app information is displayed
        composeTestRule.onNodeWithText("HexaRoll").assertExists()
        composeTestRule.onNodeWithText("Version 1.0.0").assertExists()
    }
    
    @Test
    fun testAchievementsScreenContent() {
        // Navigate to Achievements tab
        composeTestRule.onNodeWithText("Achievements").performClick()
        
        // Verify Achievements screen loads
        // The exact content depends on achievement state, but the tab should be accessible
        composeTestRule.onNodeWithText("Achievements").assertExists()
    }
    
    @Test
    fun testPresetsScreenContent() {
        // Navigate to Presets tab
        composeTestRule.onNodeWithText("Presets").performClick()
        
        // Verify Presets screen loads
        composeTestRule.onNodeWithText("Presets").assertExists()
    }
    
    @Test
    fun testHistoryScreenContent() {
        // Navigate to History tab
        composeTestRule.onNodeWithText("History").performClick()
        
        // Verify History screen loads
        composeTestRule.onNodeWithText("History").assertExists()
    }
    
    @Test
    fun testCustomizationScreenContent() {
        // Navigate to Customization tab
        composeTestRule.onNodeWithText("Customization").performClick()
        
        // Verify Customization screen loads
        composeTestRule.onNodeWithText("Customization").assertExists()
    }
} 