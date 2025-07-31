package com.brianmoler.hexaroll.utils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * Centralized error handling for the app
 */
object ErrorHandler {
    
    private const val TAG = "ErrorHandler"
    
    /**
     * Coroutine exception handler for background operations
     */
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Coroutine exception: ${throwable.message}", throwable)
        // In a production app, you would send this to crash reporting
    }
    
    /**
     * Handle storage errors
     */
    fun handleStorageError(context: Context, operation: String, error: Throwable) {
        Log.e(TAG, "Storage error during $operation: ${error.message}", error)
        // In a production app, you would show a user-friendly error message
    }
    
    /**
     * Handle data validation errors
     */
    fun handleValidationError(context: Context, field: String, value: Any?) {
        Log.w(TAG, "Validation error for $field: $value")
        // In a production app, you would show a user-friendly error message
    }
    
    /**
     * Validate dice count input
     */
    fun validateDiceCount(count: Int): Boolean {
        return count >= 0 && count <= 100 // Reasonable limit
    }
    
    /**
     * Validate modifier input
     */
    fun validateModifier(modifier: Int): Boolean {
        return modifier >= -1000 && modifier <= 1000 // Reasonable limit
    }
    
    /**
     * Validate preset name
     */
    fun validatePresetName(name: String): Boolean {
        return name.isNotBlank() && name.length <= 50
    }
    
    /**
     * Validate preset description
     */
    fun validatePresetDescription(description: String): Boolean {
        return description.length <= 200
    }
} 