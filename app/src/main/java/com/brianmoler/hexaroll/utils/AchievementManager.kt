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
    
    private var isDataLoaded = false
    
    private val recentRolls = mutableListOf<RollResult>()
    private val recentRollTimes = mutableListOf<Long>()
    private val lastRollResults = mutableListOf<Int>()
    private val lastRollDiceTypes = mutableListOf<DiceType>()
    
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
            
            Log.d("AchievementManager", "=== LOADING ACHIEVEMENT DATA ===")
            Log.d("AchievementManager", "Raw stats from storage: $stats")
            Log.d("AchievementManager", "sessionRolls from storage: ${stats.sessionRolls}")
            
            // Reset session-based stats on app start (single session tracking)
            val statsWithResetSession = stats.copy(sessionRolls = 0, themeChanges = 0)
            _achievementStats.value = statsWithResetSession
            _unlockedAchievements.value = unlockedIds
            _userTitles.value = titles
            
            Log.d("AchievementManager", "Loaded stats on startup: totalRolls=${stats.totalRolls}, d6 rolls=${stats.rollsByDiceType[DiceType.D6]}, sessionRolls=${stats.sessionRolls}, totalSessionRolls=${stats.totalSessionRolls}")
            Log.d("AchievementManager", "After session reset: sessionRolls=0, totalSessionRolls=${stats.totalSessionRolls}")
            
            // Update achievements with progress from storage (no recalculation on startup)
            updateAchievementsWithProgress(progressList)
            
            // Update completion percentage
            updateCompletionPercentage()
            
            // Mark data as loaded
            isDataLoaded = true
            
            Log.d("AchievementManager", "Loaded achievement data: ${progressList.size} progress entries, ${stats.totalRolls} total rolls")
            Log.d("AchievementManager", "Loaded stats - totalRolls: ${stats.totalRolls}, d6 rolls: ${stats.rollsByDiceType[DiceType.D6]}")
            
            // Debug: Log specific achievement progress being loaded
            progressList.filter { it.achievementId == "roll_master_100" || it.achievementId == "d6_specialist" }
                .forEach { progress ->
                    Log.d("AchievementManager", "Loading ${progress.achievementId}: progress=${progress.currentProgress}, unlocked=${progress.isUnlocked}")
                }
            
        } catch (e: Exception) {
            Log.e("AchievementManager", "Error loading achievement data", e)
        }
    }
    
    private fun updateAchievementsWithProgress(progressList: List<AchievementProgress>) {
        val progressMap = progressList.associateBy { it.achievementId }
        
        _achievements.value = _achievements.value.map { achievement ->
            val progress = progressMap[achievement.id]
            
            // For session-based achievements, always start with 0 progress on app restart
            val isSessionBasedAchievement = achievement.id == "session_champion" || achievement.id == "theme_switcher"
            val restoredProgress = if (isSessionBasedAchievement) {
                0 // Session achievements should always start at 0
            } else {
                progress?.currentProgress ?: 0
            }
            
            val isUnlocked = progress?.isUnlocked ?: false
            val unlockedAt = progress?.unlockedAt
            
            // Debug logging for restoration
            if (achievement.id == "roll_master_100" || achievement.id == "d6_specialist" || achievement.id == "session_champion" || achievement.id == "theme_switcher") {
                Log.d("AchievementManager", "Restoring ${achievement.id}: progress=$restoredProgress, unlocked=$isUnlocked (session-based: $isSessionBasedAchievement)")
            }
            
            // If achievement is unlocked, add to unlocked set (but don't auto-unlock on startup)
            if (isUnlocked && !_unlockedAchievements.value.contains(achievement.id)) {
                _unlockedAchievements.update { unlocked ->
                    unlocked + achievement.id
                }
            }
            
            achievement.copy(
                isUnlocked = isUnlocked,
                progress = restoredProgress,
                unlockedAt = unlockedAt
            )
        }
        
        Log.d("AchievementManager", "Restored progress for ${progressList.size} achievements")
        
        // Debug: Log final achievement state after restoration
        _achievements.value.filter { it.id == "roll_master_100" || it.id == "d6_specialist" || it.id == "session_champion" || it.id == "theme_switcher" }
            .forEach { achievement ->
                Log.d("AchievementManager", "After restoration ${achievement.id}: progress=${achievement.progress}, unlocked=${achievement.isUnlocked}")
            }
    }
    
    private fun updateAchievementProgressAfterRoll(stats: AchievementStats) {
        _achievements.update { achievements ->
            achievements.map { achievement ->
                // Calculate what the progress should be based on current stats
                val calculatedProgress = when (achievement.id) {
                    "first_roll" -> if (stats.totalRolls >= 1) 1 else 0
                    "roll_master_100" -> minOf(stats.totalRolls, 100)
                    "roll_master_500" -> minOf(stats.totalRolls, 500)
                    "roll_master_1000" -> minOf(stats.totalRolls, 1000)
                    "roll_master_5000" -> minOf(stats.totalRolls, 5000)
                    "roll_master_10000" -> minOf(stats.totalRolls, 10000)
                    "session_champion" -> minOf(stats.sessionRolls, 50) // Single session achievement
                    "persistent_roller" -> minOf(stats.totalSessionRolls, 100) // Cross-session achievement
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
                    "theme_explorer" -> minOf(stats.themeUsage.size, 5)
                    "theme_loyalist" -> minOf(stats.themeUsage.values.maxOrNull() ?: 0, 100)
                    "theme_switcher" -> minOf(stats.themeChanges, 10)
                    "preset_collector_5" -> minOf(stats.totalFavorites, 5)
                    "preset_collector_10" -> minOf(stats.totalFavorites, 10)
                    "preset_collector_25" -> minOf(stats.totalFavorites, 25)
                    "history_buff" -> minOf(stats.historyViews, 50)
                    "memory_master" -> minOf(stats.totalRolls, 50)
                    "weekend_warrior" -> minOf(stats.weekendRolls.size, 50)
                    "daily_grinder" -> minOf(stats.dailyRolls.size, 7)
                    "monthly_master" -> minOf(stats.monthlyRolls.size, 30)
                    "balanced" -> minOf(stats.modifierUsageCount, 10)
                    else -> achievement.progress
                }
                
                // Only increment progress if calculated progress is greater than persisted value
                val finalProgress = if (calculatedProgress > achievement.progress) {
                    Log.d("AchievementManager", "${achievement.id}: Progress increased from ${achievement.progress} to $calculatedProgress")
                    calculatedProgress
                } else {
                    achievement.progress
                }
                
                // Debug logging for progress tracking
                if (achievement.id == "roll_master_100" || achievement.id == "d6_specialist" || achievement.id == "session_champion" || achievement.id == "persistent_roller" || achievement.id == "history_buff" || achievement.id == "preset_collector_5" || achievement.id == "preset_collector_10" || achievement.id == "preset_collector_25" || achievement.id == "theme_loyalist" || achievement.id == "theme_explorer" || achievement.id == "theme_switcher") {
                    Log.d("AchievementManager", "${achievement.id}: calculated=$calculatedProgress, persisted=${achievement.progress}, final=$finalProgress")
                }
                
                // Special debug for Theme Explorer
                if (achievement.id == "theme_explorer") {
                    Log.d("AchievementManager", "Theme Explorer: themeUsage.size=${stats.themeUsage.size}, themeUsage=${stats.themeUsage}")
                }
                
                // Check if achievement should be unlocked based on final progress
                val shouldBeUnlocked = finalProgress >= achievement.maxProgress
                val isUnlocked = achievement.isUnlocked || shouldBeUnlocked
                val unlockedAt = if (shouldBeUnlocked && !achievement.isUnlocked) {
                    System.currentTimeMillis()
                } else {
                    achievement.unlockedAt
                }
                
                achievement.copy(
                    progress = finalProgress,
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
                
                Log.d("AchievementManager", "Auto-unlocked achievement: ${achievement.name}")
            }
        }
        
        // Update completion percentage
        updateCompletionPercentage()
    }
    
    suspend fun onRollCompleted(rollResult: RollResult, currentTheme: AppTheme) {
        val stats = _achievementStats.value
        
        Log.d("AchievementManager", "=== ROLL COMPLETED ===")
        Log.d("AchievementManager", "Stats before roll: $stats")
        Log.d("AchievementManager", "sessionRolls before roll: ${stats.sessionRolls}")
        Log.d("AchievementManager", "totalSessionRolls before roll: ${stats.totalSessionRolls}")
        
        // Update theme usage for the current theme
        val updatedThemeUsage = stats.themeUsage.toMutableMap()
        val currentThemeCount = updatedThemeUsage[currentTheme] ?: 0
        updatedThemeUsage[currentTheme] = currentThemeCount + 1
        
        Log.d("AchievementManager", "Roll completed with theme: $currentTheme. Theme usage updated: ${currentThemeCount} -> ${currentThemeCount + 1}")
        Log.d("AchievementManager", "Theme Explorer progress: ${updatedThemeUsage.size}/5 themes used")
        
        val updatedStats = updateStatsAfterRoll(stats, rollResult).copy(
            themeUsage = updatedThemeUsage
        )
        _achievementStats.value = updatedStats
        
        Log.d("AchievementManager", "Stats after roll: $updatedStats")
        Log.d("AchievementManager", "sessionRolls after roll: ${updatedStats.sessionRolls}")
        Log.d("AchievementManager", "totalSessionRolls after roll: ${updatedStats.totalSessionRolls}")
        
        Log.d("AchievementManager", "After roll - totalRolls: ${updatedStats.totalRolls}, d6 rolls: ${updatedStats.rollsByDiceType[DiceType.D6]}, sessionRolls: ${updatedStats.sessionRolls}")
        Log.d("AchievementManager", "Stats before roll: totalRolls=${stats.totalRolls}, d6 rolls=${stats.rollsByDiceType[DiceType.D6]}, sessionRolls=${stats.sessionRolls}")
        Log.d("AchievementManager", "Stats after roll: totalRolls=${updatedStats.totalRolls}, d6 rolls=${updatedStats.rollsByDiceType[DiceType.D6]}, sessionRolls=${updatedStats.sessionRolls}")
        
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
        
        // Track dice types for unpredictable achievement
        // Get the primary dice type (first dice selection)
        val primaryDiceType = rollResult.diceSelections.firstOrNull()?.diceType
        if (primaryDiceType != null) {
            lastRollDiceTypes.add(primaryDiceType)
            if (lastRollDiceTypes.size > 5) {
                lastRollDiceTypes.removeAt(0)
            }
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
        
        // Update achievement progress after roll
        Log.d("AchievementManager", "About to update achievement progress with stats: totalRolls=${updatedStats.totalRolls}")
        updateAchievementProgressAfterRoll(updatedStats)
        
        // Save achievement data to persist progress (ONLY after rolls)
        Log.d("AchievementManager", "About to save achievement data after roll")
        saveAchievementData()
        
        // Save updated stats
        Log.d("AchievementManager", "About to save stats: sessionRolls=${updatedStats.sessionRolls}, totalSessionRolls=${updatedStats.totalSessionRolls}")
        achievementStorage.saveAchievementStats(updatedStats)
    }
    
    private fun updateStatsAfterRoll(stats: AchievementStats, rollResult: RollResult): AchievementStats {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val weekFormat = SimpleDateFormat("yyyy-'W'ww", Locale.US)
        val monthFormat = SimpleDateFormat("yyyy-MM", Locale.US)
        
        val today = dateFormat.format(Date(currentTime))
        val thisWeek = weekFormat.format(Date(currentTime))
        val thisMonth = monthFormat.format(Date(currentTime))
        
        // Check if today is a weekend (Saturday = 7, Sunday = 1)
        val isWeekend = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
                       calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
        
        Log.d("AchievementManager", "Weekend check: dayOfWeek=${calendar.get(Calendar.DAY_OF_WEEK)}, isWeekend=$isWeekend")
        
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
        
        val newStats = stats.copy(
            totalRolls = stats.totalRolls + 1,
            rollsByDiceType = updatedRollsByDiceType,
            totalModifier = stats.totalModifier + rollResult.modifier,
            positiveModifier = stats.positiveModifier + (if (rollResult.modifier > 0) rollResult.modifier else 0),
            negativeModifier = stats.negativeModifier + (if (rollResult.modifier < 0) -rollResult.modifier else 0),
            modifierUsageCount = stats.modifierUsageCount + (if (rollResult.modifier != 0) 1 else 0),
            sessionRolls = stats.sessionRolls + 1, // Single session
            totalSessionRolls = stats.totalSessionRolls + 1, // Cross-session
            lastRollTime = currentTime,
            maxRollsInSession = maxOf(stats.maxRollsInSession, stats.sessionRolls + 1),
            polyhedralDiceUsed = updatedPolyhedralDiceUsed,
            perfectRolls = perfectRolls,
            minimumRolls = minimumRolls,
            averageRolls = averageRolls,
            dailyRolls = stats.dailyRolls.apply { add(today) },
            weeklyRolls = stats.weeklyRolls.apply { add(thisWeek) },
            monthlyRolls = stats.monthlyRolls.apply { add(thisMonth) },
            weekendRolls = if (isWeekend) stats.weekendRolls.apply { add(today) } else stats.weekendRolls
        )
        
        Log.d("AchievementManager", "updateStatsAfterRoll: old totalRolls=${stats.totalRolls}, new totalRolls=${newStats.totalRolls}")
        Log.d("AchievementManager", "updateStatsAfterRoll: old d6 rolls=${stats.rollsByDiceType[DiceType.D6]}, new d6 rolls=${newStats.rollsByDiceType[DiceType.D6]}")
        Log.d("AchievementManager", "updateStatsAfterRoll: old sessionRolls=${stats.sessionRolls}, new sessionRolls=${newStats.sessionRolls}")
        Log.d("AchievementManager", "updateStatsAfterRoll: increment calculation: ${stats.sessionRolls} + 1 = ${stats.sessionRolls + 1}")
        Log.d("AchievementManager", "updateStatsAfterRoll: old totalSessionRolls=${stats.totalSessionRolls}, new totalSessionRolls=${newStats.totalSessionRolls}")
        Log.d("AchievementManager", "updateStatsAfterRoll: totalSessionRolls increment calculation: ${stats.totalSessionRolls} + 1 = ${stats.totalSessionRolls + 1}")
        Log.d("AchievementManager", "updateStatsAfterRoll: First roll check - totalRolls=${stats.totalRolls}, sessionRolls=${stats.sessionRolls}, totalSessionRolls=${stats.totalSessionRolls}")
        
        return newStats
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
        if ((if (stats.sessionRolls < 0) 0 else stats.sessionRolls) >= 50) {
            unlockAchievement("session_champion")
        }
        
        // Marathon Roller - Check if rolling continuously for 1 minute
        val sessionDuration = currentTime - stats.sessionStartTime
        Log.d("AchievementManager", "Marathon Roller check: sessionDuration=${sessionDuration}ms (${sessionDuration/1000}s), threshold=60000ms (60s)")
        if (sessionDuration >= 60000) { // 1 minute = 60000 milliseconds
            Log.d("AchievementManager", "Marathon Roller achievement unlocked! Session duration: ${sessionDuration/1000} seconds")
            unlockAchievement("marathon_roller")
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
                    diceType == DiceType.D20 && roll == 20 -> unlockAchievement("natural_20")
                    diceType == DiceType.D20 && roll == 1 -> unlockAchievement("critical_fail")
                    diceType == DiceType.D100 && roll == 100 -> unlockAchievement("perfect_100")
                }
            }
        }
        
        // Check for 2D6 = 2 (Snake Eyes)
        if (rollResult.diceSelections.any { it.diceType == DiceType.D6 && it.count >= 2 }) {
            val d6Rolls = rollResult.individualRolls.find { rolls ->
                rollResult.diceSelections[rollResult.individualRolls.indexOf(rolls)]?.diceType == DiceType.D6
            }
            val d6Sum = d6Rolls?.sum() ?: 0
            Log.d("AchievementManager", "Snake Eyes check: D6 rolls=$d6Rolls, sum=$d6Sum")
            if (d6Sum == 2) {
                Log.d("AchievementManager", "Snake Eyes achievement unlocked! 2D6 = 2")
                unlockAchievement("snake_eyes")
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
        
        // Check for 2D6 = 7 (Lucky Sevens)
        if (rollResult.diceSelections.any { it.diceType == DiceType.D6 && it.count >= 2 }) {
            val d6Rolls = rollResult.individualRolls.find { rolls ->
                rollResult.diceSelections[rollResult.individualRolls.indexOf(rolls)]?.diceType == DiceType.D6
            }
            val d6Sum = d6Rolls?.sum() ?: 0
            Log.d("AchievementManager", "Lucky Sevens check: D6 rolls=$d6Rolls, sum=$d6Sum")
            if (d6Sum == 7) {
                Log.d("AchievementManager", "Lucky Sevens achievement unlocked! 2D6 = 7")
                unlockAchievement("lucky_sevens")
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
        
        // Unpredictable - Roll maximum range (1 to max) in 3 consecutive rolls
        if (lastRollResults.size >= 3 && lastRollDiceTypes.size >= 3) {
            val last3 = lastRollResults.takeLast(3)
            val last3DiceTypes = lastRollDiceTypes.takeLast(3)
            
            Log.d("AchievementManager", "Checking unpredictable: Rolls=$last3, DiceTypes=$last3DiceTypes")
            
            // Check if all 3 rolls used the same dice type
            if (last3DiceTypes.distinct().size == 1) {
                val diceType = last3DiceTypes.first()
                val maxValue = diceType.sides
                
                // Check if the 3 rolls cover the full range (1 to max)
                val minRoll = last3.minOrNull() ?: 0
                val maxRoll = last3.maxOrNull() ?: 0
                
                Log.d("AchievementManager", "Unpredictable check: Dice=$diceType, Min=$minRoll, Max=$maxRoll, TargetMax=$maxValue")
                
                if (minRoll == 1 && maxRoll == maxValue) {
                    Log.d("AchievementManager", "Unpredictable achievement unlocked! Dice: $diceType, Rolls: $last3, Range: 1 to $maxValue")
                    unlockAchievement("unpredictable")
                }
            } else {
                Log.d("AchievementManager", "Unpredictable failed: Different dice types used")
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
        if (stats.themeUsage.size >= 5) {
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
        val timeString = String.format(java.util.Locale.US, "%02d:%02d", hour, minute)
        if (timeString in listOf("07:11", "11:11", "12:34", "13:37")) {
            unlockAchievement("lucky_hour")
        }
        
        // High Noon
        if (hour == 12 && minute == 0) {
            unlockAchievement("high_noon")
        }
        
        // Weekend Warrior
        if (stats.weekendRolls.size >= 50) {
            unlockAchievement("weekend_warrior")
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
        
        // Check if this is a new theme (for Theme Explorer achievement)
        val isNewTheme = !stats.themeUsage.containsKey(newTheme)
        
        val updatedStats = stats.copy(
            themeChanges = stats.themeChanges + 1
        )
        _achievementStats.value = updatedStats
        
        Log.d("AchievementManager", "Theme changed to $newTheme. Theme changes: ${updatedStats.themeChanges}")
        Log.d("AchievementManager", "Theme Switcher progress: ${updatedStats.themeChanges}/10")
        Log.d("AchievementManager", "Is new theme: $isNewTheme. Current theme usage: ${stats.themeUsage}")
        
        // Check theme-based achievements
        checkThemeBasedAchievements(updatedStats)
        
        // Update achievement progress
        updateAchievementProgressAfterRoll(updatedStats)
        
        // Only save stats if data has been loaded
        if (isDataLoaded) {
            achievementStorage.saveAchievementStats(updatedStats)
            saveAchievementData()
        } else {
            Log.d("AchievementManager", "Skipping stats save in onThemeChanged - data not yet loaded")
        }
    }
    
    suspend fun onHistoryViewed() {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(historyViews = stats.historyViews + 1)
        _achievementStats.value = updatedStats
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Update achievement progress
        updateAchievementProgressAfterRoll(updatedStats)
        
        // Only save stats if data has been loaded
        if (isDataLoaded) {
            achievementStorage.saveAchievementStats(updatedStats)
            saveAchievementData()
        } else {
            Log.d("AchievementManager", "Skipping stats save in onHistoryViewed - data not yet loaded")
        }
    }
    
    suspend fun onFavoriteCreated() {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(totalFavorites = stats.totalFavorites + 1)
        _achievementStats.value = updatedStats
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Update achievement progress
        updateAchievementProgressAfterRoll(updatedStats)
        
        // Only save stats if data has been loaded
        if (isDataLoaded) {
            achievementStorage.saveAchievementStats(updatedStats)
            saveAchievementData()
        } else {
            Log.d("AchievementManager", "Skipping stats save in onFavoriteCreated - data not yet loaded")
        }
    }
    
    suspend fun onFavoritesLoaded(favoritesCount: Int) {
        val stats = _achievementStats.value
        val updatedStats = stats.copy(totalFavorites = favoritesCount)
        _achievementStats.value = updatedStats
        
        // Check favorites/history achievements
        checkFavoritesHistoryAchievements(updatedStats)
        
        // Update achievement progress
        updateAchievementProgressAfterRoll(updatedStats)
        
        // Only save stats if data has been loaded
        if (isDataLoaded) {
            achievementStorage.saveAchievementStats(updatedStats)
            saveAchievementData()
        } else {
            Log.d("AchievementManager", "Skipping stats save in onFavoritesLoaded - data not yet loaded")
        }
    }
    
    suspend fun onThemeLoaded(theme: AppTheme) {
        val stats = _achievementStats.value
        
        Log.d("AchievementManager", "Theme loaded: $theme. Current theme usage: ${stats.themeUsage}")
        
        // Check theme-based achievements
        checkThemeBasedAchievements(stats)
        
        // Only save stats if data has been loaded
        if (isDataLoaded) {
            achievementStorage.saveAchievementStats(stats)
        } else {
            Log.d("AchievementManager", "Skipping stats save in onThemeLoaded - data not yet loaded")
        }
    }
    
    suspend fun saveAchievementData() {
        try {
            val progressList = _achievements.value.map { achievement ->
                AchievementProgress(
                    achievementId = achievement.id,
                    currentProgress = achievement.progress,
                    maxProgress = achievement.maxProgress,
                    isUnlocked = achievement.isUnlocked,
                    unlockedAt = achievement.unlockedAt
                )
            }
            
            achievementStorage.saveAchievementProgress(progressList)
            achievementStorage.saveUnlockedAchievements(_unlockedAchievements.value)
            achievementStorage.saveUserTitles(_userTitles.value)
            
            Log.d("AchievementManager", "Saved achievement data: ${progressList.size} progress entries")
            
            // Debug: Log specific achievement progress being saved
            progressList.filter { it.achievementId == "roll_master_100" || it.achievementId == "d6_specialist" }
                .forEach { progress ->
                    Log.d("AchievementManager", "Saving ${progress.achievementId}: progress=${progress.currentProgress}, unlocked=${progress.isUnlocked}")
                }
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