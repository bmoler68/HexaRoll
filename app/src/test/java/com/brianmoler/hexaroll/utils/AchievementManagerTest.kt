package com.brianmoler.hexaroll.utils

import com.brianmoler.hexaroll.data.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After

/**
 * Unit tests for AchievementManager
 * 
 * Tests the achievement system logic including:
 * - Achievement definitions and structure
 * - Achievement categories and tiers
 * - Basic achievement manager functionality
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AchievementManagerTest {

    @Mock
    private lateinit var mockAchievementStorage: AchievementStorage
    
    private lateinit var achievementManager: AchievementManager
    private val testDispatcher = TestCoroutineDispatcher()
    
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        achievementManager = AchievementManager(mockAchievementStorage)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
    
    @Test
    fun `achievement definitions should contain all expected achievements`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        assertNotNull("Achievements should not be null", achievements)
        assertTrue("Should have multiple achievements", achievements.size > 0)
        
        // Check for some key achievements
        val achievementIds = achievements.map { it.id }
        assertTrue("Should have first roll achievement", achievementIds.contains("first_roll"))
        assertTrue("Should have roll master achievements", achievementIds.contains("roll_master_100"))
        assertTrue("Should have dice specialist achievements", achievementIds.contains("d6_specialist"))
    }
    
    @Test
    fun `achievements should have valid properties`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        achievements.forEach { achievement ->
            assertNotNull("Achievement ID should not be null", achievement.id)
            assertNotNull("Achievement name should not be null", achievement.name)
            assertNotNull("Achievement description should not be null", achievement.description)
            assertNotNull("Achievement category should not be null", achievement.category)
            assertNotNull("Achievement tier should not be null", achievement.tier)
            assertNotNull("Achievement icon should not be null", achievement.icon)
        }
    }
    
    @Test
    fun `achievement categories should be valid`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        val validCategories = listOf(
            AchievementCategory.ROLLING_MILESTONES,
            AchievementCategory.DICE_SPECIALISTS,
            AchievementCategory.RESULT_BASED,
            AchievementCategory.STREAK_PATTERNS,
            AchievementCategory.COMBINATION_MODIFIERS,
            AchievementCategory.THEME_BASED,
            AchievementCategory.FAVORITES_HISTORY,
            AchievementCategory.SPECIAL_EVENTS
        )
        
        achievements.forEach { achievement ->
            assertTrue("Achievement category should be valid", 
                      validCategories.contains(achievement.category))
        }
    }
    
    @Test
    fun `achievement tiers should be valid`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        val validTiers = listOf(
            AchievementTier.BRONZE,
            AchievementTier.SILVER,
            AchievementTier.GOLD,
            AchievementTier.PLATINUM,
            AchievementTier.DIAMOND
        )
        
        achievements.forEach { achievement ->
            assertTrue("Achievement tier should be valid", 
                      validTiers.contains(achievement.tier))
        }
    }
    
    @Test
    fun `achievements should have appropriate maxProgress values`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        achievements.forEach { achievement ->
            when (achievement.id) {
                "marathon_roller" -> assertEquals("Marathon roller should have 18000 max progress", 
                                                18000, achievement.maxProgress)
                "speed_demon" -> assertEquals("Speed demon should have 10 max progress", 
                                            10, achievement.maxProgress)
                "d6_specialist" -> assertEquals("D6 specialist should have 100 max progress", 
                                              100, achievement.maxProgress)
                "first_roll" -> assertEquals("First roll should have 1 max progress", 
                                           1, achievement.maxProgress)
                else -> assertTrue("Achievement should have reasonable maxProgress", 
                                 achievement.maxProgress > 0)
            }
        }
    }
    
    @Test
    fun `newly unlocked achievements should start empty`() = runBlockingTest {
        val newlyUnlocked = achievementManager.newlyUnlockedAchievements.value
        
        assertNotNull("Newly unlocked achievements should not be null", newlyUnlocked)
        assertTrue("Newly unlocked achievements should start empty", newlyUnlocked.isEmpty())
    }
    
    @Test
    fun `achievement definitions should contain specific achievement types`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        // Check for rolling milestone achievements
        val rollingMilestones = achievements.filter { it.category == AchievementCategory.ROLLING_MILESTONES }
        assertTrue("Should have rolling milestone achievements", rollingMilestones.isNotEmpty())
        
        // Check for dice specialist achievements
        val diceSpecialists = achievements.filter { it.category == AchievementCategory.DICE_SPECIALISTS }
        assertTrue("Should have dice specialist achievements", diceSpecialists.isNotEmpty())
        
        // Check for result-based achievements
        val resultBased = achievements.filter { it.category == AchievementCategory.RESULT_BASED }
        assertTrue("Should have result-based achievements", resultBased.isNotEmpty())
    }
    
    @Test
    fun `achievement definitions should have correct structure`() = runBlockingTest {
        val achievements = achievementManager.achievements.value
        
        // Check that all achievements have unique IDs
        val ids = achievements.map { it.id }
        assertEquals("All achievement IDs should be unique", ids.size, ids.distinct().size)
        
        // Check that all achievements have non-empty names and descriptions
        achievements.forEach { achievement ->
            assertTrue("Achievement name should not be empty", achievement.name.isNotEmpty())
            assertTrue("Achievement description should not be empty", achievement.description.isNotEmpty())
            assertTrue("Achievement icon should not be empty", achievement.icon.isNotEmpty())
        }
    }
}
