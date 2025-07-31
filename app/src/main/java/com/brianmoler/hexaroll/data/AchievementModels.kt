package com.brianmoler.hexaroll.data

import androidx.compose.ui.graphics.Color

enum class AchievementTier(val displayName: String, val color: Color) {
    BRONZE("Bronze", Color(0xFFCD7F32)),
    SILVER("Silver", Color(0xFFC0C0C0)),
    GOLD("Gold", Color(0xFFFFD700)),
    PLATINUM("Platinum", Color(0xFFE5E4E2)),
    DIAMOND("Diamond", Color(0xFFB9F2FF))
}

enum class AchievementCategory(val displayName: String, val description: String) {
    ROLLING_MILESTONES("Rolling Milestones", "Basic rolling achievements"),
    DICE_SPECIALISTS("Dice Specialists", "Achievements for specific dice types"),
    RESULT_BASED("Result-Based", "Achievements for specific roll results"),
    STREAK_PATTERNS("Streak & Patterns", "Achievements for consecutive rolls"),
    COMBINATION_MODIFIERS("Combination & Modifiers", "Achievements for complex rolls"),
    THEME_BASED("Theme-Based", "Achievements for theme usage"),
    FAVORITES_HISTORY("Favorites & History", "Achievements for app features"),
    SPECIAL_EVENTS("Special Events", "Time-based and rare achievements")
}

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val category: AchievementCategory,
    val tier: AchievementTier,
    val icon: String, // Emoji or icon identifier
    val isUnlocked: Boolean = false,
    val progress: Int = 0,
    val maxProgress: Int = 1,
    val unlockedAt: Long? = null,
    val rarity: Double = 0.0 // Percentage of users who have this achievement
)

data class AchievementProgress(
    val achievementId: String,
    val currentProgress: Int,
    val maxProgress: Int,
    val isUnlocked: Boolean,
    val unlockedAt: Long? = null
)

data class AchievementStats(
    val totalRolls: Int = 0,
    val rollsByDiceType: Map<DiceType, Int> = emptyMap(),
    val totalModifier: Int = 0,
    val positiveModifier: Int = 0,
    val negativeModifier: Int = 0,
    val modifierUsageCount: Int = 0, // Count of rolls that used modifiers
    val themeUsage: Map<AppTheme, Int> = emptyMap(),
    val sessionRolls: Int = 0, // Single session tracking (resets on app restart)
    val totalSessionRolls: Int = 0, // Cross-session tracking (persists across app restarts) // Will be renamed to sessionRollsCount
    val sessionStartTime: Long = System.currentTimeMillis(),
    val lastRollTime: Long = 0,
    val consecutiveSameNumber: Int = 0,
    val consecutiveOnes: Int = 0,
    val consecutiveMaxValues: Int = 0,
    val consecutiveMinValues: Int = 0,
    val consecutiveAverageValues: Int = 0,
    val maxRollsInSession: Int = 0,
    val totalFavorites: Int = 0,
    val historyViews: Int = 0,
    val themeChanges: Int = 0,
    val polyhedralDiceUsed: MutableSet<DiceType> = mutableSetOf(),
    val perfectRolls: Int = 0, // Rolling max value
    val minimumRolls: Int = 0, // Rolling min value (1)
    val averageRolls: Int = 0, // Rolling average value
    val dailyRolls: MutableSet<String> = mutableSetOf(), // Date strings
    val weeklyRolls: MutableSet<String> = mutableSetOf(), // Week strings
    val monthlyRolls: MutableSet<String> = mutableSetOf(), // Month strings
    val weekendRolls: MutableSet<String> = mutableSetOf() // Weekend date strings
)

data class UserTitle(
    val id: String,
    val name: String,
    val description: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
)

// Achievement definitions
object AchievementDefinitions {
    
    // Rolling Milestones
    val FIRST_ROLL = Achievement(
        id = "first_roll",
        name = "First Roll",
        description = "Complete your first dice roll",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.BRONZE,
        icon = "🎲"
    )
    
    val ROLL_MASTER_100 = Achievement(
        id = "roll_master_100",
        name = "Roll Master (100)",
        description = "Roll 100 times",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.BRONZE,
        icon = "🎯",
        maxProgress = 100
    )
    
    val ROLL_MASTER_500 = Achievement(
        id = "roll_master_500",
        name = "Roll Master (500)",
        description = "Roll 500 times",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.SILVER,
        icon = "🎯",
        maxProgress = 500
    )
    
    val ROLL_MASTER_1000 = Achievement(
        id = "roll_master_1000",
        name = "Roll Master (1000)",
        description = "Roll 1000 times",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.GOLD,
        icon = "🎯",
        maxProgress = 1000
    )
    
