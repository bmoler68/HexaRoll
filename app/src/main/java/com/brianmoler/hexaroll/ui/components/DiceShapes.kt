package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.ui.theme.CyberpunkColors

@Composable
fun DiceShape(
    diceType: DiceType,
    modifier: Modifier = Modifier,
    color: Color = CyberpunkColors.DiceDefault,
    isSelected: Boolean = false
) {
    val strokeColor = if (isSelected) CyberpunkColors.GlowYellow else color
    val strokeWidth = if (isSelected) 3f else 2f
    
    Canvas(
        modifier = modifier.size(60.dp)
    ) {
        when (diceType) {
            DiceType.D4 -> drawTetrahedron(color, strokeColor, strokeWidth)
            DiceType.D6 -> drawCube(color, strokeColor, strokeWidth)
            DiceType.D8 -> drawOctahedron(color, strokeColor, strokeWidth)
            DiceType.D10 -> drawTrapezohedron(color, strokeColor, strokeWidth)
            DiceType.D12 -> drawDodecahedron(color, strokeColor, strokeWidth)
            DiceType.D20 -> drawIcosahedron(color, strokeColor, strokeWidth)
            DiceType.D30 -> drawRhombicTriacontahedron(color, strokeColor, strokeWidth)
            DiceType.D100 -> drawD100(color, strokeColor, strokeWidth)
        }
    }
}

private fun DrawScope.drawTetrahedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x - radius * 0.866f, center.y + radius * 0.5f)
        lineTo(center.x + radius * 0.866f, center.y + radius * 0.5f)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawCube(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val size = size.minDimension / 3
    
    val path = Path().apply {
        moveTo(center.x - size, center.y - size)
        lineTo(center.x + size, center.y - size)
        lineTo(center.x + size, center.y + size)
        lineTo(center.x - size, center.y + size)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawOctahedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.707f, center.y)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.707f, center.y)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawTrapezohedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.5f, center.y - radius * 0.3f)
        lineTo(center.x + radius * 0.5f, center.y + radius * 0.3f)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.5f, center.y + radius * 0.3f)
        lineTo(center.x - radius * 0.5f, center.y - radius * 0.3f)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawDodecahedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        // Simplified dodecahedron representation
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.5f, center.y - radius * 0.5f)
        lineTo(center.x + radius, center.y)
        lineTo(center.x + radius * 0.5f, center.y + radius * 0.5f)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.5f, center.y + radius * 0.5f)
        lineTo(center.x - radius, center.y)
        lineTo(center.x - radius * 0.5f, center.y - radius * 0.5f)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawIcosahedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        // Simplified icosahedron representation
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.6f, center.y - radius * 0.2f)
        lineTo(center.x + radius * 0.6f, center.y + radius * 0.2f)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.6f, center.y + radius * 0.2f)
        lineTo(center.x - radius * 0.6f, center.y - radius * 0.2f)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawRhombicTriacontahedron(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    val path = Path().apply {
        // Simplified rhombic triacontahedron representation
        moveTo(center.x, center.y - radius)
        lineTo(center.x + radius * 0.7f, center.y - radius * 0.3f)
        lineTo(center.x + radius * 0.7f, center.y + radius * 0.3f)
        lineTo(center.x, center.y + radius)
        lineTo(center.x - radius * 0.7f, center.y + radius * 0.3f)
        lineTo(center.x - radius * 0.7f, center.y - radius * 0.3f)
        close()
    }
    
    drawPath(path, fillColor)
    drawPath(path, strokeColor, style = Stroke(width = strokeWidth))
}

private fun DrawScope.drawD100(fillColor: Color, strokeColor: Color, strokeWidth: Float) {
    val center = Offset(size.width / 2, size.height / 2)
    val radius = size.minDimension / 3
    
    // Draw two trapezohedrons for D100
    val path1 = Path().apply {
        moveTo(center.x - radius * 0.4f, center.y - radius)
        lineTo(center.x + radius * 0.4f, center.y - radius)
        lineTo(center.x + radius * 0.6f, center.y)
        lineTo(center.x + radius * 0.4f, center.y + radius)
        lineTo(center.x - radius * 0.4f, center.y + radius)
        lineTo(center.x - radius * 0.6f, center.y)
        close()
    }
    
    val path2 = Path().apply {
        moveTo(center.x - radius * 0.2f, center.y - radius * 0.8f)
        lineTo(center.x + radius * 0.2f, center.y - radius * 0.8f)
        lineTo(center.x + radius * 0.4f, center.y)
        lineTo(center.x + radius * 0.2f, center.y + radius * 0.8f)
        lineTo(center.x - radius * 0.2f, center.y + radius * 0.8f)
        lineTo(center.x - radius * 0.4f, center.y)
        close()
    }
    
    drawPath(path1, fillColor.copy(alpha = 0.7f))
    drawPath(path1, strokeColor, style = Stroke(width = strokeWidth))
    drawPath(path2, fillColor.copy(alpha = 0.5f))
    drawPath(path2, strokeColor, style = Stroke(width = strokeWidth))
} 