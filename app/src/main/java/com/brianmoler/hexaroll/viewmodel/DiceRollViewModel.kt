package com.brianmoler.hexaroll.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brianmoler.hexaroll.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DiceRollViewModel : ViewModel() {
    
    private val _diceSelections = MutableStateFlow(
        DiceType.values().associateWith { DiceSelection(it, 0) }
    )
    val diceSelections: StateFlow<Map<DiceType, DiceSelection>> = _diceSelections.asStateFlow()
    
    private val _modifier = MutableStateFlow(0)
    val modifier: StateFlow<Int> = _modifier.asStateFlow()
    
    private val _rollHistory = MutableStateFlow<List<RollResult>>(emptyList())
    val rollHistory: StateFlow<List<RollResult>> = _rollHistory.asStateFlow()
    
    private val _currentResult = MutableStateFlow<RollResult?>(null)
    val currentResult: StateFlow<RollResult?> = _currentResult.asStateFlow()
    
    private val _presetRolls = MutableStateFlow<List<PresetRoll>>(
        listOf(
            PresetRoll(
                name = "Pathfinder",
                description = "Standard D20 system",
                diceSelections = listOf(DiceSelection(DiceType.D20, 1)),
                modifier = 0
            ),
            PresetRoll(
                name = "COC Stats",
                description = "Call of Cthulhu character stats",
                diceSelections = listOf(DiceSelection(DiceType.D6, 3)),
                modifier = 0
            ),
            PresetRoll(
                name = "Fireball",
                description = "D&D 5e Fireball spell",
                diceSelections = listOf(DiceSelection(DiceType.D6, 8)),
                modifier = 0
            )
        )
    )
    val presetRolls: StateFlow<List<PresetRoll>> = _presetRolls.asStateFlow()
    
    private val _customization = MutableStateFlow(DiceCustomization())
    val customization: StateFlow<DiceCustomization> = _customization.asStateFlow()
    
    fun updateDiceCount(diceType: DiceType, newCount: Int) {
        _diceSelections.update { selections ->
            selections.toMutableMap().apply {
                put(diceType, DiceSelection(diceType, newCount.coerceAtLeast(0)))
            }
        }
    }
    
    fun incrementDice(diceType: DiceType) {
        val currentCount = _diceSelections.value[diceType]?.count ?: 0
        // Limit D100 to maximum 1 dice
        if (diceType == DiceType.D100 && currentCount >= 1) return
        updateDiceCount(diceType, currentCount + 1)
    }
    
    fun decrementDice(diceType: DiceType) {
        val currentCount = _diceSelections.value[diceType]?.count ?: 0
        updateDiceCount(diceType, currentCount - 1)
    }
    
    fun updateModifier(newModifier: Int) {
        _modifier.value = newModifier
    }
    
    fun incrementModifier() {
        _modifier.value += 1
    }
    
    fun decrementModifier() {
        _modifier.value -= 1
    }
    
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
        
        val notation = buildRollNotation(selections, _modifier.value, d100Rolls)
        val rollResult = RollResult(
            diceSelections = selections,
            modifier = _modifier.value,
            individualRolls = individualRolls,
            d100Rolls = d100Rolls,
            total = total,
            notation = notation
        )
        
        _rollHistory.update { history ->
            listOf(rollResult) + history.take(99) // Keep last 100 rolls
        }
        
        // Set the current result for display
        _currentResult.value = rollResult
    }
    
    private fun buildRollNotation(selections: List<DiceSelection>, modifier: Int, d100Rolls: List<D100Roll> = emptyList()): String {
        val diceNotation = selections
            .filter { it.count > 0 }
            .joinToString("+") { selection ->
                if (selection.diceType == DiceType.D100) {
                    if (d100Rolls.isNotEmpty()) {
                        // Show the result with D10 breakdown
                        val d100Roll = d100Rolls.first()
                        "${d100Roll.result} [${d100Roll.tensDie},${d100Roll.onesDie}]"
                    } else {
                        "${selection.count}x100"
                    }
                } else {
                    "${selection.count}${selection.diceType.displayName}"
                }
            }
        
        return when {
            diceNotation.isEmpty() -> "0"
            modifier == 0 -> diceNotation
            modifier > 0 -> "$diceNotation+$modifier"
            else -> "$diceNotation$modifier"
        }
    }
    
    fun addPresetRoll(name: String, description: String) {
        val selections = _diceSelections.value.values.filter { it.count > 0 }
        val preset = PresetRoll(
            name = name,
            description = description,
            diceSelections = selections,
            modifier = _modifier.value
        )
        
        _presetRolls.update { presets ->
            presets + preset
        }
    }
    
    fun loadPresetRoll(preset: PresetRoll) {
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
    }
    
    fun removePreset(presetId: String) {
        _presetRolls.update { presets ->
            presets.filter { it.id != presetId }
        }
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
    }
    
    fun saveCurrentRollToPreset(name: String, description: String) {
        val selections = _diceSelections.value.values.filter { it.count > 0 }
        if (selections.isNotEmpty()) {
            val preset = PresetRoll(
                name = name,
                description = description,
                diceSelections = selections,
                modifier = _modifier.value
            )
            
            _presetRolls.update { presets ->
                presets + preset
            }
        }
    }
    
    fun updateCustomization(customization: DiceCustomization) {
        _customization.value = customization
    }
    
    fun getTotalDiceCount(): Int {
        return _diceSelections.value.values.sumOf { it.count }
    }
    
    fun getTotalWithModifier(): Int {
        return getTotalDiceCount() + _modifier.value
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
} 