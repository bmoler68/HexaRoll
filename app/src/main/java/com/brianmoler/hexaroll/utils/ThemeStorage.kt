package com.brianmoler.hexaroll.utils

import android.content.Context
import android.content.SharedPreferences
import com.brianmoler.hexaroll.data.AppTheme
import com.google.gson.Gson

class ThemeStorage(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "theme_preferences",
        Context.MODE_PRIVATE
    )
    private val gson = Gson()
    
    companion object {
        private const val KEY_SELECTED_THEME = "selected_theme"
    }
    
    fun saveTheme(theme: AppTheme) {
        val themeJson = gson.toJson(theme)
        sharedPreferences.edit()
            .putString(KEY_SELECTED_THEME, themeJson)
            .apply()
    }
    
    fun loadTheme(): AppTheme {
        val themeJson = sharedPreferences.getString(KEY_SELECTED_THEME, null)
        return if (themeJson != null) {
            try {
                gson.fromJson(themeJson, AppTheme::class.java)
            } catch (e: Exception) {
                AppTheme.CYBERPUNK // Default fallback
            }
        } else {
            AppTheme.CYBERPUNK // Default theme
        }
    }
} 