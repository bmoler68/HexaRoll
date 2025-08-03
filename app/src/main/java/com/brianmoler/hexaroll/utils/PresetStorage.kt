package com.brianmoler.hexaroll.utils

import android.content.Context
import com.brianmoler.hexaroll.data.PresetRoll
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class PresetStorage(private val context: Context) {
    
    private val sharedPreferences = context.getSharedPreferences(
        "hexaroll_presets", 
        Context.MODE_PRIVATE
    )
    
    private val gson = Gson()
    private val presetListType = object : TypeToken<List<PresetRoll>>() {}.type
    
    suspend fun savePresets(presets: List<PresetRoll>) {
        withContext(Dispatchers.IO) {
            val json = gson.toJson(presets, presetListType)
            sharedPreferences.edit {
                putString("presets", json)
            }
        }
    }
    
    suspend fun loadPresets(): List<PresetRoll> {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString("presets", null)
            if (json != null) {
                try {
                    gson.fromJson(json, presetListType) ?: emptyList()
                } catch (e: Exception) {
                    emptyList()
                }
            } else {
                emptyList()
            }
        }
    }

}