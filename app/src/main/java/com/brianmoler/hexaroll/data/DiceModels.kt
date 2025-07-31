package com.brianmoler.hexaroll.data

import androidx.compose.ui.graphics.Color

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

data class DiceSelection(
    val diceType: DiceType,
    val count: Int = 0
)

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

data class D100Roll(
    val tensDie: Int, // 0-9
    val onesDie: Int, // 0-9
    val result: Int   // 1-100
)

data class PresetRoll(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val diceSelections: List<DiceSelection>,
    val modifier: Int
)

enum class AppTheme {
    CYBERPUNK, FANTASY, SCI_FI, WESTERN, ANCIENT
}

data class DiceCustomization(
    val theme: AppTheme = AppTheme.CYBERPUNK
) 