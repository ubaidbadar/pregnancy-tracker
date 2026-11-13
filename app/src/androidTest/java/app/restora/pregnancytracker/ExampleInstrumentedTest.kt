package app.restora.pregnancytracker

import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import app.restora.pregnancytracker.ui.screens.DueDateScreen
import app.restora.pregnancytracker.ui.screens.LanguageScreen
import app.restora.pregnancytracker.ui.screens.TodayScreen
import app.restora.pregnancytracker.ui.theme.BloomTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class BloomDesignSmokeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun onboardingShowsBloomBrandAndLanguageChoices() {
        composeRule.setContent {
            BloomTheme {
                LanguageScreen(
                    selectedLanguage = "en",
                    onSelectLanguage = {},
                    onContinue = {}
                )
            }
        }

        composeRule.onNodeWithText("Welcome to Bloom").assertIsDisplayed()
        composeRule.onNodeWithText("English").assertIsDisplayed()
        composeRule.onNodeWithText("اردو").assertIsDisplayed()
        composeRule.onNodeWithText("Continue").assertIsDisplayed()
    }

    @Test
    fun dueDateCannotContinueUntilTheUserSelectsADate() {
        composeRule.setContent {
            BloomTheme {
                DueDateScreen(
                    dueDateMillis = 0L,
                    language = "en",
                    onDateSelected = {},
                    onContinue = {}
                )
            }
        }

        composeRule.onNodeWithText("Continue").assertIsNotEnabled()
    }

    @Test
    fun todayDoesNotShipThePrototypePregnancyWeekAsARealValue() {
        composeRule.setContent {
            BloomTheme {
                TodayScreen(
                    dueDateMillis = 0L,
                    language = "en",
                    nickname = "",
                    mood = "",
                    trackingPaused = false,
                    pregnancyEnded = false,
                    pregnancyArchived = false,
                    onProfile = {},
                    onMoodSelected = {},
                    onKicks = {},
                    onContractions = {},
                    onNote = {},
                    onWeekStory = {},
                    onResumeTracking = {}
                )
            }
        }

        composeRule.onNodeWithText("How are you today?").assertIsDisplayed()
        composeRule.onNodeWithText("18 weeks, 3 days").assertDoesNotExist()
    }
}
