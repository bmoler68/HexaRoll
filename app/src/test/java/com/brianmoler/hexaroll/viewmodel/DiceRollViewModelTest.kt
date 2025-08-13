package com.brianmoler.hexaroll.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.brianmoler.hexaroll.data.DiceType
import com.brianmoler.hexaroll.data.DiceSelection
import com.brianmoler.hexaroll.data.DiceCustomization
import com.brianmoler.hexaroll.data.AppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

/**
 * Unit tests for DiceRollViewModel
 * 
 * Tests the core business logic and state management
 * of the main ViewModel that drives the application.
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DiceRollViewModelTest {
    
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @Mock
    private lateinit var mockApplication: Application
    
    private lateinit var viewModel: DiceRollViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DiceRollViewModel(mockApplication)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    
    @Test
    fun `initial state should have all dice counts set to 0`() = runBlockingTest {
        val diceSelections = viewModel.diceSelections.value
        
        assertNotNull("Dice selections should not be null", diceSelections)
        assertEquals("Should have all dice types", 8, diceSelections.size)
        
        diceSelections.values.forEach { selection ->
            assertEquals("Initial count should be 0", 0, selection.count)
        }
        
        // Verify all dice types are present
        assertTrue("D4 should be present", diceSelections.containsKey(DiceType.D4))
        assertTrue("D6 should be present", diceSelections.containsKey(DiceType.D6))
        assertTrue("D8 should be present", diceSelections.containsKey(DiceType.D8))
        assertTrue("D10 should be present", diceSelections.containsKey(DiceType.D10))
        assertTrue("D12 should be present", diceSelections.containsKey(DiceType.D12))
        assertTrue("D20 should be present", diceSelections.containsKey(DiceType.D20))
        assertTrue("D30 should be present", diceSelections.containsKey(DiceType.D30))
        assertTrue("D100 should be present", diceSelections.containsKey(DiceType.D100))
    }
    
    @Test
    fun `initial modifier should be 0`() = runBlockingTest {
        assertEquals("Initial modifier should be 0", 0, viewModel.modifier.value)
    }
    
    @Test
    fun `initial roll history should be empty`() = runBlockingTest {
        val rollHistory = viewModel.rollHistory.value
        assertNotNull("Roll history should not be null", rollHistory)
        assertTrue("Roll history should be empty initially", rollHistory.isEmpty())
    }
    
    @Test
    fun `initial current result should be null`() = runBlockingTest {
        assertNull("Current result should be null initially", viewModel.currentResult.value)
    }
    
    @Test
    fun `initial preset rolls should be empty`() = runBlockingTest {
        val presetRolls = viewModel.presetRolls.value
        assertNotNull("Preset rolls should not be null", presetRolls)
        assertTrue("Preset rolls should be empty initially", presetRolls.isEmpty())
    }
    
    @Test
    fun `initial customization should have default theme`() = runBlockingTest {
        val customization = viewModel.customization.value
        assertNotNull("Customization should not be null", customization)
        assertEquals("Default theme should be Cyberpunk", AppTheme.CYBERPUNK, customization.theme)
    }
    
    @Test
    fun `initial preset loaded message should be null`() = runBlockingTest {
        assertNull("Preset loaded message should be null initially", viewModel.presetLoadedMessage.value)
    }
    
    @Test
    fun `achievements should be accessible`() = runBlockingTest {
        assertNotNull("Achievements should not be null", viewModel.achievements.value)
        assertNotNull("Newly unlocked achievements should not be null", viewModel.newlyUnlockedAchievements.value)
    }
    
    @Test
    fun `dice selections should be properly structured`() = runBlockingTest {
        val diceSelections = viewModel.diceSelections.value
        
        diceSelections.values.forEach { selection ->
            assertNotNull("Dice type should not be null", selection.diceType)
            assertTrue("Count should be non-negative", selection.count >= 0)
            assertTrue("Count should be within reasonable bounds", selection.count <= 100)
        }
    }
    
    @Test
    fun `customization should have valid theme`() = runBlockingTest {
        val customization = viewModel.customization.value
        assertNotNull("Theme should not be null", customization.theme)
        
        // Verify theme is one of the valid options
        val validThemes = listOf(
            AppTheme.CYBERPUNK,
            AppTheme.FANTASY,
            AppTheme.SCI_FI,
            AppTheme.WESTERN,
            AppTheme.ANCIENT
        )
        assertTrue("Theme should be valid", validThemes.contains(customization.theme))
    }
}
