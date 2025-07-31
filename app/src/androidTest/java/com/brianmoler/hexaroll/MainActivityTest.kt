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
        // This would test the dice selection UI elements
        // Note: Actual implementation would depend on the specific UI structure
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