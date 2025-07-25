package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.ui.theme.CyberpunkColors
import android.graphics.Paint
import android.graphics.BlurMaskFilter
import kotlin.math.*

@Composable
fun DiceShape(
    diceType: DiceType,
    modifier: Modifier = Modifier,
    color: Color = CyberpunkColors.DiceDefault,
    isSelected: Boolean = false
) {
    val strokeColor = if (isSelected) CyberpunkColors.GlowYellow else CyberpunkColors.NeonCyan
    val strokeWidth = if (isSelected) 4f else 2f

    Canvas(
        modifier = modifier.size(80.dp)
    ) {
        when (diceType) {
            DiceType.D4 -> drawCyberpunkD4(strokeColor, strokeWidth, isSelected)
            DiceType.D6 -> drawCyberpunkD6(strokeColor, strokeWidth, isSelected)
            DiceType.D8 -> drawCyberpunkD8(strokeColor, strokeWidth, isSelected)
            DiceType.D10 -> drawCyberpunkD10(strokeColor, strokeWidth, isSelected)
            DiceType.D12 -> drawCyberpunkD12(strokeColor, strokeWidth, isSelected)
            DiceType.D20 -> drawCyberpunkD20(strokeColor, strokeWidth, isSelected)
            DiceType.D30 -> drawCyberpunkD30(strokeColor, strokeWidth, isSelected)
            DiceType.D100 -> drawCyberpunkD100(strokeColor, strokeWidth, isSelected)
        }
    }
}

