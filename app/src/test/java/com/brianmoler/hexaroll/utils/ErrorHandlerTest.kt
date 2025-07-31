package com.brianmoler.hexaroll.utils

import org.junit.Test
import org.junit.Assert.*

class ErrorHandlerTest {
    
    @Test
    fun `validateDiceCount should return true for valid counts`() {
        assertTrue(ErrorHandler.validateDiceCount(0))
        assertTrue(ErrorHandler.validateDiceCount(1))
        assertTrue(ErrorHandler.validateDiceCount(50))
        assertTrue(ErrorHandler.validateDiceCount(100))
    }
    
    @Test
    fun `validateDiceCount should return false for invalid counts`() {
        assertFalse(ErrorHandler.validateDiceCount(-1))
        assertFalse(ErrorHandler.validateDiceCount(101))
        assertFalse(ErrorHandler.validateDiceCount(1000))
    }
    
    @Test
    fun `validateModifier should return true for valid modifiers`() {
        assertTrue(ErrorHandler.validateModifier(-1000))
        assertTrue(ErrorHandler.validateModifier(-1))
        assertTrue(ErrorHandler.validateModifier(0))
        assertTrue(ErrorHandler.validateModifier(1))
        assertTrue(ErrorHandler.validateModifier(1000))
    }
    
    @Test
    fun `validateModifier should return false for invalid modifiers`() {
        assertFalse(ErrorHandler.validateModifier(-1001))
        assertFalse(ErrorHandler.validateModifier(1001))
    }
    
    @Test
    fun `validatePresetName should return true for valid names`() {
        assertTrue(ErrorHandler.validatePresetName("Test"))
        assertTrue(ErrorHandler.validatePresetName("My Preset"))
        assertTrue(ErrorHandler.validatePresetName("A".repeat(50)))
    }
    
    @Test
    fun `validatePresetName should return false for invalid names`() {
        assertFalse(ErrorHandler.validatePresetName(""))
        assertFalse(ErrorHandler.validatePresetName("   "))
        assertFalse(ErrorHandler.validatePresetName("A".repeat(51)))
    }
    
    @Test
    fun `validatePresetDescription should return true for valid descriptions`() {
        assertTrue(ErrorHandler.validatePresetDescription(""))
        assertTrue(ErrorHandler.validatePresetDescription("Test description"))
        assertTrue(ErrorHandler.validatePresetDescription("A".repeat(200)))
    }
    
    @Test
    fun `validatePresetDescription should return false for invalid descriptions`() {
        assertFalse(ErrorHandler.validatePresetDescription("A".repeat(201)))
    }
} 