package com.brianmoler.hexaroll.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brianmoler.hexaroll.data.AppTheme
import com.brianmoler.hexaroll.ui.theme.ThemeBackgrounds

/**
 * Smart scaling modes for background images
 */
enum class BackgroundFitMode {
    /**
     * Fills the entire screen, may crop parts of the image
     * Best for: When you want complete coverage regardless of aspect ratio
     */
    FILL_SCREEN,
    
    /**
     * Fits the entire image within the screen bounds, may show letterboxing
     * Best for: When you want to see the complete image without cropping
     */
    FIT_SCREEN,
    
    /**
     * Stretches the image to exactly fit screen dimensions
     * Best for: When image distortion is acceptable for perfect fit
     */
    STRETCH,
    
    /**
     * Intelligently chooses between FILL_SCREEN and FIT_SCREEN based on aspect ratios
     * Best for: Automatic optimization for different screen types
     */
    SMART_SCALE,
    
    /**
     * Uses the provided ContentScale directly
     * Best for: When you want manual control over scaling
     */
    MANUAL
}

/**
 * ThemedBackground - A composable that displays a theme-specific background image with smart scaling
 * 
 * This enhanced component provides intelligent scaling options to ensure background images
 * look perfect on different screen sizes and aspect ratios. It automatically adapts to
 * device characteristics for optimal visual experience.
 * 
 * @param theme The current application theme
 * @param modifier Modifier to be applied to the container
 * @param fitMode How the background should be scaled to fit the screen (default: SMART_SCALE)
 * @param contentScale Manual ContentScale (only used when fitMode is MANUAL)
 * @param alpha Opacity of the background image (0.0 = transparent, 1.0 = opaque)
 * @param content The content to display over the background
 */
@Composable
fun ThemedBackground(
    theme: AppTheme,
    modifier: Modifier = Modifier,
    fitMode: BackgroundFitMode = BackgroundFitMode.SMART_SCALE,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1.0f,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    // Calculate screen dimensions
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenAspectRatio = screenWidthDp.value / screenHeightDp.value
    
    // Determine optimal content scale based on fit mode and screen characteristics
    val optimalContentScale = remember(fitMode, screenAspectRatio) {
        when (fitMode) {
            BackgroundFitMode.FILL_SCREEN -> ContentScale.Crop
            BackgroundFitMode.FIT_SCREEN -> ContentScale.Fit
            BackgroundFitMode.STRETCH -> ContentScale.FillBounds
            BackgroundFitMode.MANUAL -> contentScale
            BackgroundFitMode.SMART_SCALE -> {
                // Smart scaling logic based on screen characteristics
                when {
                    // Very tall screens (like modern phones 18:9+)
                    screenAspectRatio < 0.5f -> ContentScale.Crop
                    
                    // Very wide screens (like tablets in landscape)
                    screenAspectRatio > 1.8f -> ContentScale.Crop
                    
                    // Square-ish screens (like some tablets or foldables)
                    screenAspectRatio in 0.9f..1.1f -> ContentScale.Fit
                    
                    // Standard phone aspect ratios (16:9, 16:10)
                    screenAspectRatio in 0.5f..0.8f -> {
                        // Use Crop for phones to ensure full coverage
                        ContentScale.Crop
                    }
                    
                    // Default fallback
                    else -> ContentScale.Crop
                }
            }
        }
    }
    
    Box(modifier = modifier.fillMaxSize()) {
        // Background image layer with smart scaling
        Image(
            painter = painterResource(id = ThemeBackgrounds.getBackgroundResource(theme)),
            contentDescription = "Theme background for ${theme.name.lowercase()} theme",
            modifier = Modifier.fillMaxSize(),
            contentScale = optimalContentScale,
            alpha = alpha
        )
        
        // Content overlay layer
        content()
    }
}
