package com.brianmoler.hexaroll.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.brianmoler.hexaroll.data.DiceType

/**
 * Accessibility helper for providing content descriptions
 */
object AccessibilityHelper {
    
    /**
     * Get content description for dice button
     */
    fun getDiceButtonDescription(diceType: DiceType, count: Int): String {
        return when {
            count == 0 -> "Add ${diceType.displayName}"
            count == 1 -> "1 ${diceType.displayName} selected"
            else -> "$count ${diceType.displayName}s selected"
        }
    }
    
    /**
     * Get content description for modifier button
     */
    fun getModifierButtonDescription(modifier: Int): String {
        return when {
            modifier == 0 -> "No modifier"
            modifier > 0 -> "Modifier: +$modifier"
            else -> "Modifier: $modifier"
        }
    }
    
    /**
     * Get content description for roll button
     */
    fun getRollButtonDescription(diceCount: Int, modifier: Int): String {
        val modifierText = when {
            modifier == 0 -> ""
            modifier > 0 -> " with +$modifier modifier"
            else -> " with $modifier modifier"
        }
        
        return when {
            diceCount == 0 -> "No dice selected"
            diceCount == 1 -> "Roll 1 die$modifierText"
            else -> "Roll $diceCount dice$modifierText"
        }
    }
    
    /**
     * Get content description for theme button
     */
    fun getThemeButtonDescription(themeName: String): String {
        return "Switch to $themeName theme"
    }
    
    /**
     * Get content description for preset button
     */
    fun getPresetButtonDescription(presetName: String): String {
        return "Load preset: $presetName"
    }
    
    /**
     * Get content description for achievement button
     */
    fun getAchievementButtonDescription(achievementName: String, progress: Int, maxProgress: Int): String {
        return "$achievementName achievement: $progress out of $maxProgress"
    }
}

/**
 * Composable modifier for adding content description
 */
@Composable
fun Modifier.withContentDescription(description: String): Modifier {
    return this.semantics {
        contentDescription = description
    }
} 