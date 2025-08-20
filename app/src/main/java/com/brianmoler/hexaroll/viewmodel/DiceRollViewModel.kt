package com.brianmoler.hexaroll.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.brianmoler.hexaroll.data.*
import com.brianmoler.hexaroll.ui.components.BackgroundFitMode
import com.brianmoler.hexaroll.utils.AchievementManager
import com.brianmoler.hexaroll.utils.AchievementStorage
import com.brianmoler.hexaroll.utils.ErrorHandler
import com.brianmoler.hexaroll.utils.PresetStorage
import com.brianmoler.hexaroll.utils.RollHistoryStorage
import com.brianmoler.hexaroll.utils.ThemeStorage
import com.brianmoler.hexaroll.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * DiceRollViewModel - Main ViewModel for the HexaRoll dice rolling application
 * 
 * This ViewModel manages all the application state and business logic including:
 * - Dice selection and counting
 * - Roll history and results
 * - Preset management
 * - Theme customization
 * - Achievement tracking
 * - Data persistence
 * 
 * Architecture:
 * - Uses StateFlow for reactive state management
 * - Integrates with various storage utilities for persistence
 * - Handles error scenarios gracefully
 * - Manages achievement system integration
 */
class DiceRollViewModel(application: Application) : AndroidViewModel(application) {
    
    // Storage utilities for data persistence
    private val presetStorage = PresetStorage(application)
    private val rollHistoryStorage = RollHistoryStorage(application)
    private val themeStorage = ThemeStorage(application)
    private val achievementStorage = AchievementStorage(application)
    private val achievementManager = AchievementManager(achievementStorage)
    
    // State management for dice selections
    // Maps each dice type to its current count (0 by default)
    private val _diceSelections = MutableStateFlow(
        DiceType.entries.associateWith { DiceSelection(it, 0) }
    )
    val diceSelections: StateFlow<Map<DiceType, DiceSelection>> = _diceSelections.asStateFlow()
    
    // State management for roll modifier
    // Can be positive or negative to add/subtract from roll results
    private val _modifier = MutableStateFlow(0)
    val modifier: StateFlow<Int> = _modifier.asStateFlow()
    
    // State management for roll history
    // Stores all previous roll results for the history screen
    private val _rollHistory = MutableStateFlow<List<RollResult>>(emptyList())
    val rollHistory: StateFlow<List<RollResult>> = _rollHistory.asStateFlow()
    
    // State management for current roll result
    // Stores the most recent roll result for display
    private val _currentResult = MutableStateFlow<RollResult?>(null)
    val currentResult: StateFlow<RollResult?> = _currentResult.asStateFlow()
    
    // State management for preset rolls
    // Stores user-defined preset configurations
    private val _presetRolls = MutableStateFlow<List<PresetRoll>>(emptyList())
    val presetRolls: StateFlow<List<PresetRoll>> = _presetRolls.asStateFlow()
    
    // State management for app customization
    // Stores theme and other customization settings
    private val _customization = MutableStateFlow(DiceCustomization())
    val customization: StateFlow<DiceCustomization> = _customization.asStateFlow()
    
    // State management for preset loading messages
    // Shows feedback when presets are loaded
    private val _presetLoadedMessage = MutableStateFlow<String?>(null)
    val presetLoadedMessage: StateFlow<String?> = _presetLoadedMessage.asStateFlow()
    
    // Achievement-related StateFlows
    // Delegated to the AchievementManager for centralized achievement handling
    val achievements = achievementManager.achievements
    val newlyUnlockedAchievements = achievementManager.newlyUnlockedAchievements

    /**
     * Initialize the ViewModel
     * 
     * Loads all persistent data from storage:
     * - User presets
     * - Theme preferences
     * - Roll history
     */
    init {
        loadPresetsFromStorage()
        loadThemeFromStorage()
        loadRollHistory()
    }
    
    /**
     * Load presets from persistent storage
     * 
     * Retrieves user-defined preset configurations and updates the state.
     * Also tracks preset count for achievement purposes.
     */
    private fun loadPresetsFromStorage() {
        viewModelScope.launch(ErrorHandler.coroutineExceptionHandler) {
            try {
                val presets = presetStorage.loadPresets()
                _presetRolls.value = presets
                
                // Track loaded favorites for achievements
                if (presets.isNotEmpty()) {
                    achievementManager.onFavoritesLoaded(presets.size)
                }
            } catch (e: Exception) {
                ErrorHandler.handleStorageError(getApplication(), "loading presets", e)
                _presetRolls.value = emptyList()
            }
        }
    }
    
    /**
     * Save presets to persistent storage
     * 
     * Persists the current preset configurations to device storage.
     */
    private fun savePresetsToStorage() {
        viewModelScope.launch(ErrorHandler.coroutineExceptionHandler) {
            try {
                presetStorage.savePresets(_presetRolls.value)
            } catch (e: Exception) {
                ErrorHandler.handleStorageError(getApplication(), "saving presets", e)
            }
        }
    }
    
