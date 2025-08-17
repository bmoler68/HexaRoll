package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.ui.theme.ThemeBackgrounds

/**
 * ThemedBackground - A composable that displays a theme-specific background image
 * 
 * This component provides a consistent way to apply theme backgrounds across the app.
 * It displays the appropriate background image for the current theme and overlays
 * the provided content on top.
 * 
 * @param theme The current application theme
 * @param modifier Modifier to be applied to the container
 * @param contentScale How the background image should be scaled (default: Crop)
 * @param alpha Opacity of the background image (0.0 = transparent, 1.0 = opaque)
 * @param content The content to display over the background
 */
@Composable
fun ThemedBackground(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1.0f,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Background image layer
        Image(
            painter = painterResource(id = ThemeBackgrounds.getBackgroundResource(theme)),
            contentDescription = "Theme background for ${theme.name.lowercase()} theme",
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            alpha = alpha
        )
        
        // Content overlay layer
        content()
    }
}
