package com.brianmoler.hexaroll

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    @Test
    fun testAppLaunchesSuccessfully() {
        // Verify the app launches and shows the main screen
        composeTestRule.onNodeWithText("Dice Arena").assertExists()
    }
    
    @Test
    fun testTabNavigation() {
        // Test navigation between tabs
        composeTestRule.onNodeWithText("Customization").performClick()
        composeTestRule.onNodeWithText("Customization").assertExists()
        
        composeTestRule.onNodeWithText("Presets").performClick()
        composeTestRule.onNodeWithText("Presets").assertExists()
        
        composeTestRule.onNodeWithText("History").performClick()
        composeTestRule.onNodeWithText("History").assertExists()
        
        composeTestRule.onNodeWithText("Achievements").performClick()
        composeTestRule.onNodeWithText("Achievements").assertExists()
    }
    
    @Test
    fun testDiceSelection() {
        // Test dice selection functionality
        // Start with D6 dice (first in the grid)
        val d6Card = composeTestRule.onNodeWithText("D6")
        d6Card.assertExists()
        
        // Find the increment button for D6 (the "+" button)
        val incrementButton = composeTestRule.onNodeWithText("+")
        val decrementButton = composeTestRule.onNodeWithText("-")
        
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
    fun testThemeSelection() {
        // Navigate to customization tab
        composeTestRule.onNodeWithText("Customization").performClick()
        
        // Test theme selection
        // This would test the theme selection UI elements
        // Note: Actual implementation would depend on the specific UI structure
    }
} 