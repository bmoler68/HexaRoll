package com.brianmoler.hexaroll.data

import com.brianmoler.hexaroll.ui.components.BackgroundFitMode

/**
 * DiceType - Enumeration of available dice types
 * 
 * Each dice type has a specific number of sides and a display name
 * used throughout the application for UI and calculations.
 * 
 * @property sides The number of sides on the dice (e.g., 6 for D6)
 * @property displayName The human-readable name (e.g., "D6")
 */
enum class DiceType(val sides: Int, val displayName: String) {
    D4(4, "D4"),
    D6(6, "D6"),
    D8(8, "D8"),
    D10(10, "D10"),
    D12(12, "D12"),
    D20(20, "D20"),
    D30(30, "D30"),
    D100(100, "D100")
}

/**
 * DiceSelection - Represents a user's selection of dice
 * 
 * Contains information about which type of dice and how many
 * the user has selected for rolling.
 * 
 * @property diceType The type of dice selected
 * @property count The number of dice of this type (default: 0)
 */
data class DiceSelection(
    val diceType: DiceType,
    val count: Int = 0
)

/**
 * RollResult - Represents the result of a dice roll
 * 
 * Contains all information about a completed dice roll including:
 * - Individual roll results for each dice
 * - Total calculated result
 * - Metadata like timestamp and notation
 * 
 * @property id Unique identifier for this roll result
 * @property timestamp When the roll was performed (milliseconds since epoch)
 * @property diceSelections The dice that were rolled
 * @property modifier Any modifier applied to the roll (+/- value)
 * @property individualRolls List of individual roll results for each dice type
 * @property d100Rolls Special tracking for D100 percentile dice rolls
 * @property total The final calculated result including modifier
 * @property notation Human-readable notation (e.g., "2D6+3")
 */
data class RollResult(
    val id: String = java.util.UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val diceSelections: List<DiceSelection>,
    val modifier: Int,
    val individualRolls: List<List<Int>>,
    val d100Rolls: List<D100Roll> = emptyList(), // Track individual D10 rolls for D100
    val total: Int,
    val notation: String
)

/**
 * D100Roll - Special tracking for percentile dice rolls
 * 
 * D100 dice are typically rolled as two D10 dice where one represents
 * tens and the other represents ones. This class tracks both components.
 * 
 * @property tensDie The tens digit (0-9, where 0 represents 10)
 * @property onesDie The ones digit (0-9)
 * @property result The calculated final result (1-100)
 */
data class D100Roll(
    val tensDie: Int, // 0-9
    val onesDie: Int, // 0-9
    val result: Int   // 1-100
)

/**
 * PresetRoll - User-defined preset configuration
 * 
 * Allows users to save and reuse common dice roll configurations
 * for quick access without re-selecting dice each time.
 * 
 * @property id Unique identifier for this preset
 * @property name User-defined name for the preset
 * @property description User-defined description
 * @property diceSelections The dice configuration to save
 * @property modifier Any modifier to apply with this preset
 */
data class PresetRoll(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val diceSelections: List<DiceSelection>,
    val modifier: Int
)

/**
 * AppTheme - Available visual themes for the application
 * 
 * Each theme provides a different visual style and color scheme
 * for the dice rolling interface.
 */
enum class AppTheme {
    CYBERPUNK, FANTASY, SCI_FI, WESTERN, ANCIENT
}

/**
 * DiceCustomization - User customization settings
 * 
 * Contains user preferences for the application appearance
 * and behavior.
 * 
 * @property theme The currently selected visual theme
 * @property backgroundEnabled Whether theme backgrounds are displayed
 * @property backgroundOpacity Opacity level for theme backgrounds (0.0 - 1.0)
 * @property backgroundScaling How background images should be scaled to fit the screen
 */
data class DiceCustomization(
    val theme: AppTheme = AppTheme.CYBERPUNK,
    val backgroundEnabled: Boolean = true,
    val backgroundOpacity: Float = 0.3f,
    val backgroundScaling: BackgroundFitMode = BackgroundFitMode.SMART_SCALE
) 