package com.brianmoler.hexaroll.utils

import android.content.Context
import com.brianmoler.hexaroll.data.RollResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RollHistoryStorage(private val context: Context) {
    
    private val sharedPreferences = context.getSharedPreferences(
        "hexaroll_history", 
        Context.MODE_PRIVATE
    )
    
    private val gson = Gson()
    private val rollHistoryListType = object : TypeToken<List<RollResult>>() {}.type
    
    companion object {
        private const val KEY_ROLL_HISTORY = "roll_history"
        private const val MAX_HISTORY_SIZE = 100
    }
    
    suspend fun saveRollHistory(rollHistory: List<RollResult>) {
        withContext(Dispatchers.IO) {
            // Ensure we only keep the latest 100 rolls
            val limitedHistory = rollHistory.take(MAX_HISTORY_SIZE)
            val json = gson.toJson(limitedHistory, rollHistoryListType)
            sharedPreferences.edit()
                .putString(KEY_ROLL_HISTORY, json)
                .apply()
        }
    }
    
    suspend fun loadRollHistory(): List<RollResult> {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_ROLL_HISTORY, null)
            if (json != null) {
                try {
                    gson.fromJson(json, rollHistoryListType) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }
    
    suspend fun clearRollHistory() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .remove(KEY_ROLL_HISTORY)
                .apply()
        }
    }
    
    suspend fun addRollToHistory(rollResult: RollResult) {
        withContext(Dispatchers.IO) {
            val currentHistory = loadRollHistory().toMutableList()
            // Add new roll at the beginning (most recent first)
            currentHistory.add(0, rollResult)
            // Keep only the latest 100 rolls
            val limitedHistory = currentHistory.take(MAX_HISTORY_SIZE)
            saveRollHistory(limitedHistory)
        }
    }
} 