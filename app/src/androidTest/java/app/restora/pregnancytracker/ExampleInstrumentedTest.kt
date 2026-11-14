package app.restora.pregnancytracker

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import app.restora.pregnancytracker.ui.screens.DueDateScreen
import app.restora.pregnancytracker.ui.screens.LanguageScreen
import app.restora.pregnancytracker.ui.screens.MeScreen
import app.restora.pregnancytracker.ui.BloomApp
import app.restora.pregnancytracker.ui.screens.TodayScreen
import app.restora.pregnancytracker.ui.screens.ToolsScreen
import app.restora.pregnancytracker.ui.theme.BloomTheme
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
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
    @Test
    fun endedPregnancyDoesNotShowPregnancyTrackingActions() {
        composeRule.setContent {
            BloomTheme {
                TodayScreen(
                    dueDateMillis = 0L,
                    language = "en",
                    nickname = "",
                    mood = "",
                    trackingPaused = true,
                    pregnancyEnded = true,
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

        composeRule.onNodeWithText("How are you today?").assertDoesNotExist()
        composeRule.onNodeWithText("Kicks").assertDoesNotExist()
        composeRule.onNodeWithText("Contractions").assertDoesNotExist()
    }

    @Test
    fun quietProfileKeepsPrivacyToolsButHidesPremiumUpsell() {
        composeRule.setContent {
            BloomTheme {
                MeScreen(
                    userLabel = "No label",
                    partnerLabel = "Skip",
                    language = "en",
                    quietMode = true,
                    onLanguageChange = {},
                    onExport = {},
                    onPauseOrEnd = {},
                    onPremium = {},
                    onTogether = {},
                    onBabyArrived = {},
                    onDeleteEverything = {}
                )
            }
        }

        composeRule.onNodeWithText("Export my data").assertIsDisplayed()
        composeRule.onNodeWithText("Bloom Plus").assertDoesNotExist()
    }

    @Test
    fun onboardingPrimaryActionStaysInTheBottomSafeArea() {
        composeRule.setContent {
            BloomTheme {
                LanguageScreen(
                    selectedLanguage = "en",
                    onSelectLanguage = {},
                    onContinue = {}
                )
            }
        }

        val root = composeRule.onRoot().getUnclippedBoundsInRoot()
        val continueButton = composeRule.onNodeWithText("Continue").getUnclippedBoundsInRoot()
        val bottomClearance = with(composeRule.density) { 120.dp.toPx() }
        assertTrue(continueButton.bottom >= root.bottom - bottomClearance)
    }

    @Test
    fun toolsCardsUseTheReferenceTwoColumnLayout() {
        composeRule.setContent {
            BloomTheme {
                ToolsScreen(
                    onKickCounter = {},
                    onContractions = {},
                    onQuestions = {},
                    onHospitalBag = {}
                )
            }
        }

        val kick = composeRule.onNodeWithText("Kick counter").getUnclippedBoundsInRoot()
        val contractions = composeRule.onNodeWithText("Contractions").getUnclippedBoundsInRoot()
        val questions = composeRule.onNodeWithText("Questions").getUnclippedBoundsInRoot()
        val hospitalBag = composeRule.onNodeWithText("Hospital bag").getUnclippedBoundsInRoot()

        assertTrue(kick.left < contractions.left)
        assertTrue(questions.left < hospitalBag.left)
        assertTrue(questions.top > kick.top)
    }

    @Test
    fun kickSessionBackStackReturnsToItsOrigin() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val preferences = context.getSharedPreferences("bloom_local_preferences", Context.MODE_PRIVATE)
        preferences.edit()
            .clear()
            .putBoolean("onboarding_complete", true)
            .putString("language", "en")
            .commit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)?.applicationLocales =
                LocaleList.forLanguageTags("en")
        }

        try {
            composeRule.setContent { BloomApp() }

            composeRule.onNodeWithText("Tools").performClick()
            composeRule.onNodeWithText("Kick counter").performClick()
            composeRule.onNodeWithText("Tap").performClick()
            composeRule.onNodeWithText("End session").performClick()
            composeRule.onNodeWithText("Session finished").assertIsDisplayed()
            composeRule.onNodeWithText("Done").performClick()
            composeRule.onNodeWithText("Kick counter").assertIsDisplayed()

            composeRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }
            composeRule.onNodeWithText("How are you today?").assertIsDisplayed()
        } finally {
            preferences.edit().clear().commit()
        }
    }

}
