package com.brianmoler.hexaroll.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.brianmoler.hexaroll.data.AchievementProgress
import com.brianmoler.hexaroll.data.AchievementStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AchievementStorage(private val context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("achievements", Context.MODE_PRIVATE)
    private val gson = Gson()
    
    // Type tokens for JSON serialization
    private val achievementProgressListType = object : TypeToken<List<AchievementProgress>>() {}.type
    private val achievementStatsType = object : TypeToken<AchievementStats>() {}.type
    private val unlockedAchievementsSetType = object : TypeToken<Set<String>>() {}.type
    
    // Achievement Progress Storage
    suspend fun saveAchievementProgress(progressList: List<AchievementProgress>) {
        withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(progressList, achievementProgressListType)
                sharedPreferences.edit().putString("achievement_progress", json).apply()
            } catch (e: Exception) {
                // Handle serialization error
            }
        }
    }
    
    suspend fun loadAchievementProgress(): List<AchievementProgress> {
        return withContext(Dispatchers.IO) {
            try {
                val json = sharedPreferences.getString("achievement_progress", "[]")
                gson.fromJson(json, achievementProgressListType) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    
    // Achievement Stats Storage
    suspend fun saveAchievementStats(stats: AchievementStats) {
        withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(stats, achievementStatsType)
                sharedPreferences.edit().putString("achievement_stats", json).apply()
            } catch (e: Exception) {
                // Handle serialization error
            }
        }
    }
    
    suspend fun loadAchievementStats(): AchievementStats {
        return withContext(Dispatchers.IO) {
            try {
                val json = sharedPreferences.getString("achievement_stats", "{}")
                gson.fromJson(json, achievementStatsType) ?: AchievementStats()
            } catch (e: Exception) {
                AchievementStats()
            }
        }
    }
    
    // Unlocked Achievements Storage
    suspend fun saveUnlockedAchievements(unlockedIds: Set<String>) {
        withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(unlockedIds, unlockedAchievementsSetType)
                sharedPreferences.edit().putString("unlocked_achievements", json).apply()
            } catch (e: Exception) {
                // Handle serialization error
            }
        }
    }
    
    suspend fun loadUnlockedAchievements(): Set<String> {
        return withContext(Dispatchers.IO) {
            try {
                val json = sharedPreferences.getString("unlocked_achievements", "[]")
                gson.fromJson(json, unlockedAchievementsSetType) ?: emptySet()
            } catch (e: Exception) {
                emptySet()
            }
        }
    }
    

}