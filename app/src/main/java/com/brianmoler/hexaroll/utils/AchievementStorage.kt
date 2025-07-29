package com.brianmoler.hexaroll.utils

import android.content.Context
import android.content.SharedPreferences
import com.brianmoler.hexaroll.data.Achievement
import com.brianmoler.hexaroll.data.AchievementProgress
import com.brianmoler.hexaroll.data.AchievementStats
import com.brianmoler.hexaroll.data.UserTitle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AchievementStorage(private val context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "hexaroll_achievements", 
        Context.MODE_PRIVATE
    )
    
    private val gson = Gson()
    private val achievementProgressListType = object : TypeToken<List<AchievementProgress>>() {}.type
    private val userTitlesListType = object : TypeToken<List<UserTitle>>() {}.type
    private val achievementStatsType = object : TypeToken<AchievementStats>() {}.type
    
    companion object {
        private const val KEY_ACHIEVEMENT_PROGRESS = "achievement_progress"
        private const val KEY_USER_TITLES = "user_titles"
        private const val KEY_ACHIEVEMENT_STATS = "achievement_stats"
        private const val KEY_UNLOCKED_ACHIEVEMENTS = "unlocked_achievements"
    }
    
    suspend fun saveAchievementProgress(progressList: List<AchievementProgress>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(progressList, achievementProgressListType)
            sharedPreferences.edit()
                .putString(KEY_ACHIEVEMENT_PROGRESS, json)
                .apply()
        }
    }
    
    suspend fun loadAchievementProgress(): List<AchievementProgress> {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_ACHIEVEMENT_PROGRESS, null)
            if (json != null) {
                try {
                    gson.fromJson(json, achievementProgressListType) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }
    
    suspend fun saveUserTitles(titles: List<UserTitle>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(titles, userTitlesListType)
            sharedPreferences.edit()
                .putString(KEY_USER_TITLES, json)
                .apply()
        }
    }
    
    suspend fun loadUserTitles(): List<UserTitle> {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_USER_TITLES, null)
            if (json != null) {
                try {
                    gson.fromJson(json, userTitlesListType) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }
    
    suspend fun saveAchievementStats(stats: AchievementStats) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(stats, achievementStatsType)
            sharedPreferences.edit()
                .putString(KEY_ACHIEVEMENT_STATS, json)
                .apply()
        }
    }
    
    suspend fun loadAchievementStats(): AchievementStats {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_ACHIEVEMENT_STATS, null)
            if (json != null) {
                try {
                    gson.fromJson(json, achievementStatsType) ?: AchievementStats()
                } catch (e: Exception) {
                    AchievementStats()
                }
            } else {
                AchievementStats()
            }
        }
    }
    
    suspend fun saveUnlockedAchievements(unlockedIds: Set<String>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(unlockedIds)
            sharedPreferences.edit()
                .putString(KEY_UNLOCKED_ACHIEVEMENTS, json)
                .apply()
        }
    }
    
    suspend fun loadUnlockedAchievements(): Set<String> {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_UNLOCKED_ACHIEVEMENTS, null)
            if (json != null) {
                try {
                    gson.fromJson(json, object : TypeToken<Set<String>>() {}.type) ?: emptySet()
                } catch (e: Exception) {
                    emptySet()
                }
            } else {
                emptySet()
            }
        }
    }
    
    suspend fun clearAllAchievementData() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit()
                .remove(KEY_ACHIEVEMENT_PROGRESS)
                .remove(KEY_USER_TITLES)
                .remove(KEY_ACHIEVEMENT_STATS)
                .remove(KEY_UNLOCKED_ACHIEVEMENTS)
                .apply()
        }
    }
} 