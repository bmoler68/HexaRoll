package com.brianmoler.hexaroll.utils

import android.content.Context
import com.brianmoler.hexaroll.data.AchievementProgress
import com.brianmoler.hexaroll.data.AchievementStats
import com.brianmoler.hexaroll.data.UserTitle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit


class AchievementStorage(private val context: Context) {
    
    private val sharedPreferences = context.getSharedPreferences(
        "hexaroll_achievements", 
        Context.MODE_PRIVATE
    )
    
    private val gson = Gson().newBuilder()
        .serializeNulls()
        .create()
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
            sharedPreferences.edit {
                putString(KEY_ACHIEVEMENT_PROGRESS, json)
                }
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
            sharedPreferences.edit {
                putString(KEY_USER_TITLES, json)
            }
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
            sharedPreferences.edit {
                putString(KEY_ACHIEVEMENT_STATS, json)
            }
            
            android.util.Log.d("AchievementStorage", "=== SAVING STATS ===")
            android.util.Log.d("AchievementStorage", "Stats object: $stats")
            android.util.Log.d("AchievementStorage", "sessionRolls value: ${stats.sessionRolls}")
            android.util.Log.d("AchievementStorage", "totalSessionRolls value: ${stats.totalSessionRolls}")
            android.util.Log.d("AchievementStorage", "Saved JSON: $json")
            
            // Check if sessionRolls appears in the JSON
            if (json.contains("sessionRolls")) {
                android.util.Log.d("AchievementStorage", "✅ sessionRolls found in JSON")
            } else {
                android.util.Log.d("AchievementStorage", "❌ sessionRolls NOT found in JSON")
            }
            
            // Check if totalSessionRolls appears in the JSON
            if (json.contains("totalSessionRolls")) {
                android.util.Log.d("AchievementStorage", "✅ totalSessionRolls found in JSON")
            } else {
                android.util.Log.d("AchievementStorage", "❌ totalSessionRolls NOT found in JSON")
            }
            
            // Log all field names in the JSON
            val jsonObject = com.google.gson.JsonParser.parseString(json).asJsonObject
            android.util.Log.d("AchievementStorage", "All fields in JSON: ${jsonObject.keySet()}")
        }
    }
    
    suspend fun loadAchievementStats(): AchievementStats {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_ACHIEVEMENT_STATS, null)
            if (json != null) {
                try {
                    android.util.Log.d("AchievementStorage", "=== LOADING STATS ===")
                    android.util.Log.d("AchievementStorage", "Loading JSON: $json")
                    
                    // Check if sessionRolls appears in the JSON
                    if (json.contains("sessionRolls")) {
                        android.util.Log.d("AchievementStorage", "✅ sessionRolls found in JSON")
                    } else {
                        android.util.Log.d("AchievementStorage", "❌ sessionRolls NOT found in JSON")
                    }
                    
                    // Check if totalSessionRolls appears in the JSON
                    if (json.contains("totalSessionRolls")) {
                        android.util.Log.d("AchievementStorage", "✅ totalSessionRolls found in JSON")
                    } else {
                        android.util.Log.d("AchievementStorage", "❌ totalSessionRolls NOT found in JSON")
                    }
                    
                    // Parse the JSON manually to see the values
                    val jsonObject = com.google.gson.JsonParser.parseString(json).asJsonObject
                    val sessionRollsFromJson = jsonObject.get("sessionRolls")?.asInt
                    val totalSessionRollsFromJson = jsonObject.get("totalSessionRolls")?.asInt
                    android.util.Log.d("AchievementStorage", "sessionRolls value from JSON: $sessionRollsFromJson")
                    android.util.Log.d("AchievementStorage", "totalSessionRolls value from JSON: $totalSessionRollsFromJson")
                    
                    val stats = gson.fromJson(json, achievementStatsType) ?: AchievementStats()
                    android.util.Log.d("AchievementStorage", "Loaded stats: totalRolls=${stats.totalRolls}, sessionRolls=${stats.sessionRolls}, totalSessionRolls=${stats.totalSessionRolls}")
                    android.util.Log.d("AchievementStorage", "Deserialization mismatch: JSON has sessionRolls=$sessionRollsFromJson, totalSessionRolls=$totalSessionRollsFromJson, but stats object has sessionRolls=${stats.sessionRolls}, totalSessionRolls=${stats.totalSessionRolls}")
                    stats
                } catch (e: Exception) {
                    android.util.Log.e("AchievementStorage", "Error loading stats", e)
                    AchievementStats()
                }
            } else {
                android.util.Log.d("AchievementStorage", "No stats found in storage")
                AchievementStats()
            }
        }
    }
    
    suspend fun saveUnlockedAchievements(unlockedIds: Set<String>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(unlockedIds)
            sharedPreferences.edit {
                putString(KEY_UNLOCKED_ACHIEVEMENTS, json)
            }
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
            sharedPreferences.edit {
                remove(KEY_ACHIEVEMENT_PROGRESS)
                    .remove(KEY_USER_TITLES)
                    .remove(KEY_ACHIEVEMENT_STATS)
                    .remove(KEY_UNLOCKED_ACHIEVEMENTS)
            }
        }
    }
} 