    /**
     * Update the count for a specific dice type
     * 
     * @param diceType The type of dice to update
     * @param newCount The new count (must be non-negative)
     * 
     * Validates the count and updates the dice selections state.
     * Ensures counts are non-negative and within reasonable limits.
     */
    private fun updateDiceCount(diceType: DiceType, newCount: Int) {
        if (!ErrorHandler.validateDiceCount(newCount)) {
            ErrorHandler.handleValidationError(getApplication(), "dice count", newCount)
            return
        }
        
        _diceSelections.update { selections ->
            selections.toMutableMap().apply {
                put(diceType, DiceSelection(diceType, newCount.coerceAtLeast(0)))
            }
        }
    }
    
    /**
     * Increment the count for a specific dice type
     * 
     * @param diceType The type of dice to increment
     * 
     * Increases the count by 1 and updates the state.
     */
    fun incrementDice(diceType: DiceType) {
        val currentCount = _diceSelections.value[diceType]?.count ?: 0
        updateDiceCount(diceType, currentCount + 1)
    }
    
    /**
     * Decrement the count for a specific dice type
     * 
     * @param diceType The type of dice to decrement
     * 
     * Decreases the count by 1 and updates the state.
     * Count cannot go below 0.
     */
    fun decrementDice(diceType: DiceType) {
        val currentCount = _diceSelections.value[diceType]?.count ?: 0
        updateDiceCount(diceType, currentCount - 1)
    }

    /**
     * Increment the roll modifier
     * 
     * Increases the modifier by 1, which will be added to roll results.
     */
    fun incrementModifier() {
        _modifier.value += 1
    }
    
    /**
     * Decrement the roll modifier
     * 
     * Decreases the modifier by 1, which will be subtracted from roll results.
     */
    fun decrementModifier() {
        _modifier.value -= 1
    }
    
    /**
     * Perform a dice roll with the current selections
     * 
     * This is the main dice rolling function that:
     * - Validates that dice are selected
     * - Generates random rolls for each dice type
     * - Handles special D100 percentile dice logic
     * - Calculates total results
     * - Updates history and achievements
     * - Triggers achievement checks
     */
    fun rollDice() {
        Log.d("DiceRollViewModel", "rollDice() called")
        val selections = _diceSelections.value.values.filter { it.count > 0 }
        if (selections.isEmpty()) {
            Log.d("DiceRollViewModel", "No dice selected, returning")
            return
        }
        
        val individualRolls = mutableListOf<List<Int>>()
        val d100Rolls = mutableListOf<D100Roll>()
        var total = _modifier.value
        
        selections.forEach { selection ->
            if (selection.diceType == DiceType.D100) {
                // Special handling for D100 (percentile dice)
                val percentileRolls = List(selection.count) {
                    val tensDie = Random.nextInt(0, 10) // 0-9
                    val onesDie = Random.nextInt(0, 10) // 0-9
                    val result = if (tensDie == 0 && onesDie == 0) 100 else (tensDie * 10 + onesDie)
                    d100Rolls.add(D100Roll(tensDie, onesDie, result))
                    result
                }
                individualRolls.add(percentileRolls)
                total += percentileRolls.sum()
            } else {
                // Normal dice rolling
                val rolls = List(selection.count) { Random.nextInt(1, selection.diceType.sides + 1) }
                individualRolls.add(rolls)
                total += rolls.sum()
            }
        }
        
        val notation = buildRollNotation(selections, _modifier.value)
        val rollResult = RollResult(
            diceSelections = selections,
            modifier = _modifier.value,
            individualRolls = individualRolls,
            d100Rolls = d100Rolls,
            total = total,
            notation = notation
        )
        
        // Update roll history with new roll at the beginning (most recent first)
        _rollHistory.update { history ->
            (listOf(rollResult) + history).take(AppDefaultsData.Data.MAX_ROLL_HISTORY) // Keep latest rolls
        }
        
        // Save to persistent storage
        saveRollHistoryToStorage()
        
        // Trigger achievement checks
        viewModelScope.launch {
            achievementManager.onRollCompleted(rollResult, _customization.value.theme)
        }
        
        // Set the current result for display
        _currentResult.value = rollResult
    }
    
    private fun buildRollNotation(selections: List<DiceSelection>, modifier: Int): String {
        val diceNotation = selections
            .filter { it.count > 0 }
            .joinToString("+") { selection ->
                "${selection.count}${selection.diceType.displayName}"
            }
        
        return when {
            diceNotation.isEmpty() -> "0"
            modifier == 0 -> diceNotation
            modifier > 0 -> "$diceNotation+$modifier"
            else -> "$diceNotation$modifier"
        }
    }

