package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brianmoler.hexaroll.R
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.data.DiceType

@Composable
fun DiceShape(
    diceType: DiceType,
    modifier: Modifier = Modifier,
    theme: AppTheme = AppTheme.CYBERPUNK
) {
    // Use theme-specific dice images based on selected theme
    val imageResource = when (theme) {
        AppTheme.FANTASY -> when (diceType) {
            DiceType.D4 -> R.drawable.d4_fantasy
            DiceType.D6 -> R.drawable.d6_fantasy
            DiceType.D8 -> R.drawable.d8_fantasy
            DiceType.D10 -> R.drawable.d10_fantasy
            DiceType.D12 -> R.drawable.d12_fantasy
            DiceType.D20 -> R.drawable.d20_fantasy
            DiceType.D30 -> R.drawable.d30_fantasy
            DiceType.D100 -> R.drawable.d100_fantasy
        }
        AppTheme.SCI_FI -> when (diceType) {
            DiceType.D4 -> R.drawable.d4_scifi
            DiceType.D6 -> R.drawable.d6_scifi
            DiceType.D8 -> R.drawable.d8_scifi
            DiceType.D10 -> R.drawable.d10_scifi
            DiceType.D12 -> R.drawable.d12_scifi
            DiceType.D20 -> R.drawable.d20_scifi
            DiceType.D30 -> R.drawable.d30_scifi
            DiceType.D100 -> R.drawable.d100_scifi
        }
        AppTheme.WESTERN -> when (diceType) {
            DiceType.D4 -> R.drawable.d4_western
            DiceType.D6 -> R.drawable.d6_western
            DiceType.D8 -> R.drawable.d8_western
            DiceType.D10 -> R.drawable.d10_western
            DiceType.D12 -> R.drawable.d12_western
            DiceType.D20 -> R.drawable.d20_western
            DiceType.D30 -> R.drawable.d30_western
            DiceType.D100 -> R.drawable.d100_western
        }
        AppTheme.ANCIENT -> when (diceType) {
            DiceType.D4 -> R.drawable.d4_roman
            DiceType.D6 -> R.drawable.d6_roman
            DiceType.D8 -> R.drawable.d8_roman
            DiceType.D10 -> R.drawable.d10_roman
            DiceType.D12 -> R.drawable.d12_roman
            DiceType.D20 -> R.drawable.d20_roman
            DiceType.D30 -> R.drawable.d30_roman
            DiceType.D100 -> R.drawable.d100_roman
        }
        else -> when (diceType) {  // CYBERPUNK theme
            DiceType.D4 -> R.drawable.d4_cyberpunk
            DiceType.D6 -> R.drawable.d6_cyberpunk
            DiceType.D8 -> R.drawable.d8_cyberpunk
            DiceType.D10 -> R.drawable.d10_cyberpunk
            DiceType.D12 -> R.drawable.d12_cyberpunk
            DiceType.D20 -> R.drawable.d20_cyberpunk
            DiceType.D30 -> R.drawable.d30_cyberpunk
            DiceType.D100 -> R.drawable.d100_cyberpunk
        }
    }
    
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "${diceType.displayName} dice",
        modifier = modifier.size(80.dp)
    )
} 