    val ROLL_MASTER_5000 = Achievement(
        id = "roll_master_5000",
        name = "Roll Master (5000)",
        description = "Roll 5000 times",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.PLATINUM,
        icon = "🎯",
        maxProgress = 5000
    )
    
    val ROLL_MASTER_10000 = Achievement(
        id = "roll_master_10000",
        name = "Roll Master (10000)",
        description = "Roll 10000 times",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.DIAMOND,
        icon = "🎯",
        maxProgress = 10000
    )
    
    val SPEED_DEMON = Achievement(
        id = "speed_demon",
        name = "Speed Demon",
        description = "Roll 10 times in under 30 seconds",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.SILVER,
        icon = "⚡"
    )
    
    val MARATHON_ROLLER = Achievement(
        id = "marathon_roller",
        name = "Marathon Roller",
        description = "Roll continuously for 1 minute",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.GOLD,
        icon = "🏃"
    )
    
    val SESSION_CHAMPION = Achievement(
        id = "session_champion",
        name = "Session Champion",
        description = "Roll 50+ times in a single app session",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.SILVER,
        icon = "👑",
        maxProgress = 50
    )
    
    val PERSISTENT_ROLLER = Achievement(
        id = "persistent_roller",
        name = "Persistent Roller",
        description = "Roll 100+ times across all sessions",
        category = AchievementCategory.ROLLING_MILESTONES,
        tier = AchievementTier.GOLD,
        icon = "🔄",
        maxProgress = 100
    )
    
    // Dice Type Specialists
    val D4_DEVOTEE = Achievement(
        id = "d4_devotee",
        name = "D4 Devotee",
        description = "Roll D4 50 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.BRONZE,
        icon = "🔺",
        maxProgress = 50
    )
    
    val D6_SPECIALIST = Achievement(
        id = "d6_specialist",
        name = "D6 Specialist",
        description = "Roll D6 100 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.SILVER,
        icon = "⬜",
        maxProgress = 100
    )
    
    val D8_ENTHUSIAST = Achievement(
        id = "d8_enthusiast",
        name = "D8 Enthusiast",
        description = "Roll D8 75 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.BRONZE,
        icon = "🔷",
        maxProgress = 75
    )
    
    val D10_MASTER = Achievement(
        id = "d10_master",
        name = "D10 Master",
        description = "Roll D10 100 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.SILVER,
        icon = "🔟",
        maxProgress = 100
    )
    
    val D12_EXPERT = Achievement(
        id = "d12_expert",
        name = "D12 Expert",
        description = "Roll D12 50 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.BRONZE,
        icon = "🔶",
        maxProgress = 50
    )
    
    val D20_LEGEND = Achievement(
        id = "d20_legend",
        name = "D20 Legend",
        description = "Roll D20 200 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.GOLD,
        icon = "🎲",
        maxProgress = 200
    )
    
    val D30_PIONEER = Achievement(
        id = "d30_pioneer",
        name = "D30 Pioneer",
        description = "Roll D30 25 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.SILVER,
        icon = "🔸",
        maxProgress = 25
    )
    
    val D100_CENTURION = Achievement(
        id = "d100_centurion",
        name = "D100 Centurion",
        description = "Roll D100 50 times",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.SILVER,
        icon = "💯",
        maxProgress = 50
    )
    
    val POLYHEDRAL_MASTER = Achievement(
        id = "polyhedral_master",
        name = "Polyhedral Master",
        description = "Roll all 8 dice types at least once",
        category = AchievementCategory.DICE_SPECIALISTS,
        tier = AchievementTier.GOLD,
        icon = "🎲",
        maxProgress = 8
    )
    
    // Result-Based Achievements
    val SNAKE_EYES = Achievement(
        id = "snake_eyes",
        name = "Snake Eyes",
        description = "Roll 2 on 2D6",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.BRONZE,
        icon = "🐍"
    )
    
    val BOXCARS = Achievement(
        id = "boxcars",
        name = "Boxcars",
        description = "Roll 12 on 2D6",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.SILVER,
        icon = "📦"
    )
    
    val NATURAL_20 = Achievement(
        id = "natural_20",
        name = "Natural 20",
        description = "Roll 20 on D20",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.SILVER,
        icon = "⭐"
    )
    
    val CRITICAL_FAIL = Achievement(
        id = "critical_fail",
        name = "Critical Fail",
        description = "Roll 1 on D20",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.BRONZE,
        icon = "💥"
    )
    
    val PERFECT_100 = Achievement(
        id = "perfect_100",
        name = "Perfect 100",
        description = "Roll 100 on D100",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.GOLD,
        icon = "💯"
    )
    
