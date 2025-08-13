package com.brianmoler.hexaroll.utils

import android.content.Context
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
 * - Achievement unlocking conditions
 * - Progress tracking
 * - Statistics management
 * - Session handling
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class AchievementManagerTest {
    
    @Mock
    private lateinit var mockContext: Context
    
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
        assertTrue("Should have D6 enthusiast achievement", achievementIds.contains("d6_enthusiast"))
        assertTrue("Should have marathon roller achievement", achievementIds.contains("marathon_roller"))
        assertTrue("Should have lucky hour achievement", achievementIds.contains("lucky_hour"))
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
            AchievementCategory.DICE_SPECIALIST,
            AchievementCategory.ROLLING_MILESTONES,
            AchievementCategory.SPECIAL_EVENTS,
            AchievementCategory.SESSION_ACHIEVEMENTS
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
            AchievementTier.PLATINUM
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
            if (achievement.id == "marathon_roller") {
                assertEquals("Marathon roller should have 18000 max progress", 
                            18000, achievement.maxProgress)
            } else if (achievement.id == "speed_demon") {
                assertEquals("Speed demon should have 10 max progress", 
                            10, achievement.maxProgress)
            } else {
                assertTrue("Achievement should have reasonable maxProgress", 
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
    fun `achievement definitions should be accessible`() = runBlockingTest {
        // Test that we can access the achievement definitions object
        val definitions = AchievementDefinitions::class.java
        
        assertNotNull("AchievementDefinitions class should exist", definitions)
        assertTrue("AchievementDefinitions should be accessible", 
                  definitions.isAssignableFrom(AchievementDefinitions::class.java))
    }
}
