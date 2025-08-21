package com.brianmoler.hexaroll.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * SoundStorage - Handles persistence of sound preferences
 * 
 * This utility class manages sound-related user preferences including:
 * - Sound enabled/disabled state
 * - Volume levels (future enhancement)
 * - Sound effect preferences (future enhancement)
 * 
 * Uses SharedPreferences for lightweight, persistent storage.
 */
class SoundStorage(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "sound_preferences", 
        Context.MODE_PRIVATE
    )
    
    companion object {
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val DEFAULT_SOUND_ENABLED = true
    }
    
    /**
     * Save sound enabled state
     * 
     * @param enabled Whether sound effects should be played
     */
    fun saveSoundEnabled(enabled: Boolean) {
        sharedPreferences.edit {
            putBoolean(KEY_SOUND_ENABLED, enabled)
        }
    }
    
    /**
     * Load sound enabled state
     * 
     * @return true if sound effects are enabled, false otherwise
     */
    fun loadSoundEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_SOUND_ENABLED, DEFAULT_SOUND_ENABLED)
    }
}
