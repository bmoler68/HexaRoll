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
    theme: AppTheme = AppTheme.CYBERPUNK,
    isSelected: Boolean = false
) {
    // Use specific PNG images for each dice type
    val imageResource = when (diceType) {
        DiceType.D4 -> R.drawable.d4_cyberpunk
        DiceType.D6 -> R.drawable.d6_cyberpunk
        DiceType.D8 -> R.drawable.d8_cyberpunk
        DiceType.D10 -> R.drawable.d10_cyberpunk
        DiceType.D12 -> R.drawable.d12_cyberpunk
        DiceType.D20 -> R.drawable.d20_cyberpunk
        DiceType.D30 -> R.drawable.d30_cyberpunk
        DiceType.D100 -> R.drawable.d100_cyberpunk
    }
    
    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "${diceType.displayName} dice",
        modifier = modifier.size(80.dp)
    )
} 