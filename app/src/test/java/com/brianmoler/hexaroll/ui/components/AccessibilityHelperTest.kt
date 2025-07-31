package com.brianmoler.hexaroll.ui.components

import com.brianmoler.hexaroll.data.DiceType
import org.junit.Test
import org.junit.Assert.*

class AccessibilityHelperTest {
    
    @Test
    fun `getDiceButtonDescription should return correct description for count 0`() {
        val description = AccessibilityHelper.getDiceButtonDescription(DiceType.D6, 0)
        assertEquals("Add D6", description)
    }
    
    @Test
    fun `getDiceButtonDescription should return correct description for count 1`() {
        val description = AccessibilityHelper.getDiceButtonDescription(DiceType.D20, 1)
        assertEquals("1 D20 selected", description)
    }
    
    @Test
    fun `getDiceButtonDescription should return correct description for count greater than 1`() {
        val description = AccessibilityHelper.getDiceButtonDescription(DiceType.D10, 5)
        assertEquals("5 D10s selected", description)
    }
    
    @Test
    fun `getModifierButtonDescription should return correct description for modifier 0`() {
        val description = AccessibilityHelper.getModifierButtonDescription(0)
        assertEquals("No modifier", description)
    }
    
    @Test
    fun `getModifierButtonDescription should return correct description for positive modifier`() {
        val description = AccessibilityHelper.getModifierButtonDescription(5)
        assertEquals("Modifier: +5", description)
    }
    
    @Test
    fun `getModifierButtonDescription should return correct description for negative modifier`() {
        val description = AccessibilityHelper.getModifierButtonDescription(-3)
        assertEquals("Modifier: -3", description)
    }
    
    @Test
    fun `getRollButtonDescription should return correct description for no dice`() {
        val description = AccessibilityHelper.getRollButtonDescription(0, 0)
        assertEquals("No dice selected", description)
    }
    
    @Test
    fun `getRollButtonDescription should return correct description for 1 die`() {
        val description = AccessibilityHelper.getRollButtonDescription(1, 0)
        assertEquals("Roll 1 die", description)
    }
    
    @Test
    fun `getRollButtonDescription should return correct description for multiple dice`() {
        val description = AccessibilityHelper.getRollButtonDescription(3, 0)
        assertEquals("Roll 3 dice", description)
    }
    
    @Test
    fun `getRollButtonDescription should include positive modifier`() {
        val description = AccessibilityHelper.getRollButtonDescription(2, 5)
        assertEquals("Roll 2 dice with +5 modifier", description)
    }
    
    @Test
    fun `getRollButtonDescription should include negative modifier`() {
        val description = AccessibilityHelper.getRollButtonDescription(1, -2)
        assertEquals("Roll 1 die with -2 modifier", description)
    }
    
    @Test
    fun `getThemeButtonDescription should return correct description`() {
        val description = AccessibilityHelper.getThemeButtonDescription("Cyberpunk")
        assertEquals("Switch to Cyberpunk theme", description)
    }
    
    @Test
    fun `getPresetButtonDescription should return correct description`() {
        val description = AccessibilityHelper.getPresetButtonDescription("My Preset")
        assertEquals("Load preset: My Preset", description)
    }
    
    @Test
    fun `getAchievementButtonDescription should return correct description`() {
        val description = AccessibilityHelper.getAchievementButtonDescription("Test Achievement", 3, 10)
        assertEquals("Test Achievement achievement: 3 out of 10", description)
    }
} 