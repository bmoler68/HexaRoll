package com.brianmoler.hexaroll

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.data.DiceSelection
import com.brianmoler.hexaroll.data.RollResult
import com.brianmoler.hexaroll.data.D100Roll

/**
 * Instrumented tests for HexaRoll application
 * 
 * These tests run on an Android device and verify:
 * - Application context and package information
 * - Data model functionality in the Android environment
 * - Basic dice rolling logic
 */
@RunWith(AndroidJUnit4::class)
class HexaRollInstrumentedTest {
    
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.brianmoler.hexaroll", appContext.packageName)
    }
    
    @Test
    fun testDiceTypeEnumInAndroidEnvironment() {
        // Test that DiceType enum works correctly in Android environment
        assertEquals(6, DiceType.D6.sides)
        assertEquals("D6", DiceType.D6.displayName)
        assertEquals(20, DiceType.D20.sides)
        assertEquals("D20", DiceType.D20.displayName)
        assertEquals(100, DiceType.D100.sides)
        assertEquals("D100", DiceType.D100.displayName)
    }
    
    @Test
    fun testDiceSelectionCreation() {
        // Test DiceSelection data class creation
        val selection = DiceSelection(DiceType.D8, 3)
        assertEquals(DiceType.D8, selection.diceType)
        assertEquals(3, selection.count)
        
        val defaultSelection = DiceSelection(DiceType.D12)
        assertEquals(0, defaultSelection.count)
    }
    
    @Test
    fun testD100RollCalculation() {
        // Test D100 roll calculation logic
        val roll = D100Roll(tensDie = 7, onesDie = 5)
        assertEquals(75, roll.result)
        
        val roll100 = D100Roll(tensDie = 0, onesDie = 0)
        assertEquals(100, roll100.result)
        
        val roll1 = D100Roll(tensDie = 0, onesDie = 1)
        assertEquals(1, roll1.result)
    }
    
    @Test
    fun testRollResultCreation() {
        // Test RollResult data class creation
        val diceSelections = listOf(DiceSelection(DiceType.D6, 2))
        val individualRolls = listOf(listOf(4, 6))
        val rollResult = RollResult(
            diceSelections = diceSelections,
            modifier = 3,
            individualRolls = individualRolls,
            total = 13
        )
        
        assertEquals(diceSelections, rollResult.diceSelections)
        assertEquals(3, rollResult.modifier)
        assertEquals(individualRolls, rollResult.individualRolls)
        assertEquals(13, rollResult.total)
        assertNotNull(rollResult.id)
        assertTrue(rollResult.timestamp > 0)
    }
    
    @Test
    fun testAllDiceTypesAvailable() {
        // Verify all expected dice types are available
        val expectedTypes = listOf(
            DiceType.D4, DiceType.D6, DiceType.D8, DiceType.D10,
            DiceType.D12, DiceType.D20, DiceType.D30, DiceType.D100
        )
        
        assertEquals(8, expectedTypes.size)
        assertEquals(8, DiceType.entries.size)
        
        expectedTypes.forEach { expectedType ->
            assertTrue("${expectedType.name} should be available", 
                      DiceType.entries.contains(expectedType))
        }
    }
}