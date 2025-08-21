package com.brianmoler.hexaroll.utils

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.brianmoler.hexaroll.R

/**
 * SoundManager - Handles audio playback for the HexaRoll application
 * 
 * This utility class manages sound effects including:
 * - Dice rolling sounds
 * - Achievement unlock sounds
 * - UI interaction sounds
 * 
 * Features:
 * - Automatic resource cleanup
 * - Error handling for missing audio files
 * - Volume control support
 * - Memory-efficient playback
 */
class SoundManager(private val context: Context) {
    
    private var mediaPlayer: MediaPlayer? = null
    private var isSoundEnabled: Boolean = true
    
    /**
     * Play dice rolling sound effect
     * 
     * Loads and plays the dice rolling sound from raw resources.
     * Automatically handles cleanup and error scenarios.
     */
    fun playDiceRollSound() {
        if (!isSoundEnabled) return
        
        try {
            // Clean up any existing MediaPlayer
            releaseMediaPlayer()
            
            // Create new MediaPlayer for dice roll sound
            mediaPlayer = MediaPlayer.create(context, R.raw.dice_roll)
            
            mediaPlayer?.let { player ->
                // Set completion listener to clean up resources
                player.setOnCompletionListener {
                    releaseMediaPlayer()
                }
                
                // Set error listener for error handling
                player.setOnErrorListener { _, what, extra ->
                    Log.e("SoundManager", "Error playing dice roll sound: what=$what, extra=$extra")
                    releaseMediaPlayer()
                    true
                }
                
                // Start playback
                player.start()
                Log.d("SoundManager", "Dice roll sound started")
            }
            
        } catch (e: Exception) {
            Log.e("SoundManager", "Error playing dice roll sound", e)
            releaseMediaPlayer()
        }
    }
    
    /**
     * Enable or disable sound effects
     * 
     * @param enabled Whether sound effects should be played
     */
    fun setSoundEnabled(enabled: Boolean) {
        isSoundEnabled = enabled
        if (!enabled) {
            releaseMediaPlayer()
        }
        Log.d("SoundManager", "Sound ${if (enabled) "enabled" else "disabled"}")
    }
    
    /**
     * Check if sound is currently enabled
     * 
     * @return true if sound effects are enabled, false otherwise
     */
    fun isSoundEnabled(): Boolean = isSoundEnabled
    
    /**
     * Release MediaPlayer resources
     * 
     * Called automatically when sounds complete or when disabling sound.
     * Also called when the SoundManager is no longer needed.
     */
    private fun releaseMediaPlayer() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
            mediaPlayer = null
        }
    }
    
    /**
     * Clean up resources when SoundManager is no longer needed
     * 
     * Should be called when the SoundManager is being destroyed
     * to prevent memory leaks.
     */
    fun cleanup() {
        releaseMediaPlayer()
        Log.d("SoundManager", "SoundManager cleaned up")
    }
}
