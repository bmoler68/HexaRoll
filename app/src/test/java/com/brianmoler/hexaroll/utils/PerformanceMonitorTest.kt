package com.brianmoler.hexaroll.utils

import org.junit.Test
import org.junit.Assert.*

class PerformanceMonitorTest {
    
    @Test
    fun `trackExecutionTime should return correct result`() {
        val result = PerformanceMonitor.trackExecutionTime("test operation") {
            "test result"
        }
        
        assertEquals("test result", result)
    }
    
    @Test
    fun `trackExecutionTime should handle exceptions`() {
        val exception = RuntimeException("test exception")
        
        try {
            PerformanceMonitor.trackExecutionTime("test operation") {
                throw exception
            }
            fail("Expected exception to be thrown")
        } catch (e: RuntimeException) {
            assertEquals(exception, e)
        }
    }
    
    @Test
    fun `trackStorageOperation should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackStorageOperation("test", 100)
    }
    
    @Test
    fun `trackAchievementOperation should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackAchievementOperation("test", "achievement_id")
    }
    
    @Test
    fun `trackRollOperation should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackRollOperation(3, listOf("D6", "D20"))
    }
    
    @Test
    fun `trackThemeChange should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackThemeChange("old_theme", "new_theme")
    }
    
    @Test
    fun `trackPresetOperation should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackPresetOperation("test", "preset_name")
    }
    
    @Test
    fun `trackHistoryOperation should not throw exception`() {
        // This test ensures the method doesn't throw exceptions
        PerformanceMonitor.trackHistoryOperation("test", 10)
    }
} 