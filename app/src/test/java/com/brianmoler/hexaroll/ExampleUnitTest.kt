package com.brianmoler.hexaroll

import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.data.DiceSelection
import com.brianmoler.hexaroll.data.RollResult
import com.brianmoler.hexaroll.data.PresetRoll
import com.brianmoler.hexaroll.data.D100Roll
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for HexaRoll core data models and functionality
 * 
 * These tests verify the behavior of data classes and enums
 * that form the foundation of the application.
 */
class HexaRollUnitTest {
    
    @Test
    fun `DiceType enum should have correct sides and display names`() {
        assertEquals(4, DiceType.D4.sides)
        assertEquals("D4", DiceType.D4.displayName)
        
        assertEquals(6, DiceType.D6.sides)
        assertEquals("D6", DiceType.D6.displayName)
        
        assertEquals(8, DiceType.D8.sides)
        assertEquals("D8", DiceType.D8.displayName)
        
        assertEquals(10, DiceType.D10.sides)
        assertEquals("D10", DiceType.D10.displayName)
        
        assertEquals(12, DiceType.D12.sides)
        assertEquals("D12", DiceType.D12.displayName)
        
        assertEquals(20, DiceType.D20.sides)
        assertEquals("D20", DiceType.D20.displayName)
        
        assertEquals(30, DiceType.D30.sides)
        assertEquals("D30", DiceType.D30.displayName)
        
        assertEquals(100, DiceType.D100.sides)
        assertEquals("D100", DiceType.D100.displayName)
    }
    
    @Test
    fun `DiceType entries should contain all dice types`() {
        val expectedTypes = listOf(
            DiceType.D4, DiceType.D6, DiceType.D8, DiceType.D10,
            DiceType.D12, DiceType.D20, DiceType.D30, DiceType.D100
        )
        
        assertEquals(expectedTypes.size, DiceType.entries.size)
        expectedTypes.forEach { expectedType ->
            assertTrue("${expectedType.name} should be in entries", 
                      DiceType.entries.contains(expectedType))
        }
    }
    
    @Test
    fun `DiceSelection should create with correct values`() {
        val selection = DiceSelection(DiceType.D6, 3)
        assertEquals(DiceType.D6, selection.diceType)
        assertEquals(3, selection.count)
    }
    
    @Test
    fun `DiceSelection should use default count of 0`() {
        val selection = DiceSelection(DiceType.D20)
        assertEquals(0, selection.count)
    }
    
    @Test
    fun `D100Roll should calculate result correctly`() {
        val roll = D100Roll(tensDie = 5, onesDie = 3)
        assertEquals(53, roll.result)
    }
    
    @Test
    fun `D100Roll with tens 0 should represent 100`() {
        val roll = D100Roll(tensDie = 0, onesDie = 0)
        assertEquals(100, roll.result)
    }
    
    @Test
    fun `PresetRoll should create with correct values`() {
        val diceSelections = listOf(DiceSelection(DiceType.D6, 2))
        val preset = PresetRoll(
            name = "Test Preset",
            description = "Test Description",
            diceSelections = diceSelections,
            modifier = 5
        )
        
        assertEquals("Test Preset", preset.name)
        assertEquals("Test Description", preset.description)
        assertEquals(diceSelections, preset.diceSelections)
        assertEquals(5, preset.modifier)
        assertNotNull(preset.id)
    }
    
    @Test
    fun `RollResult should create with correct values`() {
        val diceSelections = listOf(DiceSelection(DiceType.D6, 1))
        val individualRolls = listOf(listOf(4))
        val rollResult = RollResult(
            diceSelections = diceSelections,
            modifier = 2,
            individualRolls = individualRolls,
            total = 6,
            notation = "1D6+2"
        )
        
        assertEquals(diceSelections, rollResult.diceSelections)
        assertEquals(2, rollResult.modifier)
        assertEquals(individualRolls, rollResult.individualRolls)
        assertEquals(6, rollResult.total)
        assertEquals("1D6+2", rollResult.notation)
        assertNotNull(rollResult.id)
        assertTrue(rollResult.timestamp > 0)
    }
    
    @Test
    fun `RollResult should handle D100 rolls correctly`() {
        val diceSelections = listOf(DiceSelection(DiceType.D100, 1))
        val individualRolls = listOf(listOf(75))
        val d100Rolls = listOf(D100Roll(tensDie = 7, onesDie = 5))
        val rollResult = RollResult(
            diceSelections = diceSelections,
            modifier = 0,
            individualRolls = individualRolls,
            d100Rolls = d100Rolls,
            total = 75,
            notation = "1D100"
        )
        
        assertEquals(1, rollResult.d100Rolls.size)
        assertEquals(75, rollResult.d100Rolls.first().result)
        assertEquals(75, rollResult.total)
    }
}