private fun DrawScope.drawCyberpunkD4(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create dynamic triangle with cyberpunk styling
    val path = Path().apply {
        moveTo(center.x, center.y - radius * 0.9f)
        lineTo(center.x - radius * 0.8f, center.y + radius * 0.5f)
        lineTo(center.x + radius * 0.8f, center.y + radius * 0.5f)
        close()
    }
    
    // Multi-layer cyberpunk effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonBlue.copy(alpha = 1.0f),
            CyberpunkColors.NeonBlue.copy(alpha = 0.7f),
            CyberpunkColors.NeonBlue.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val highlightGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonCyan.copy(alpha = 0.8f),
            Color.Transparent
        ),
        center = Offset(center.x - radius * 0.3f, center.y - radius * 0.3f),
        radius = radius * 0.8f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = highlightGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw inner glow lines
    val innerPath = Path().apply {
        moveTo(center.x, center.y - radius * 0.6f)
        lineTo(center.x - radius * 0.5f, center.y + radius * 0.3f)
        lineTo(center.x + radius * 0.5f, center.y + radius * 0.3f)
        close()
    }
    drawPath(innerPath, CyberpunkColors.NeonCyan.copy(alpha = 0.3f), style = Stroke(width = 1f))
    
    // Draw D4 number with cyberpunk styling
    drawCyberpunkText("4", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD6(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val cubeSize = size.minDimension / 3f
    
    // Create cyberpunk cube with rounded corners effect
    val path = Path().apply {
        moveTo(center.x - cubeSize, center.y - cubeSize)
        lineTo(center.x + cubeSize, center.y - cubeSize)
        lineTo(center.x + cubeSize, center.y + cubeSize)
        lineTo(center.x - cubeSize, center.y + cubeSize)
        close()
    }
    
    // Cyberpunk gradient with metallic effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonGreen.copy(alpha = 1.0f),
            CyberpunkColors.NeonGreen.copy(alpha = 0.6f),
            CyberpunkColors.NeonGreen.copy(alpha = 0.2f),
            Color.Transparent
        ),
        center = center,
        radius = cubeSize * 2f
    )
    
    val highlightGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonCyan.copy(alpha = 0.6f),
            Color.Transparent
        ),
        center = Offset(center.x - cubeSize * 0.4f, center.y - cubeSize * 0.4f),
        radius = cubeSize * 1.2f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = highlightGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw grid pattern for cyberpunk effect
    drawPath(Path().apply {
        moveTo(center.x - cubeSize, center.y)
        lineTo(center.x + cubeSize, center.y)
    }, CyberpunkColors.NeonCyan.copy(alpha = 0.4f), style = Stroke(width = 1f))
    
    drawPath(Path().apply {
        moveTo(center.x, center.y - cubeSize)
        lineTo(center.x, center.y + cubeSize)
    }, CyberpunkColors.NeonCyan.copy(alpha = 0.4f), style = Stroke(width = 1f))
    
    // Draw D6 number
    drawCyberpunkText("6", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD8(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk diamond with dynamic angles
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.8f, center.y)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.8f, center.y)
        close()
    }
    
    // Cyberpunk gradient with energy effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonPurple.copy(alpha = 1.0f),
            CyberpunkColors.NeonPurple.copy(alpha = 0.7f),
            CyberpunkColors.NeonPurple.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val energyGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonMagenta.copy(alpha = 0.8f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.5f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = energyGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw energy lines
    drawPath(Path().apply {
        moveTo(center.x, center.y - radius * 0.7f)
        lineTo(center.x, center.y + radius * 0.7f)
    }, CyberpunkColors.NeonMagenta.copy(alpha = 0.5f), style = Stroke(width = 2f))
    
    drawPath(Path().apply {
        moveTo(center.x - radius * 0.6f, center.y)
        lineTo(center.x + radius * 0.6f, center.y)
    }, CyberpunkColors.NeonMagenta.copy(alpha = 0.5f), style = Stroke(width = 2f))
    
    // Draw D8 number
    drawCyberpunkText("8", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD10(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk decagon with tech pattern
    val path = Path().apply {
        val sides = 10
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius
            val y = center.y + sin(angle) * radius
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    // Cyberpunk gradient with tech effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonRed.copy(alpha = 1.0f),
            CyberpunkColors.NeonRed.copy(alpha = 0.6f),
            CyberpunkColors.NeonRed.copy(alpha = 0.2f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val techGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonOrange.copy(alpha = 0.7f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.3f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = techGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw tech circuit pattern
    for (i in 0 until 5) {
        val angle = i * PI / 2.5
        val startX = center.x + cos(angle) * radius * 0.3f
        val startY = center.y + sin(angle) * radius * 0.3f
        val endX = center.x + cos(angle) * radius * 0.8f
        val endY = center.y + sin(angle) * radius * 0.8f
        
        drawPath(Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }, CyberpunkColors.NeonOrange.copy(alpha = 0.4f), style = Stroke(width = 1f))
    }
    
    // Draw D10 number
    drawCyberpunkText("0", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD12(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk dodecagon with advanced pattern
    val path = Path().apply {
        val sides = 12
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius
            val y = center.y + sin(angle) * radius
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    // Cyberpunk gradient with holographic effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonCyan.copy(alpha = 1.0f),
            CyberpunkColors.NeonCyan.copy(alpha = 0.7f),
            CyberpunkColors.NeonCyan.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val holoGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonBlue.copy(alpha = 0.6f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.4f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = holoGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw holographic pattern
    for (i in 0 until 6) {
        val angle = i * PI / 3
        val startX = center.x + cos(angle) * radius * 0.2f
        val startY = center.y + sin(angle) * radius * 0.2f
        val endX = center.x + cos(angle) * radius * 0.9f
        val endY = center.y + sin(angle) * radius * 0.9f
        
        drawPath(Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }, CyberpunkColors.NeonBlue.copy(alpha = 0.3f), style = Stroke(width = 1f))
    }
    
    // Draw D12 number
    drawCyberpunkText("12", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD20(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk icosagon with complex pattern
    val path = Path().apply {
        val sides = 20
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius
            val y = center.y + sin(angle) * radius
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    // Cyberpunk gradient with quantum effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonMagenta.copy(alpha = 1.0f),
            CyberpunkColors.NeonMagenta.copy(alpha = 0.7f),
            CyberpunkColors.NeonMagenta.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val quantumGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonPurple.copy(alpha = 0.8f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.6f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = quantumGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw quantum pattern
    for (i in 0 until 10) {
        val angle = i * PI / 5
        val startX = center.x + cos(angle) * radius * 0.1f
        val startY = center.y + sin(angle) * radius * 0.1f
        val endX = center.x + cos(angle) * radius * 0.95f
        val endY = center.y + sin(angle) * radius * 0.95f
        
        drawPath(Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }, CyberpunkColors.NeonPurple.copy(alpha = 0.2f), style = Stroke(width = 0.5f))
    }
    
    // Draw D20 number
    drawCyberpunkText("20", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD30(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk 30-sided polygon with advanced pattern
    val path = Path().apply {
        val sides = 30
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius
            val y = center.y + sin(angle) * radius
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    // Cyberpunk gradient with neural network effect
    val baseGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonOrange.copy(alpha = 1.0f),
            CyberpunkColors.NeonOrange.copy(alpha = 0.6f),
            CyberpunkColors.NeonOrange.copy(alpha = 0.2f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val neuralGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonYellow.copy(alpha = 0.7f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.7f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(path, brush = baseGradient)
        drawPath(path, brush = neuralGradient)
    } else {
        drawPath(path, brush = baseGradient)
    }
    
    // Draw cyberpunk border
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw neural network pattern
    for (i in 0 until 15) {
        val angle = i * PI / 7.5
        val startX = center.x + cos(angle) * radius * 0.05f
        val startY = center.y + sin(angle) * radius * 0.05f
        val endX = center.x + cos(angle) * radius * 0.98f
        val endY = center.y + sin(angle) * radius * 0.98f
        
        drawPath(Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }, CyberpunkColors.NeonYellow.copy(alpha = 0.15f), style = Stroke(width = 0.3f))
    }
    
    // Draw D30 number
    drawCyberpunkText("30", center, CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkD100(strokeColor: Color, strokeWidth: Float, isSelected: Boolean) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3f
    
    // Create cyberpunk D100 with dual-ring design
    val outerPath = Path().apply {
        val sides = 10
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius
            val y = center.y + sin(angle) * radius
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    val innerPath = Path().apply {
        val sides = 10
        val angleStep = 2 * PI / sides
        val startAngle = -PI / 2
        
        for (i in 0 until sides) {
            val angle = startAngle + i * angleStep
            val x = center.x + cos(angle) * radius * 0.7f
            val y = center.y + sin(angle) * radius * 0.7f
            
            if (i == 0) {
                moveTo(x.toFloat(), y.toFloat())
            } else {
                lineTo(x.toFloat(), y.toFloat())
            }
        }
        close()
    }
    
    // Cyberpunk gradients with dual energy effect
    val outerGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonBlue.copy(alpha = 1.0f),
            CyberpunkColors.NeonBlue.copy(alpha = 0.7f),
            CyberpunkColors.NeonBlue.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 2f
    )
    
    val innerGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonGreen.copy(alpha = 1.0f),
            CyberpunkColors.NeonGreen.copy(alpha = 0.7f),
            CyberpunkColors.NeonGreen.copy(alpha = 0.3f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.4f
    )
    
    val energyGradient = androidx.compose.ui.graphics.Brush.radialGradient(
        colors = listOf(
            CyberpunkColors.NeonCyan.copy(alpha = 0.8f),
            Color.Transparent
        ),
        center = center,
        radius = radius * 1.8f
    )
    
    // Draw base with glow
    if (isSelected) {
        drawPath(outerPath, brush = outerGradient)
        drawPath(innerPath, brush = innerGradient)
        drawPath(outerPath, brush = energyGradient)
    } else {
        drawPath(outerPath, brush = outerGradient)
        drawPath(innerPath, brush = innerGradient)
    }
    
    // Draw cyberpunk borders
    drawPath(outerPath, strokeColor, style = Stroke(width = strokeWidth))
    drawPath(innerPath, strokeColor, style = Stroke(width = strokeWidth))
    
    // Draw energy connection lines
    for (i in 0 until 5) {
        val angle = i * PI / 2.5
        val startX = center.x + cos(angle) * radius * 0.7f
        val startY = center.y + sin(angle) * radius * 0.7f
        val endX = center.x + cos(angle) * radius
        val endY = center.y + sin(angle) * radius
        
        drawPath(Path().apply {
            moveTo(startX.toFloat(), startY.toFloat())
            lineTo(endX.toFloat(), endY.toFloat())
        }, CyberpunkColors.NeonCyan.copy(alpha = 0.6f), style = Stroke(width = 2f))
    }
    
    // Draw D100 numbers
    drawCyberpunkText("00", Offset(center.x - radius * 0.3f, center.y), CyberpunkColors.NeonYellow, isSelected)
    drawCyberpunkText("90", Offset(center.x + radius * 0.3f, center.y), CyberpunkColors.NeonYellow, isSelected)
}

private fun DrawScope.drawCyberpunkText(text: String, position: Offset, color: Color, isSelected: Boolean) {
    val fontSize = when (text.length) {
        1 -> 16f
        2 -> 12f
        else -> 10f
    }
    
    val paint = Paint().apply {
        this.color = color.toArgb()
        textSize = fontSize
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }
    
    // Draw glow effect if selected
    if (isSelected) {
        val glowPaint = Paint().apply {
            this.color = color.copy(alpha = 0.5f).toArgb()
            maskFilter = BlurMaskFilter(6f, BlurMaskFilter.Blur.NORMAL)
            textSize = fontSize
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        
        drawContext.canvas.nativeCanvas.drawText(
            text,
            position.x,
            position.y + fontSize * 0.3f,
            glowPaint
        )
    }
    
    drawContext.canvas.nativeCanvas.drawText(
        text,
        position.x,
        position.y + fontSize * 0.3f,
        paint
    )
} 