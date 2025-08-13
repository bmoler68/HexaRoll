package com.brianmoler.hexaroll.data

import org.junit.Test
import org.junit.Assert.*
import java.util.UUID

/**
 * Unit tests for HexaRoll data models
 * 
 * Tests the data classes and enums that form the foundation
 * of the application's data structure.
 */
class DataModelsTest {
    
    @Test
    fun `DiceType enum should have correct properties`() {
        // Test all dice types have correct sides and display names
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
    fun `DiceType entries should contain all types`() {
        val entries = DiceType.entries
        assertEquals(8, entries.size)
        
        val expectedTypes = listOf(
            DiceType.D4, DiceType.D6, DiceType.D8, DiceType.D10,
            DiceType.D12, DiceType.D20, DiceType.D30, DiceType.D100
        )
        
        expectedTypes.forEach { expectedType ->
            assertTrue("${expectedType.name} should be in entries", entries.contains(expectedType))
        }
    }
    
    @Test
    fun `DiceSelection should work with default constructor`() {
        val selection = DiceSelection(DiceType.D6)
        assertEquals(DiceType.D6, selection.diceType)
        assertEquals(0, selection.count)
    }
    
    @Test
    fun `DiceSelection should work with custom count`() {
        val selection = DiceSelection(DiceType.D20, 5)
        assertEquals(DiceType.D20, selection.diceType)
        assertEquals(5, selection.count)
    }
    
    @Test
    fun `DiceSelection should be immutable`() {
        val selection = DiceSelection(DiceType.D8, 3)
        val modifiedSelection = selection.copy(count = 7)
        
        assertEquals(3, selection.count)
        assertEquals(7, modifiedSelection.count)
        assertEquals(DiceType.D8, modifiedSelection.diceType)
    }
    
    @Test
    fun `D100Roll should calculate result correctly`() {
        // Test various combinations
        assertEquals(1, D100Roll(tensDie = 0, onesDie = 1).result)
        assertEquals(10, D100Roll(tensDie = 1, onesDie = 0).result)
        assertEquals(25, D100Roll(tensDie = 2, onesDie = 5).result)
        assertEquals(99, D100Roll(tensDie = 9, onesDie = 9).result)
        assertEquals(100, D100Roll(tensDie = 0, onesDie = 0).result)
    }
    
    @Test
    fun `D100Roll should handle edge cases`() {
        // Test boundary values
        assertEquals(1, D100Roll(tensDie = 0, onesDie = 1).result)
        assertEquals(10, D100Roll(tensDie = 1, onesDie = 0).result)
        assertEquals(90, D100Roll(tensDie = 9, onesDie = 0).result)
        assertEquals(99, D100Roll(tensDie = 9, onesDie = 9).result)
        assertEquals(100, D100Roll(tensDie = 0, onesDie = 0).result)
    }
    
    @Test
    fun `RollResult should generate unique IDs`() {
        val result1 = RollResult(
            diceSelections = listOf(DiceSelection(DiceType.D6, 1)),
            modifier = 0,
            individualRolls = listOf(listOf(3)),
            total = 3
        )
        
        val result2 = RollResult(
            diceSelections = listOf(DiceSelection(DiceType.D6, 1)),
            modifier = 0,
            individualRolls = listOf(listOf(4)),
            total = 4
        )
        
        assertNotEquals("Roll results should have different IDs", result1.id, result2.id)
        assertNotNull("ID should not be null", result1.id)
        assertNotNull("ID should not be null", result2.id)
    }
    
    @Test
    fun `RollResult should have valid timestamp`() {
        val beforeCreation = System.currentTimeMillis()
        val result = RollResult(
            diceSelections = listOf(DiceSelection(DiceType.D6, 1)),
            modifier = 0,
            individualRolls = listOf(listOf(3)),
            total = 3
        )
        val afterCreation = System.currentTimeMillis()
        
        assertTrue("Timestamp should be within reasonable range", 
                  result.timestamp >= beforeCreation && result.timestamp <= afterCreation)
    }
    
    @Test
    fun `PresetRoll should generate unique IDs`() {
        val preset1 = PresetRoll(
            name = "Test 1",
            description = "Description 1",
            diceSelections = listOf(DiceSelection(DiceType.D6, 2)),
            modifier = 0
        )
        
        val preset2 = PresetRoll(
            name = "Test 2",
            description = "Description 2",
            diceSelections = listOf(DiceSelection(DiceType.D20, 1)),
            modifier = 5
        )
        
        assertNotEquals("Preset rolls should have different IDs", preset1.id, preset2.id)
        assertNotNull("ID should not be null", preset1.id)
        assertNotNull("ID should not be null", preset2.id)
    }
    
    @Test
    fun `PresetRoll should store all properties correctly`() {
        val diceSelections = listOf(
            DiceSelection(DiceType.D6, 3),
            DiceSelection(DiceType.D20, 1)
        )
        
        val preset = PresetRoll(
            name = "Combat Roll",
            description = "Standard combat roll with D6 and D20",
            diceSelections = diceSelections,
            modifier = 2
        )
        
        assertEquals("Combat Roll", preset.name)
        assertEquals("Standard combat roll with D6 and D20", preset.description)
        assertEquals(diceSelections, preset.diceSelections)
        assertEquals(2, preset.modifier)
        assertEquals(2, preset.diceSelections.size)
    }
    
    @Test
    fun `data classes should support equality`() {
        val selection1 = DiceSelection(DiceType.D6, 3)
        val selection2 = DiceSelection(DiceType.D6, 3)
        val selection3 = DiceSelection(DiceType.D6, 4)
        
        assertEquals("Equal selections should be equal", selection1, selection2)
        assertNotEquals("Different selections should not be equal", selection1, selection3)
    }
    
    @Test
    fun `data classes should support copying`() {
        val original = DiceSelection(DiceType.D8, 2)
        val copied = original.copy(count = 5)
        
        assertEquals(DiceType.D8, copied.diceType)
        assertEquals(5, copied.count)
        assertEquals(2, original.count) // Original should be unchanged
    }
}