    val HIGH_ROLLER = Achievement(
        id = "high_roller",
        name = "High Roller",
        description = "Roll maximum value on any dice 10 times",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.SILVER,
        icon = "🎰",
        maxProgress = 10
    )
    
    val LOW_BALLER = Achievement(
        id = "low_baller",
        name = "Low Baller",
        description = "Roll minimum value (1) on any dice 10 times",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.SILVER,
        icon = "📉",
        maxProgress = 10
    )
    
    val AVERAGE_JOE = Achievement(
        id = "average_joe",
        name = "Average Joe",
        description = "Roll exactly the average value for a dice type 10 times",
        category = AchievementCategory.RESULT_BASED,
        tier = AchievementTier.SILVER,
        icon = "📊",
        maxProgress = 10
    )
    
    // Streak & Pattern Achievements
    val HOT_STREAK = Achievement(
        id = "hot_streak",
        name = "Hot Streak",
        description = "Roll the same number 3 times in a row",
        category = AchievementCategory.STREAK_PATTERNS,
        tier = AchievementTier.SILVER,
        icon = "🔥"
    )
    
    val COLD_STREAK = Achievement(
        id = "cold_streak",
        name = "Cold Streak",
        description = "Roll 1 three times in a row",
        category = AchievementCategory.STREAK_PATTERNS,
        tier = AchievementTier.BRONZE,
        icon = "❄️"
    )
    
    val ALTERNATING = Achievement(
        id = "alternating",
        name = "Alternating",
        description = "Roll high/low/high/low pattern",
        category = AchievementCategory.STREAK_PATTERNS,
        tier = AchievementTier.GOLD,
        icon = "🔄"
    )
    
    val CONSISTENT = Achievement(
        id = "consistent",
        name = "Consistent",
        description = "Roll within 2 points of each other for 5 rolls",
        category = AchievementCategory.STREAK_PATTERNS,
        tier = AchievementTier.SILVER,
        icon = "📏"
    )
    
    val UNPREDICTABLE = Achievement(
        id = "unpredictable",
        name = "Unpredictable",
        description = "Roll maximum range (1 to max) in 3 consecutive rolls",
        category = AchievementCategory.STREAK_PATTERNS,
        tier = AchievementTier.GOLD,
        icon = "🎲"
    )
    
    // Combination & Modifier Achievements
    val MODIFIER_MASTER = Achievement(
        id = "modifier_master",
        name = "Modifier Master",
        description = "Use modifiers totaling +10 or more",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.SILVER,
        icon = "➕"
    )
    
    val NEGATIVE_NANCY = Achievement(
        id = "negative_nancy",
        name = "Negative Nancy",
        description = "Use negative modifiers totaling -10 or more",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.SILVER,
        icon = "➖"
    )
    
    val BALANCED = Achievement(
        id = "balanced",
        name = "Balanced",
        description = "Use modifiers in 10 different rolls",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.BRONZE,
        icon = "⚖️",
        maxProgress = 10
    )
    
    val EXTREME = Achievement(
        id = "extreme",
        name = "Extreme",
        description = "Roll with +20 or -20 modifier",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.PLATINUM,
        icon = "💪"
    )
    
    val MIXED_BAG = Achievement(
        id = "mixed_bag",
        name = "Mixed Bag",
        description = "Roll 3+ different dice types in one roll",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.SILVER,
        icon = "🎒"
    )
    
    val DICE_HOARDER = Achievement(
        id = "dice_hoarder",
        name = "Dice Hoarder",
        description = "Roll 10+ dice in a single roll",
        category = AchievementCategory.COMBINATION_MODIFIERS,
        tier = AchievementTier.GOLD,
        icon = "🐉"
    )
    
    // Theme-Based Achievements
    val THEME_EXPLORER = Achievement(
        id = "theme_explorer",
        name = "Theme Explorer",
        description = "Use all five themes (Cyberpunk, Fantasy, Sci-Fi, Western, Ancient)",
        category = AchievementCategory.THEME_BASED,
        tier = AchievementTier.SILVER,
        icon = "🎨",
        maxProgress = 5
    )
    
    val THEME_LOYALIST = Achievement(
        id = "theme_loyalist",
        name = "Theme Loyalist",
        description = "Roll 100 times with the same theme",
        category = AchievementCategory.THEME_BASED,
        tier = AchievementTier.GOLD,
        icon = "💎",
        maxProgress = 100
    )
    
    val THEME_SWITCHER = Achievement(
        id = "theme_switcher",
        name = "Theme Switcher",
        description = "Change themes 10 times in one session",
        category = AchievementCategory.THEME_BASED,
        tier = AchievementTier.SILVER,
        icon = "🔄",
        maxProgress = 10
    )
    
