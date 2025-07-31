package com.brianmoler.hexaroll.utils

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * Performance monitoring utility for tracking app performance
 */
object PerformanceMonitor {
    
    private const val TAG = "PerformanceMonitor"
    
    /**
     * Track execution time of a block of code
     */
    fun <T> trackExecutionTime(operation: String, block: () -> T): T {
        val startTime = System.currentTimeMillis()
        val result = block()
        val executionTime = System.currentTimeMillis() - startTime
        
        if (executionTime > 100) { // Log slow operations
            Log.w(TAG, "$operation took ${executionTime}ms")
        }
        
        return result
    }
    
    /**
     * Track execution time of a suspend function
     */
    suspend fun <T> trackSuspendExecutionTime(operation: String, block: () -> T): T {
        val startTime = System.currentTimeMillis()
        val result = block()
        val executionTime = System.currentTimeMillis() - startTime
        
        if (executionTime > 100) { // Log slow operations
            Log.w(TAG, "$operation took ${executionTime}ms")
        }
        
        return result
    }
    
    /**
     * Track memory usage
     */
    fun trackMemoryUsage(operation: String) {
        val runtime = Runtime.getRuntime()
        val usedMemory = runtime.totalMemory() - runtime.freeMemory()
        val maxMemory = runtime.maxMemory()
        val memoryUsagePercent = (usedMemory.toFloat() / maxMemory.toFloat()) * 100
        
        if (memoryUsagePercent > 80) { // Log high memory usage
            Log.w(TAG, "$operation: Memory usage ${memoryUsagePercent.toInt()}% (${usedMemory / 1024 / 1024}MB / ${maxMemory / 1024 / 1024}MB)")
        }
    }
    
    /**
     * Track storage operations
     */
    fun trackStorageOperation(operation: String, dataSize: Int) {
        Log.d(TAG, "$operation: Data size ${dataSize} bytes")
    }
    
    /**
     * Track achievement operations
     */
    fun trackAchievementOperation(operation: String, achievementId: String) {
        Log.d(TAG, "$operation: Achievement $achievementId")
    }
    
    /**
     * Track roll operations
     */
    fun trackRollOperation(diceCount: Int, diceTypes: List<String>) {
        Log.d(TAG, "Roll operation: $diceCount dice, types: ${diceTypes.joinToString(", ")}")
    }
    
    /**
     * Track theme changes
     */
    fun trackThemeChange(oldTheme: String, newTheme: String) {
        Log.d(TAG, "Theme change: $oldTheme -> $newTheme")
    }
    
    /**
     * Track preset operations
     */
    fun trackPresetOperation(operation: String, presetName: String) {
        Log.d(TAG, "$operation: Preset $presetName")
    }
    
    /**
     * Track history operations
     */
    fun trackHistoryOperation(operation: String, historySize: Int) {
        Log.d(TAG, "$operation: History size $historySize")
    }
} 