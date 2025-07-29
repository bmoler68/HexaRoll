package com.brianmoler.hexaroll.utils

import android.util.Log
import com.brianmoler.hexaroll.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AchievementManager(private val achievementStorage: AchievementStorage) {
    
    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    private val _achievementStats = MutableStateFlow(AchievementStats())
    private val _unlockedAchievements = MutableStateFlow<Set<String>>(emptySet())
    private val _newlyUnlockedAchievements = MutableStateFlow<List<Achievement>>(emptyList())
    private val _userTitles = MutableStateFlow<List<UserTitle>>(emptyList())
    private val _completionPercentage = MutableStateFlow(0.0f)
    
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()
    val achievementStats: StateFlow<AchievementStats> = _achievementStats.asStateFlow()
    val unlockedAchievements: StateFlow<Set<String>> = _unlockedAchievements.asStateFlow()
    val newlyUnlockedAchievements: StateFlow<List<Achievement>> = _newlyUnlockedAchievements.asStateFlow()
    val userTitles: StateFlow<List<UserTitle>> = _userTitles.asStateFlow()
    val completionPercentage: StateFlow<Float> = _completionPercentage.asStateFlow()
    
    private val recentRolls = mutableListOf<RollResult>()
    private val recentRollTimes = mutableListOf<Long>()
    private val lastRollResults = mutableListOf<Int>()
    
    init {
        initializeAchievements()
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            loadAchievementData()
        }
    }
    
    private fun initializeAchievements() {
        _achievements.value = AchievementDefinitions.ALL_ACHIEVEMENTS
    }
    
    private suspend fun loadAchievementData() {
        try {
            val progressList = achievementStorage.loadAchievementProgress()
            val stats = achievementStorage.loadAchievementStats()
            val unlockedIds = achievementStorage.loadUnlockedAchievements()
            val titles = achievementStorage.loadUserTitles()
            
            _achievementStats.value = stats
            _unlockedAchievements.value = unlockedIds
            _userTitles.value = titles
            
            // Update achievements with progress
            updateAchievementsWithProgress(progressList)
            
            // Update achievement progress based on loaded stats
            updateAchievementProgress(stats)
            
            // Update completion percentage
            updateCompletionPercentage()
            
            // Save achievement data to persist restored progress
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                saveAchievementData()
            }
        } catch (e: Exception) {
            Log.e("AchievementManager", "Error loading achievement data", e)
        }
    }
    
    private fun updateAchievementsWithProgress(progressList: List<AchievementProgress>) {
        val progressMap = progressList.associateBy { it.achievementId }
        
        _achievements.value = _achievements.value.map { achievement ->
            val progress = progressMap[achievement.id]
            achievement.copy(
                isUnlocked = progress?.isUnlocked ?: false,
                progress = progress?.currentProgress ?: 0,
                unlockedAt = progress?.unlockedAt
            )
        }
    }
    
    private fun updateAchievementProgress(stats: AchievementStats) {
        _achievements.update { achievements ->
            achievements.map { achievement ->
                val progress = when (achievement.id) {
                    "first_roll" -> if (stats.totalRolls >= 1) 1 else 0
                    "roll_master_100" -> minOf(stats.totalRolls, 100)
                    "roll_master_500" -> minOf(stats.totalRolls, 500)
                    "roll_master_1000" -> minOf(stats.totalRolls, 1000)
                    "roll_master_5000" -> minOf(stats.totalRolls, 5000)
                    "roll_master_10000" -> minOf(stats.totalRolls, 10000)
                    "session_champion" -> minOf(stats.sessionRolls, 50)
                    "d4_devotee" -> minOf(stats.rollsByDiceType[DiceType.D4] ?: 0, 50)
                    "d6_specialist" -> minOf(stats.rollsByDiceType[DiceType.D6] ?: 0, 100)
                    "d8_enthusiast" -> minOf(stats.rollsByDiceType[DiceType.D8] ?: 0, 75)
                    "d10_master" -> minOf(stats.rollsByDiceType[DiceType.D10] ?: 0, 100)
                    "d12_expert" -> minOf(stats.rollsByDiceType[DiceType.D12] ?: 0, 50)
                    "d20_legend" -> minOf(stats.rollsByDiceType[DiceType.D20] ?: 0, 200)
                    "d30_pioneer" -> minOf(stats.rollsByDiceType[DiceType.D30] ?: 0, 25)
                    "d100_centurion" -> minOf(stats.rollsByDiceType[DiceType.D100] ?: 0, 50)
                    "polyhedral_master" -> stats.polyhedralDiceUsed.size
                    "high_roller" -> minOf(stats.perfectRolls, 10)
                    "low_baller" -> minOf(stats.minimumRolls, 10)
                    "average_joe" -> minOf(stats.averageRolls, 10)
                    "theme_explorer" -> stats.themeUsage.size
                    "theme_loyalist" -> stats.themeUsage.values.maxOrNull() ?: 0
                    "theme_switcher" -> minOf(stats.themeChanges, 10)
                    "preset_collector_5" -> minOf(stats.totalFavorites, 5)
                    "preset_collector_10" -> minOf(stats.totalFavorites, 10)
                    "preset_collector_25" -> minOf(stats.totalFavorites, 25)
                    "history_buff" -> minOf(stats.historyViews, 50)
                    "memory_master" -> minOf(stats.totalRolls, 50)
                    "weekend_warrior" -> minOf(stats.weeklyRolls.size, 50)
                    "daily_grinder" -> minOf(stats.dailyRolls.size, 7)
                    "monthly_master" -> minOf(stats.monthlyRolls.size, 30)
                    "balanced" -> minOf(stats.modifierUsageCount, 10)
                    else -> achievement.progress
                }
                
                // Auto-unlock achievement if progress is satisfied but achievement is locked
                val shouldBeUnlocked = progress >= achievement.maxProgress
                val isUnlocked = achievement.isUnlocked || shouldBeUnlocked
                val unlockedAt = if (shouldBeUnlocked && !achievement.isUnlocked) {
                    System.currentTimeMillis()
                } else {
                    achievement.unlockedAt
                }
                
                achievement.copy(
                    progress = progress,
                    isUnlocked = isUnlocked,
                    unlockedAt = unlockedAt
                )
            }
        }
        
        // Trigger unlock for any newly auto-unlocked achievements
        _achievements.value.forEach { achievement ->
            if (achievement.progress >= achievement.maxProgress && achievement.isUnlocked && 
                !_unlockedAchievements.value.contains(achievement.id)) {
                // This achievement was just auto-unlocked, add it to the unlocked set
                _unlockedAchievements.update { unlocked ->
                    unlocked + achievement.id
                }
                
                // Add to newly unlocked for popup
                _newlyUnlockedAchievements.update { newlyUnlocked ->
                    newlyUnlocked + achievement
                }
                
                // Save achievement data
                kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                    saveAchievementData()
                }
                
                // Update completion percentage
                updateCompletionPercentage()
                
                Log.d("AchievementManager", "Auto-unlocked achievement: ${achievement.name}")
            }
        }
    }
    
    suspend fun onRollCompleted(rollResult: RollResult, currentTheme: AppTheme) {
        val stats = _achievementStats.value
        
        // Update theme usage for the current theme
        val updatedThemeUsage = stats.themeUsage.toMutableMap()
        val currentThemeCount = updatedThemeUsage[currentTheme] ?: 0
        updatedThemeUsage[currentTheme] = currentThemeCount + 1
        
        val updatedStats = updateStatsAfterRoll(stats, rollResult).copy(
            themeUsage = updatedThemeUsage
        )
        _achievementStats.value = updatedStats
        
        // Track recent rolls for streak achievements
        recentRolls.add(rollResult)
        recentRollTimes.add(System.currentTimeMillis())
        if (recentRolls.size > 10) {
            recentRolls.removeAt(0)
            recentRollTimes.removeAt(0)
        }
        
        // Track last roll results for pattern achievements
        val totalRoll = rollResult.total
        lastRollResults.add(totalRoll)
        if (lastRollResults.size > 5) {
            lastRollResults.removeAt(0)
        }
        
        // Check for achievements
        checkRollingMilestoneAchievements(rollResult, updatedStats)
        checkDiceSpecialistAchievements(rollResult, updatedStats)
        checkResultBasedAchievements(rollResult, updatedStats)
        checkStreakPatternAchievements()
        checkCombinationModifierAchievements(rollResult, updatedStats)
        checkThemeBasedAchievements(updatedStats)
        checkFavoritesHistoryAchievements(updatedStats)
        checkSpecialEventAchievements(rollResult, updatedStats)
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        // Save updated stats
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    private fun updateStatsAfterRoll(stats: AchievementStats, rollResult: RollResult): AchievementStats {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val weekFormat = SimpleDateFormat("yyyy-'W'ww", Locale.getDefault())
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        
        val today = dateFormat.format(Date(currentTime))
        val thisWeek = weekFormat.format(Date(currentTime))
        val thisMonth = monthFormat.format(Date(currentTime))
        
        // Update dice type usage
        val updatedRollsByDiceType = stats.rollsByDiceType.toMutableMap()
        rollResult.diceSelections.forEach { selection ->
            val currentCount = updatedRollsByDiceType[selection.diceType] ?: 0
            updatedRollsByDiceType[selection.diceType] = currentCount + selection.count
        }
        
        // Update polyhedral dice used
        val updatedPolyhedralDiceUsed = stats.polyhedralDiceUsed.toMutableSet()
        rollResult.diceSelections.forEach { selection ->
            updatedPolyhedralDiceUsed.add(selection.diceType)
        }
        
        // Check for perfect rolls, minimum rolls, and average rolls
        var perfectRolls = stats.perfectRolls
        var minimumRolls = stats.minimumRolls
        var averageRolls = stats.averageRolls
        
        rollResult.individualRolls.forEachIndexed { index, rolls ->
            val diceType = rollResult.diceSelections.getOrNull(index)?.diceType
            if (diceType != null) {
                rolls.forEach { roll ->
                    when {
                        roll == diceType.sides -> perfectRolls++
                        roll == 1 -> minimumRolls++
                        roll == (diceType.sides + 1) / 2 -> averageRolls++
                    }
                }
            }
        }
        
        return stats.copy(
            totalRolls = stats.totalRolls + 1,
            rollsByDiceType = updatedRollsByDiceType,
            totalModifier = stats.totalModifier + rollResult.modifier,
            positiveModifier = stats.positiveModifier + (if (rollResult.modifier > 0) rollResult.modifier else 0),
            negativeModifier = stats.negativeModifier + (if (rollResult.modifier < 0) -rollResult.modifier else 0),
            modifierUsageCount = stats.modifierUsageCount + (if (rollResult.modifier != 0) 1 else 0),
            sessionRolls = stats.sessionRolls + 1,
            lastRollTime = currentTime,
            maxRollsInSession = maxOf(stats.maxRollsInSession, stats.sessionRolls + 1),
            polyhedralDiceUsed = updatedPolyhedralDiceUsed,
            perfectRolls = perfectRolls,
            minimumRolls = minimumRolls,
            averageRolls = averageRolls,
            dailyRolls = stats.dailyRolls.apply { add(today) },
            weeklyRolls = stats.weeklyRolls.apply { add(thisWeek) },
            monthlyRolls = stats.monthlyRolls.apply { add(thisMonth) }
        )
    }
    
    private fun checkRollingMilestoneAchievements(rollResult: RollResult, stats: AchievementStats) {
        
        // First Roll
        if (stats.totalRolls == 1) {
            unlockAchievement("first_roll")
        }
        
        // Roll Master achievements
        val rollMasterTargets = listOf(100, 500, 1000, 5000, 10000)
        rollMasterTargets.forEach { target ->
            if (stats.totalRolls >= target) {
                unlockAchievement("roll_master_$target")
            }
        }
        
        // Speed Demon - Check if 10 rolls in last 30 seconds
        val currentTime = System.currentTimeMillis()
        val recentRollsIn30Seconds = recentRollTimes.count { currentTime - it <= 30000 }
        if (recentRollsIn30Seconds >= 10) {
            unlockAchievement("speed_demon")
        }
        
        // Session Champion
        if (stats.sessionRolls >= 50) {
            unlockAchievement("session_champion")
        }
    }
    
    private fun checkDiceSpecialistAchievements(rollResult: RollResult, stats: AchievementStats) {
        
        // Check individual dice type achievements
        val diceTypeTargets = mapOf(
            "d4_devotee" to (DiceType.D4 to 50),
            "d6_specialist" to (DiceType.D6 to 100),
            "d8_enthusiast" to (DiceType.D8 to 75),
            "d10_master" to (DiceType.D10 to 100),
            "d12_expert" to (DiceType.D12 to 50),
            "d20_legend" to (DiceType.D20 to 200),
            "d30_pioneer" to (DiceType.D30 to 25),
            "d100_centurion" to (DiceType.D100 to 50)
        )
        
        diceTypeTargets.forEach { entry ->
            val achievementId = entry.key
            val (diceType, target) = entry.value
            val count = stats.rollsByDiceType[diceType] ?: 0
            if (count >= target) {
                unlockAchievement(achievementId)
            }
        }
        
        // Polyhedral Master
        if (stats.polyhedralDiceUsed.size >= 8) {
            unlockAchievement("polyhedral_master")
        }
    }
    
    private fun checkResultBasedAchievements(rollResult: RollResult, stats: AchievementStats) {
        // Check for specific result achievements
        rollResult.individualRolls.forEachIndexed { index, rolls ->
            val diceType = rollResult.diceSelections.getOrNull(index)?.diceType
            
            rolls.forEach { roll ->
                when {
                    diceType == DiceType.D6 && roll == 1 -> unlockAchievement("snake_eyes")
                    diceType == DiceType.D20 && roll == 20 -> unlockAchievement("natural_20")
                    diceType == DiceType.D20 && roll == 1 -> unlockAchievement("critical_fail")
                    diceType == DiceType.D100 && roll == 100 -> unlockAchievement("perfect_100")
                }
            }
        }
        
        // Check for 2D6 = 12 (Boxcars)
        if (rollResult.diceSelections.any { it.diceType == DiceType.D6 && it.count >= 2 }) {
            val d6Rolls = rollResult.individualRolls.find { rolls ->
                rollResult.diceSelections[rollResult.individualRolls.indexOf(rolls)]?.diceType == DiceType.D6
            }
            if (d6Rolls?.sum() == 12) {
                unlockAchievement("boxcars")
            }
        }
        
        // High Roller, Low Baller, Average Joe
        if (stats.perfectRolls >= 10) unlockAchievement("high_roller")
        if (stats.minimumRolls >= 10) unlockAchievement("low_baller")
        if (stats.averageRolls >= 10) unlockAchievement("average_joe")
    }
    
    private fun checkStreakPatternAchievements() {
        if (lastRollResults.size < 3) return
        
        // Hot Streak - Same number 3 times in a row
        if (lastRollResults.size >= 3) {
            val last3 = lastRollResults.takeLast(3)
            if (last3.distinct().size == 1) {
                unlockAchievement("hot_streak")
            }
        }
        
        // Cold Streak - Roll 1 three times in a row
        if (lastRollResults.size >= 3) {
            val last3 = lastRollResults.takeLast(3)
            if (last3.all { it == 1 }) {
                unlockAchievement("cold_streak")
            }
        }
        
        // Alternating pattern - high/low/high/low
        if (lastRollResults.size >= 4) {
            val last4 = lastRollResults.takeLast(4)
            val isAlternating = last4.withIndex().all { (index, value) ->
                when (index) {
                    0, 2 -> value > 10 // High
                    1, 3 -> value <= 10 // Low
                    else -> false
                }
            }
            if (isAlternating) {
                unlockAchievement("alternating")
            }
        }
        
        // Consistent - Within 2 points for 5 rolls
        if (lastRollResults.size >= 5) {
            val last5 = lastRollResults.takeLast(5)
            val min = last5.minOrNull() ?: 0
            val max = last5.maxOrNull() ?: 0
            if (max - min <= 2) {
                unlockAchievement("consistent")
            }
        }
    }
    
    private fun checkCombinationModifierAchievements(rollResult: RollResult, stats: AchievementStats) {
        
        // Modifier Master
        if (stats.positiveModifier >= 10) {
            unlockAchievement("modifier_master")
        }
        
        // Negative Nancy
        if (stats.negativeModifier >= 10) {
            unlockAchievement("negative_nancy")
        }
        
        // Extreme
        if (rollResult.modifier >= 20 || rollResult.modifier <= -20) {
            unlockAchievement("extreme")
        }
        
        // Mixed Bag
        if (rollResult.diceSelections.size >= 3) {
            unlockAchievement("mixed_bag")
        }
        
        // Dice Hoarder
        val totalDice = rollResult.diceSelections.sumOf { it.count }
        if (totalDice >= 10) {
            unlockAchievement("dice_hoarder")
        }
    }
    
    private fun checkThemeBasedAchievements(stats: AchievementStats) {
        
        // Theme Explorer
        if (stats.themeUsage.size >= 4) {
            unlockAchievement("theme_explorer")
        }
        
        // Theme Loyalist
        val maxThemeUsage = stats.themeUsage.values.maxOrNull() ?: 0
        if (maxThemeUsage >= 100) {
            unlockAchievement("theme_loyalist")
        }
        
        // Theme Switcher
        if (stats.themeChanges >= 10) {
            unlockAchievement("theme_switcher")
        }
    }
    
    private fun checkFavoritesHistoryAchievements(stats: AchievementStats) {
        
        // Preset Pioneer
        if (stats.totalFavorites >= 1) {
            unlockAchievement("preset_pioneer")
        }
        
        // Preset Collector
        val presetTargets = listOf(5, 10, 25)
        presetTargets.forEach { target ->
            if (stats.totalFavorites >= target) {
                unlockAchievement("preset_collector_$target")
            }
        }
        
        // History Buff
        if (stats.historyViews >= 50) {
            unlockAchievement("history_buff")
        }
        
        // Memory Master
        if (stats.totalRolls >= 50) {
            unlockAchievement("memory_master")
        }
    }
    
    private fun checkSpecialEventAchievements(rollResult: RollResult, stats: AchievementStats) {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply { timeInMillis = currentTime }
        
        // Midnight Roller
        if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0) {
            unlockAchievement("midnight_roller")
        }
        
        // Lucky Hour
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timeString = String.format("%02d:%02d", hour, minute)
        if (timeString in listOf("07:11", "11:11", "12:34", "13:37")) {
            unlockAchievement("lucky_hour")
        }
        
        // Weekend Warrior
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            // This would need to track weekend rolls specifically
        }
        
        // Daily Grinder
        if (stats.dailyRolls.size >= 7) {
            unlockAchievement("daily_grinder")
        }
        
        // Monthly Master
        if (stats.monthlyRolls.size >= 30) {
            unlockAchievement("monthly_master")
        }
    }
    
    private fun unlockAchievement(achievementId: String) {
        val achievement = _achievements.value.find { it.id == achievementId }
        if (achievement != null && !achievement.isUnlocked) {
            val updatedAchievement = achievement.copy(
                isUnlocked = true,
                unlockedAt = System.currentTimeMillis()
            )
            
            _achievements.update { achievements ->
                achievements.map { if (it.id == achievementId) updatedAchievement else it }
            }
            
            _unlockedAchievements.update { unlocked ->
                unlocked + achievementId
            }
            
            _newlyUnlockedAchievements.update { newlyUnlocked ->
                newlyUnlocked + updatedAchievement
            }
            
            // Save achievement data to persist unlock status
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                saveAchievementData()
            }
            
            // Update completion percentage
            updateCompletionPercentage()
            
            // Clear newly unlocked after a delay
            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                kotlinx.coroutines.delay(5000)
                _newlyUnlockedAchievements.value = emptyList()
            }
            
            Log.d("AchievementManager", "Unlocked achievement: ${achievement.name}")
        }
    }
    
    suspend fun onThemeChanged(newTheme: AppTheme) {
        val stats = _achievementStats.value
        val updatedThemeUsage = stats.themeUsage.toMutableMap()
        val currentThemeCount = updatedThemeUsage[newTheme] ?: 0
        updatedThemeUsage[newTheme] = currentThemeCount + 1
        
        val updatedStats = stats.copy(
            themeChanges = stats.themeChanges + 1,
            themeUsage = updatedThemeUsage
        )
        _achievementStats.value = updatedStats
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Check theme-based achievements
        checkThemeBasedAchievements(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    suspend fun onHistoryViewed() {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(historyViews = stats.historyViews + 1)
        _achievementStats.value = updatedStats
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    suspend fun onFavoriteCreated() {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(totalFavorites = stats.totalFavorites + 1)
        _achievementStats.value = updatedStats
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    suspend fun onFavoritesLoaded(favoritesCount: Int) {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(totalFavorites = favoritesCount)
        _achievementStats.value = updatedStats
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    suspend fun onThemeLoaded(theme: AppTheme) {
        val stats = _achievementStats.value
        val updatedThemeUsage = stats.themeUsage.toMutableMap()
        val currentThemeCount = updatedThemeUsage[theme] ?: 0
        updatedThemeUsage[theme] = currentThemeCount + 1
        
        val updatedStats = stats.copy(themeUsage = updatedThemeUsage)
        _achievementStats.value = updatedStats
        
        // Update achievement progress
        updateAchievementProgress(updatedStats)
        
        // Check theme-based achievements
        checkThemeBasedAchievements(updatedStats)
        
        // Save achievement data to persist progress
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            saveAchievementData()
        }
        
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    suspend fun saveAchievementData() {
        try {
            achievementStorage.saveAchievementProgress(_achievements.value.map { achievement ->
                AchievementProgress(
                    achievementId = achievement.id,
                    currentProgress = achievement.progress,
                    maxProgress = achievement.maxProgress,
                    isUnlocked = achievement.isUnlocked,
                    unlockedAt = achievement.unlockedAt
                )
            })
            achievementStorage.saveUnlockedAchievements(_unlockedAchievements.value)
            achievementStorage.saveUserTitles(_userTitles.value)
        } catch (e: Exception) {
            Log.e("AchievementManager", "Error saving achievement data", e)
        }
    }
    
    suspend fun resetAllProgress() {
        try {
            // Reset all achievements to locked state with zero progress
            _achievements.update { achievements ->
                achievements.map { achievement ->
                    achievement.copy(
                        isUnlocked = false,
                        progress = 0,
                        unlockedAt = null
                    )
                }
            }
            
            // Clear unlocked achievements set
            _unlockedAchievements.value = emptySet()
            
            // Clear newly unlocked achievements
            _newlyUnlockedAchievements.value = emptyList()
            
            // Reset achievement stats
            _achievementStats.value = AchievementStats()
            
            // Clear user titles
            _userTitles.value = emptyList()
            
            // Save the reset state
            saveAchievementData()
            achievementStorage.saveAchievementStats(_achievementStats.value)
            
            // Update completion percentage
            updateCompletionPercentage()
            
            // Add a success notification
            _newlyUnlockedAchievements.update { list ->
                list + Achievement(
                    id = "reset_success",
                    name = "Progress Reset",
                    description = "All achievement progress has been reset successfully",
                    category = AchievementCategory.SPECIAL_EVENTS,
                    tier = AchievementTier.BRONZE,
                    icon = "🔄",
                    isUnlocked = true,
                    progress = 1,
                    maxProgress = 1,
                    unlockedAt = System.currentTimeMillis(),
                    rarity = 0.0
                )
            }
            
            Log.d("AchievementManager", "All achievement progress has been reset")
        } catch (e: Exception) {
            Log.e("AchievementManager", "Error resetting achievement progress", e)
        }
    }
    
    fun getAchievementProgress(achievementId: String): AchievementProgress? {
        val achievement = _achievements.value.find { it.id == achievementId }
        return achievement?.let {
            AchievementProgress(
                achievementId = it.id,
                currentProgress = it.progress,
                maxProgress = it.maxProgress,
                isUnlocked = it.isUnlocked,
                unlockedAt = it.unlockedAt
            )
        }
    }
    
    fun getAchievementsByCategory(category: AchievementCategory): List<Achievement> {
        return _achievements.value.filter { it.category == category }
    }
    
    fun getUnlockedAchievements(): List<Achievement> {
        return _achievements.value.filter { it.isUnlocked }
    }
    
    private fun updateCompletionPercentage() {
        val total = _achievements.value.size
        val unlocked = _unlockedAchievements.value.size
        val percentage = if (total > 0) (unlocked.toFloat() / total) else 0.0f
        _completionPercentage.value = percentage
    }
} 