    // Favorites & History Achievements
    val PRESET_PIONEER = Achievement(
        id = "preset_pioneer",
        name = "Preset Pioneer",
        description = "Create your first favorite",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.BRONZE,
        icon = "⭐"
    )
    
    val PRESET_COLLECTOR_5 = Achievement(
        id = "preset_collector_5",
        name = "Preset Collector (5)",
        description = "Create 5 favorites",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.SILVER,
        icon = "📚",
        maxProgress = 5
    )
    
    val PRESET_COLLECTOR_10 = Achievement(
        id = "preset_collector_10",
        name = "Preset Collector (10)",
        description = "Create 10 favorites",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.GOLD,
        icon = "📚",
        maxProgress = 10
    )
    
    val PRESET_COLLECTOR_25 = Achievement(
        id = "preset_collector_25",
        name = "Preset Collector (25)",
        description = "Create 25 favorites",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.PLATINUM,
        icon = "📚",
        maxProgress = 25
    )
    
    val HISTORY_BUFF = Achievement(
        id = "history_buff",
        name = "History Buff",
        description = "View roll history 50 times",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.SILVER,
        icon = "📖",
        maxProgress = 50
    )
    
    val MEMORY_MASTER = Achievement(
        id = "memory_master",
        name = "Memory Master",
        description = "Have 50+ rolls in history",
        category = AchievementCategory.FAVORITES_HISTORY,
        tier = AchievementTier.SILVER,
        icon = "🧠",
        maxProgress = 50
    )
    
    // Special Event Achievements
    val MIDNIGHT_ROLLER = Achievement(
        id = "midnight_roller",
        name = "Midnight Roller",
        description = "Roll at midnight",
        category = AchievementCategory.SPECIAL_EVENTS,
        tier = AchievementTier.GOLD,
        icon = "🌙"
    )
    
    val LUCKY_HOUR = Achievement(
        id = "lucky_hour",
        name = "Lucky Hour",
        description = "Roll at 7:11, 11:11, etc.",
        category = AchievementCategory.SPECIAL_EVENTS,
        tier = AchievementTier.SILVER,
        icon = "🍀"
    )
    
    val WEEKEND_WARRIOR = Achievement(
        id = "weekend_warrior",
        name = "Weekend Warrior",
        description = "Roll 50+ times on weekends",
        category = AchievementCategory.SPECIAL_EVENTS,
        tier = AchievementTier.SILVER,
        icon = "🏆",
        maxProgress = 50
    )
    
    val DAILY_GRINDER = Achievement(
        id = "daily_grinder",
        name = "Daily Grinder",
        description = "Roll every day for a week",
        category = AchievementCategory.SPECIAL_EVENTS,
        tier = AchievementTier.GOLD,
        icon = "📅",
        maxProgress = 7
    )
    
    val MONTHLY_MASTER = Achievement(
        id = "monthly_master",
        name = "Monthly Master",
        description = "Roll every day for a month",
        category = AchievementCategory.SPECIAL_EVENTS,
        tier = AchievementTier.DIAMOND,
        icon = "📆",
        maxProgress = 30
    )
    
    // All achievements list
    val ALL_ACHIEVEMENTS = listOf(
        FIRST_ROLL,
        ROLL_MASTER_100, ROLL_MASTER_500, ROLL_MASTER_1000, ROLL_MASTER_5000, ROLL_MASTER_10000,
        SPEED_DEMON, MARATHON_ROLLER, SESSION_CHAMPION, PERSISTENT_ROLLER,
        D4_DEVOTEE, D6_SPECIALIST, D8_ENTHUSIAST, D10_MASTER, D12_EXPERT, D20_LEGEND, D30_PIONEER, D100_CENTURION, POLYHEDRAL_MASTER,
        SNAKE_EYES, BOXCARS, NATURAL_20, CRITICAL_FAIL, PERFECT_100, HIGH_ROLLER, LOW_BALLER, AVERAGE_JOE,
        HOT_STREAK, COLD_STREAK, ALTERNATING, CONSISTENT, UNPREDICTABLE,
        MODIFIER_MASTER, NEGATIVE_NANCY, BALANCED, EXTREME, MIXED_BAG, DICE_HOARDER,
        THEME_EXPLORER, THEME_LOYALIST, THEME_SWITCHER,
        PRESET_PIONEER, PRESET_COLLECTOR_5, PRESET_COLLECTOR_10, PRESET_COLLECTOR_25, HISTORY_BUFF, MEMORY_MASTER,
        MIDNIGHT_ROLLER, LUCKY_HOUR, WEEKEND_WARRIOR, DAILY_GRINDER, MONTHLY_MASTER
    )
} 