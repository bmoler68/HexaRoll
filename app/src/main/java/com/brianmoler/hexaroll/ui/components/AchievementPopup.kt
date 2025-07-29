package com.brianmoler.hexaroll.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.brianmoler.hexaroll.data.Achievement
import com.brianmoler.hexaroll.ui.theme.*

@Composable
fun AchievementPopup(
    achievement: Achievement,
    onDismiss: () -> Unit,
    theme: AppTheme
) {
    var isVisible by remember { mutableStateOf(true) }
    
    // Auto-dismiss after 3 seconds
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
        isVisible = false
        onDismiss()
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(durationMillis = 500, easing = EaseOutBack)
        ) + fadeIn(animationSpec = tween(500)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(durationMillis = 300, easing = EaseInBack)
        ) + fadeOut(animationSpec = tween(300))
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            AchievementPopupContent(
                achievement = achievement,
                theme = theme,
                onDismiss = {
                    isVisible = false
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun AchievementPopupContent(
    achievement: Achievement,
    theme: AppTheme,
    onDismiss: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "achievement_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_animation"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (theme) {
                AppTheme.CYBERPUNK -> CyberpunkColors.ElevatedCardBackground
                AppTheme.FANTASY -> FantasyColors.ElevatedCardBackground
                AppTheme.SCI_FI -> SciFiColors.ElevatedCardBackground
                AppTheme.WESTERN -> WesternColors.ElevatedCardBackground
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 3.dp,
            color = achievement.tier.color.copy(alpha = glowAlpha)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Achievement icon with glow effect
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = achievement.tier.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(40.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = achievement.tier.color.copy(alpha = glowAlpha),
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = achievement.icon,
                    fontSize = 40.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Achievement tier badge
            Text(
                text = achievement.tier.displayName,
                color = achievement.tier.color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = achievement.tier.color.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Achievement name
            Text(
                text = "ACHIEVEMENT UNLOCKED!",
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.NeonYellow
                    AppTheme.FANTASY -> FantasyColors.NeonYellow
                    AppTheme.SCI_FI -> SciFiColors.NeonYellow
                    AppTheme.WESTERN -> WesternColors.NeonYellow
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Achievement name
            Text(
                text = achievement.name,
                color = achievement.tier.color,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Achievement description
            Text(
                text = achievement.description,
                color = when (theme) {
                    AppTheme.CYBERPUNK -> CyberpunkColors.PrimaryText
                    AppTheme.FANTASY -> FantasyColors.PrimaryText
                    AppTheme.SCI_FI -> SciFiColors.PrimaryText
                    AppTheme.WESTERN -> WesternColors.PrimaryText
                },
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Dismiss button
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = achievement.tier.color
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Awesome!",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AchievementNotification(
    achievements: List<Achievement>,
    onDismiss: () -> Unit,
    theme: AppTheme
) {
    if (achievements.isNotEmpty()) {
        val achievement = achievements.first()
        
        AchievementPopup(
            achievement = achievement,
            onDismiss = onDismiss,
            theme = theme
        )
    }
} 