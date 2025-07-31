package com.brianmoler.hexaroll.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.res.stringResource
import com.brianmoler.hexaroll.data.DiceType

/**
 * Accessibility helper for providing content descriptions
 */
object AccessibilityHelper {
    
    /**
     * Get content description for dice button
     */
    fun getDiceButtonDescription(diceType: DiceType, count: Int, addDiceRes: String, diceSelectedRes: String, diceSelectedSingleRes: String): String {
        return when {
            count == 0 -> String.format(addDiceRes, diceType.displayName)
            count == 1 -> String.format(diceSelectedSingleRes, diceType.displayName)
            else -> String.format(diceSelectedRes, count, diceType.displayName)
        }
    }
    
    /**
     * Get content description for modifier button
     */
    fun getModifierButtonDescription(modifier: Int, noModifierRes: String, modifierPositiveRes: String, modifierNegativeRes: String): String {
        return when {
            modifier == 0 -> noModifierRes
            modifier > 0 -> String.format(modifierPositiveRes, modifier)
            else -> String.format(modifierNegativeRes, modifier)
        }
    }
    
    /**
     * Get content description for roll button
     */
    fun getRollButtonDescription(diceCount: Int, modifier: Int, noDiceSelectedRes: String, rollSingleDieRes: String, rollMultipleDiceRes: String): String {
        val modifierText = when {
            modifier == 0 -> ""
            modifier > 0 -> " with +$modifier modifier"
            else -> " with $modifier modifier"
        }
        
        return when {
            diceCount == 0 -> noDiceSelectedRes
            diceCount == 1 -> String.format(rollSingleDieRes, modifierText)
            else -> String.format(rollMultipleDiceRes, diceCount, modifierText)
        }
    }
    
    /**
     * Get content description for theme button
     */
    fun getThemeButtonDescription(themeName: String, switchThemeRes: String): String {
        return String.format(switchThemeRes, themeName)
    }
    
    /**
     * Get content description for preset button
     */
    fun getPresetButtonDescription(presetName: String, loadPresetRes: String): String {
        return String.format(loadPresetRes, presetName)
    }
    
    /**
     * Get content description for achievement button
     */
    fun getAchievementButtonDescription(achievementName: String, progress: Int, maxProgress: Int, achievementProgressRes: String): String {
        return String.format(achievementProgressRes, achievementName, progress, maxProgress)
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