    fun loadPresetRoll(preset: PresetRoll) {
        Log.d("DiceRollViewModel", "Loading preset: ${preset.name}")
        
        // Reset all dice counts
        _diceSelections.update { selections ->
            selections.mapValues { DiceSelection(it.key, 0) }
        }
        
        // Load preset dice selections
        preset.diceSelections.forEach { selection ->
            updateDiceCount(selection.diceType, selection.count)
        }
        
        // Load preset modifier
        _modifier.value = preset.modifier
        
        // Clear the current result when loading a preset
        _currentResult.value = null
        
        // Show confirmation message
        val message = getApplication<Application>().getString(R.string.preset_loaded)
        Log.d("DiceRollViewModel", "Setting preset message: $message")
        _presetLoadedMessage.value = message
        
        // Clear the message after 3 seconds
        viewModelScope.launch {
            kotlinx.coroutines.delay(3000)
            Log.d("DiceRollViewModel", "Clearing preset message")
            _presetLoadedMessage.value = null
        }
    }
    
    fun createPresetFromRoll(rollResult: RollResult, name: String, description: String) {
        val preset = PresetRoll(
            name = name,
            description = description,
            diceSelections = rollResult.diceSelections,
            modifier = rollResult.modifier
        )
        
        _presetRolls.update { presets ->
            presets + preset
        }
        savePresetsToStorage()
        
        // Track favorite creation for achievements
        viewModelScope.launch {
            achievementManager.onFavoriteCreated()
        }
    }
    
    fun removePreset(presetId: String) {
        _presetRolls.update { presets ->
            presets.filter { it.id != presetId }
        }
        savePresetsToStorage()
    }
    
    fun updatePreset(presetId: String, newName: String, newDescription: String) {
        _presetRolls.update { presets ->
            presets.map { preset ->
                if (preset.id == presetId) {
                    preset.copy(name = newName, description = newDescription)
                } else {
                    preset
                }
            }
        }
        savePresetsToStorage()
    }

    fun clearPresetLoadedMessage() {
        _presetLoadedMessage.value = null
    }

    private fun loadThemeFromStorage() {
        viewModelScope.launch {
            try {
                val customization = themeStorage.loadCustomization()
                _customization.value = customization
                
                // Track loaded theme for achievements
                achievementManager.onThemeLoaded(customization.theme)
            } catch (e: Exception) {
                Log.e("DiceRollViewModel", "Error loading customization", e)
                _customization.value = DiceCustomization(theme = AppTheme.CYBERPUNK)
                
                // Track default theme for achievements
                achievementManager.onThemeLoaded(AppTheme.CYBERPUNK)
            }
        }
    }
    
    private fun saveThemeToStorage() {
        viewModelScope.launch {
            try {
                themeStorage.saveCustomization(_customization.value)
            } catch (e: Exception) {
                Log.e("DiceRollViewModel", "Error saving customization", e)
            }
        }
    }
    
    fun updateTheme(theme: AppTheme) {
        _customization.update { current ->
            current.copy(theme = theme)
        }
        saveThemeToStorage()
        
        // Track theme change for achievements
        viewModelScope.launch {
            achievementManager.onThemeChanged(theme)
        }
    }
    


    fun clearArena() {
        // Reset all dice counts to 0
        _diceSelections.update { selections ->
            selections.mapValues { DiceSelection(it.key, 0) }
        }
        
        // Reset modifier to 0
        _modifier.value = 0
        
        // Clear the current result display
        _currentResult.value = null
        
        // Note: Roll history is preserved for the History tab
    }

    private fun loadRollHistory() {
        viewModelScope.launch {
            try {
                val history = rollHistoryStorage.loadRollHistory()
                _rollHistory.value = history
            } catch (e: Exception) {
                Log.e("DiceRollViewModel", "Error loading roll history", e)
                _rollHistory.value = emptyList()
            }
        }
    }

    private fun saveRollHistoryToStorage() {
        viewModelScope.launch {
            try {
                rollHistoryStorage.saveRollHistory(_rollHistory.value)
            } catch (e: Exception) {
                Log.e("DiceRollViewModel", "Error saving roll history", e)
            }
        }
    }
    
    fun clearRollHistory() {
        viewModelScope.launch {
            try {
                rollHistoryStorage.clearRollHistory()
                _rollHistory.value = emptyList()
            } catch (e: Exception) {
                Log.e("DiceRollViewModel", "Error clearing roll history", e)
            }
        }
    }

    fun getCompletionPercentage() = achievementManager.completionPercentage

    fun resetAllProgress() {
        viewModelScope.launch {
            achievementManager.resetAllProgress()
        }
    }
    
    fun onHistoryViewed() {
        viewModelScope.launch {
            achievementManager.onHistoryViewed()
        }
    }
    

} 