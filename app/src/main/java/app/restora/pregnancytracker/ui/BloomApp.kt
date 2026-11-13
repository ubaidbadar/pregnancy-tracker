package app.restora.pregnancytracker.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.SystemClock
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import app.restora.pregnancytracker.R
import app.restora.pregnancytracker.ui.components.BloomBottomBar
import app.restora.pregnancytracker.ui.components.BloomTab
import app.restora.pregnancytracker.ui.screens.BabyArrivedScreen
import app.restora.pregnancytracker.ui.screens.CheckInDoneScreen
import app.restora.pregnancytracker.ui.screens.ContractionTimerScreen
import app.restora.pregnancytracker.ui.screens.DueDateScreen
import app.restora.pregnancytracker.ui.screens.JournalScreen
import app.restora.pregnancytracker.ui.screens.KickCounterScreen
import app.restora.pregnancytracker.ui.screens.KickSummaryScreen
import app.restora.pregnancytracker.ui.screens.LanguageScreen
import app.restora.pregnancytracker.ui.screens.LossConfirmationScreen
import app.restora.pregnancytracker.ui.screens.MeScreen
import app.restora.pregnancytracker.ui.screens.NameAndLabelScreen
import app.restora.pregnancytracker.ui.screens.PauseScreen
import app.restora.pregnancytracker.ui.screens.PlanScreen
import app.restora.pregnancytracker.ui.screens.PremiumScreen
import app.restora.pregnancytracker.ui.screens.TodayScreen
import app.restora.pregnancytracker.ui.screens.ToolsScreen
import app.restora.pregnancytracker.ui.screens.TogetherScreen
import app.restora.pregnancytracker.ui.screens.WeekStoryScreen
import app.restora.pregnancytracker.ui.theme.BloomSeason
import app.restora.pregnancytracker.ui.theme.BloomTheme
import app.restora.pregnancytracker.ui.theme.BloomThemeMode
import kotlinx.coroutines.delay
import java.util.Locale

private const val PREFERENCES_NAME = "bloom_local_preferences"

private enum class BloomScreen {
    LANGUAGE,
    DUE_DATE,
    NAME_AND_LABEL,
    TODAY,
    CHECK_IN_DONE,
    WEEK_STORY,
    TOOLS,
    KICK_COUNTER,
    KICK_SUMMARY,
    CONTRACTION_TIMER,
    PLAN,
    JOURNAL,
    TOGETHER,
    ME,
    PREMIUM,
    PAUSE_OR_END,
    LOSS_CONFIRMATION,
    BABY_ARRIVED
}

@Composable
fun BloomApp() {
    val context = LocalContext.current
    val preferences = remember(context) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    var language by rememberSaveable {
        mutableStateOf(preferences.getString("language", "en") ?: "en")
    }
    var dueDateMillis by rememberSaveable {
        mutableLongStateOf(preferences.getLong("due_date", 0L))
    }
    var nickname by rememberSaveable {
        mutableStateOf(preferences.getString("nickname", "") ?: "")
    }
    var userLabel by rememberSaveable {
        mutableStateOf(preferences.getString("user_label", "No label") ?: "No label")
    }
    var partnerLabel by rememberSaveable {
        mutableStateOf(preferences.getString("partner_label", "Skip") ?: "Skip")
    }
    var mood by rememberSaveable {
        mutableStateOf(preferences.getString("today_mood", "") ?: "")
    }
    var journalNote by rememberSaveable {
        mutableStateOf(preferences.getString("journal_note", "") ?: "")
    }
    var appointment by rememberSaveable {
        mutableStateOf(preferences.getString("appointment", "") ?: "")
    }
    var question by rememberSaveable {
        mutableStateOf(preferences.getString("question", "") ?: "")
    }
    var checklistItem by rememberSaveable {
        mutableStateOf(preferences.getString("checklist_item", "") ?: "")
    }
    var trackingPaused by rememberSaveable {
        mutableStateOf(preferences.getBoolean("tracking_paused", false))
    }
    var pregnancyEnded by rememberSaveable {
        mutableStateOf(preferences.getBoolean("pregnancy_ended", false))
    }
    var pregnancyArchived by rememberSaveable {
        mutableStateOf(preferences.getBoolean("pregnancy_archived", false))
    }

    val initialScreen = if (preferences.getBoolean("onboarding_complete", false)) {
        BloomScreen.TODAY.name
    } else {
        BloomScreen.LANGUAGE.name
    }
    var screenName by rememberSaveable { mutableStateOf(initialScreen) }
    var previousScreenName by rememberSaveable { mutableStateOf(BloomScreen.TODAY.name) }
    val screen = runCatching { BloomScreen.valueOf(screenName) }.getOrDefault(BloomScreen.LANGUAGE)

    var kickCount by rememberSaveable { mutableLongStateOf(preferences.getLong("kick_count", 0L)) }
    var kickStartedAt by rememberSaveable { mutableLongStateOf(0L) }
    var kickElapsedSeconds by rememberSaveable { mutableLongStateOf(0L) }
    var kickDurationSeconds by rememberSaveable { mutableLongStateOf(preferences.getLong("kick_duration", 0L)) }
    var kickActive by rememberSaveable { mutableStateOf(false) }

    var contractionStartedAt by rememberSaveable { mutableLongStateOf(0L) }
    var contractionLastStoppedAt by rememberSaveable { mutableLongStateOf(0L) }
    var contractionDurations by rememberSaveable { mutableStateOf(preferences.getString("contraction_durations", "") ?: "") }
    var contractionElapsedSeconds by rememberSaveable { mutableLongStateOf(0L) }
    var contractionLastGapSeconds by rememberSaveable { mutableLongStateOf(preferences.getLong("contraction_last_gap", 0L)) }
    var contractionActive by rememberSaveable { mutableStateOf(false) }

    fun navigate(destination: BloomScreen) {
        if (screenName != destination.name) {
            previousScreenName = screenName
            screenName = destination.name
        }
    }

    fun stopContraction() {
        if (contractionActive) {
            val now = SystemClock.elapsedRealtime()
            contractionElapsedSeconds = ((now - contractionStartedAt) / 1_000L).coerceAtLeast(0L)
            contractionActive = false
            contractionLastStoppedAt = now
            contractionDurations = listOf(contractionDurations, contractionElapsedSeconds.toString())
                .filter { it.isNotBlank() }
                .joinToString(",")
            preferences.edit()
                .putLong("contraction_last_gap", contractionLastGapSeconds)
                .putString("contraction_durations", contractionDurations)
                .apply()
        }
    }

    fun startKickSession() {
        kickCount = 0L
        kickElapsedSeconds = 0L
        kickDurationSeconds = 0L
        kickStartedAt = SystemClock.elapsedRealtime()
        kickActive = true
        preferences.edit().putLong("kick_count", 0L).putLong("kick_duration", 0L).apply()
        navigate(BloomScreen.KICK_COUNTER)
    }

    fun startContraction() {
        val now = SystemClock.elapsedRealtime()
        if (contractionLastStoppedAt > 0L) {
            contractionLastGapSeconds = ((now - contractionLastStoppedAt) / 1_000L).coerceAtLeast(0L)
        }
        contractionStartedAt = now
        contractionElapsedSeconds = 0L
        contractionActive = true
    }

    fun selectLanguage(value: String) {
        language = value
        preferences.edit().putString("language", value).apply()
    }

    fun saveDueDate(value: Long) {
        dueDateMillis = value
        preferences.edit().putLong("due_date", value).apply()
    }

    fun completeOnboarding() {
        preferences.edit()
            .putString("nickname", nickname)
            .putString("user_label", userLabel)
            .putString("partner_label", partnerLabel)
            .putBoolean("onboarding_complete", true)
            .apply()
        navigate(BloomScreen.TODAY)
    }

    fun deleteEverything() {
        preferences.edit().clear().apply()
        language = "en"
        dueDateMillis = 0L
        nickname = ""
        userLabel = "No label"
        partnerLabel = "Skip"
        mood = ""
        journalNote = ""
        appointment = ""
        question = ""
        checklistItem = ""
        trackingPaused = false
        pregnancyEnded = false
        pregnancyArchived = false
        kickCount = 0L
        kickStartedAt = 0L
        kickElapsedSeconds = 0L
        kickDurationSeconds = 0L
        kickActive = false
        contractionStartedAt = 0L
        contractionLastStoppedAt = 0L
        contractionDurations = ""
        contractionElapsedSeconds = 0L
        contractionLastGapSeconds = 0L
        contractionActive = false
        navigate(BloomScreen.LANGUAGE)
    }

    val exportText = remember(
        language,
        dueDateMillis,
        nickname,
        userLabel,
        partnerLabel,
        mood,
        journalNote,
        appointment,
        question,
        checklistItem,
        trackingPaused,
        pregnancyEnded,
        pregnancyArchived,
        kickCount,
        kickDurationSeconds,
        contractionDurations
    ) {
        buildString {
            appendLine("Bloom — on-device data export")
            appendLine("Language: " + language)
            appendLine("Due date: " + if (dueDateMillis > 0L) formatDate(dueDateMillis, language) else "Not set")
            appendLine("Nickname: " + nickname)
            appendLine("Label: " + userLabel)
            appendLine("Partner label: " + partnerLabel)
            appendLine("Today's check-in: " + mood)
            appendLine("Journal note: " + journalNote)
            appendLine("Appointment: " + appointment)
            appendLine("Question: " + question)
            appendLine("Checklist item: " + checklistItem)
            appendLine("Tracking paused: " + trackingPaused)
            appendLine("Pregnancy ended: " + pregnancyEnded)
            appendLine("Pregnancy archived: " + pregnancyArchived)
            appendLine("Latest kick count: " + kickCount)
            appendLine("Latest kick session duration: " + formatDuration(kickDurationSeconds))
            appendLine("Recorded contraction durations (seconds): " + contractionDurations)
        }
    }

    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        if (uri != null) {
            runCatching {
                context.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { writer ->
                    writer.write(exportText)
                }
            }
        }
    }

    LaunchedEffect(screenName, kickActive, kickStartedAt) {
        if (screenName == BloomScreen.KICK_COUNTER.name && kickActive && kickStartedAt > 0L) {
            while (true) {
                kickElapsedSeconds = ((SystemClock.elapsedRealtime() - kickStartedAt) / 1_000L)
                    .coerceAtLeast(0L)
                delay(1_000L)
            }
        }
    }

    LaunchedEffect(screenName, contractionActive, contractionStartedAt) {
        if (screenName == BloomScreen.CONTRACTION_TIMER.name && contractionActive && contractionStartedAt > 0L) {
            while (true) {
                contractionElapsedSeconds = ((SystemClock.elapsedRealtime() - contractionStartedAt) / 1_000L)
                    .coerceAtLeast(0L)
                delay(250L)
            }
        }
    }

    BackHandler(enabled = screen != BloomScreen.LANGUAGE && screen != BloomScreen.TODAY) {
        when (screen) {
            BloomScreen.KICK_COUNTER -> {
                kickActive = false
                kickDurationSeconds = kickElapsedSeconds
                preferences.edit()
                    .putLong("kick_count", kickCount)
                    .putLong("kick_duration", kickDurationSeconds)
                    .apply()
                navigate(BloomScreen.TODAY)
            }
            BloomScreen.CONTRACTION_TIMER -> {
                stopContraction()
                navigate(BloomScreen.TOOLS)
            }
            else -> {
                val previous = runCatching { BloomScreen.valueOf(previousScreenName) }
                    .getOrDefault(BloomScreen.TODAY)
                screenName = previous.name
            }
        }
    }

    val themeMode = when {
        screen == BloomScreen.CONTRACTION_TIMER -> BloomThemeMode.NIGHT
        screen == BloomScreen.PAUSE_OR_END || screen == BloomScreen.LOSS_CONFIRMATION -> BloomThemeMode.QUIET
        screen == BloomScreen.TODAY && (pregnancyEnded || pregnancyArchived) -> BloomThemeMode.QUIET
        else -> BloomThemeMode.DAWN
    }

    BloomLocaleProvider(language) {
        // Apricot is the Dawn visual fallback; trimester mapping waits for an approved date rule.
        BloomTheme(mode = themeMode, season = BloomSeason.APRICOT) {
            val bottomBarVisible = screen in setOf(
                BloomScreen.TODAY,
                BloomScreen.CHECK_IN_DONE,
                BloomScreen.TOOLS,
                BloomScreen.PLAN,
                BloomScreen.JOURNAL,
                BloomScreen.TOGETHER,
                BloomScreen.ME
            )
            val selectedTab = when (screen) {
                BloomScreen.JOURNAL -> BloomTab.JOURNAL
                BloomScreen.TOOLS -> BloomTab.TOOLS
                BloomScreen.PLAN -> BloomTab.PLAN
                else -> BloomTab.TODAY
            }

            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    if (bottomBarVisible) {
                        BloomBottomBar(
                            selected = selectedTab,
                            labels = listOf(
                                stringResource(R.string.nav_today),
                                stringResource(R.string.nav_journal),
                                stringResource(R.string.nav_tools),
                                stringResource(R.string.nav_plan)
                            ),
                            onSelect = { tab ->
                                when (tab) {
                                    BloomTab.TODAY -> navigate(BloomScreen.TODAY)
                                    BloomTab.JOURNAL -> navigate(BloomScreen.JOURNAL)
                                    BloomTab.TOOLS -> navigate(BloomScreen.TOOLS)
                                    BloomTab.PLAN -> navigate(BloomScreen.PLAN)
                                }
                            }
                        )
                    }
                }
            ) { innerPadding: PaddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (screen) {
                        BloomScreen.LANGUAGE -> LanguageScreen(
                            selectedLanguage = language,
                            onSelectLanguage = ::selectLanguage,
                            onContinue = { navigate(BloomScreen.DUE_DATE) }
                        )
                        BloomScreen.DUE_DATE -> DueDateScreen(
                            dueDateMillis = dueDateMillis,
                            language = language,
                            onDateSelected = ::saveDueDate,
                            onContinue = { navigate(BloomScreen.NAME_AND_LABEL) }
                        )
                        BloomScreen.NAME_AND_LABEL -> NameAndLabelScreen(
                            nickname = nickname,
                            userLabel = userLabel,
                            partnerLabel = partnerLabel,
                            onNicknameChange = { nickname = it },
                            onUserLabelChange = { userLabel = it },
                            onPartnerLabelChange = { partnerLabel = it },
                            onContinue = ::completeOnboarding
                        )
                        BloomScreen.TODAY -> TodayScreen(
                            dueDateMillis = dueDateMillis,
                            language = language,
                            nickname = nickname,
                            mood = mood,
                            trackingPaused = trackingPaused,
                            pregnancyEnded = pregnancyEnded,
                            pregnancyArchived = pregnancyArchived,
                            onProfile = { navigate(BloomScreen.ME) },
                            onMoodSelected = {
                                mood = it
                                preferences.edit().putString("today_mood", it).apply()
                                navigate(BloomScreen.CHECK_IN_DONE)
                            },
                            onKicks = ::startKickSession,
                            onContractions = { navigate(BloomScreen.CONTRACTION_TIMER) },
                            onNote = { navigate(BloomScreen.JOURNAL) },
                            onWeekStory = { navigate(BloomScreen.WEEK_STORY) },
                            onResumeTracking = {
                                trackingPaused = false
                                preferences.edit().putBoolean("tracking_paused", false).apply()
                            }
                        )
                        BloomScreen.CHECK_IN_DONE -> CheckInDoneScreen(
                            mood = mood,
                            onDone = { navigate(BloomScreen.TODAY) }
                        )
                        BloomScreen.WEEK_STORY -> WeekStoryScreen(
                            onAddQuestion = { navigate(BloomScreen.PLAN) },
                            onNext = { navigate(BloomScreen.TODAY) }
                        )
                        BloomScreen.TOOLS -> ToolsScreen(
                            onKickCounter = ::startKickSession,
                            onContractions = { navigate(BloomScreen.CONTRACTION_TIMER) },
                            onQuestions = { navigate(BloomScreen.PLAN) },
                            onHospitalBag = { navigate(BloomScreen.PLAN) }
                        )
                        BloomScreen.KICK_COUNTER -> KickCounterScreen(
                            count = kickCount,
                            elapsedSeconds = kickElapsedSeconds,
                            onTap = {
                                kickCount += 1L
                                preferences.edit().putLong("kick_count", kickCount).apply()
                            },
                            onEnd = {
                                kickDurationSeconds = kickElapsedSeconds
                                kickActive = false
                                preferences.edit().putLong("kick_duration", kickDurationSeconds).apply()
                                navigate(BloomScreen.KICK_SUMMARY)
                            }
                        )
                        BloomScreen.KICK_SUMMARY -> KickSummaryScreen(
                            count = kickCount,
                            durationSeconds = kickDurationSeconds,
                            onShare = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        context.getString(
                                            R.string.kick_share_text,
                                            kickCount,
                                            formatDuration(kickDurationSeconds)
                                        )
                                    )
                                }
                                context.startActivity(Intent.createChooser(shareIntent, null))
                            },
                            onDone = { navigate(BloomScreen.TODAY) }
                        )
                        BloomScreen.CONTRACTION_TIMER -> ContractionTimerScreen(
                            elapsedSeconds = contractionElapsedSeconds,
                            lastGapSeconds = contractionLastGapSeconds,
                            isRunning = contractionActive,
                            onStart = ::startContraction,
                            durations = contractionDurations.split(",").mapNotNull { it.toLongOrNull() },
                            onStop = ::stopContraction,
                            onBack = {
                                stopContraction()
                                navigate(BloomScreen.TOOLS)
                            }
                        )
                        BloomScreen.PLAN -> PlanScreen(
                            appointment = appointment,
                            question = question,
                            checklistItem = checklistItem,
                            onAppointmentChange = {
                                appointment = it
                                preferences.edit().putString("appointment", it).apply()
                            },
                            onQuestionChange = {
                                question = it
                                preferences.edit().putString("question", it).apply()
                            },
                            onChecklistChange = {
                                checklistItem = it
                                preferences.edit().putString("checklist_item", it).apply()
                            }
                        )
                        BloomScreen.JOURNAL -> JournalScreen(
                            mood = mood,
                            note = journalNote,
                            onSaveNote = {
                                journalNote = it
                                preferences.edit().putString("journal_note", it).apply()
                            }
                        )
                        BloomScreen.TOGETHER -> TogetherScreen(
                            dueDateMillis = dueDateMillis,
                            language = language
                        )
                        BloomScreen.ME -> MeScreen(
                            userLabel = userLabel,
                            partnerLabel = partnerLabel,
                            language = language,
                            onLanguageChange = ::selectLanguage,
                            onExport = { exportLauncher.launch("bloom-data.txt") },
                            onPauseOrEnd = { navigate(BloomScreen.PAUSE_OR_END) },
                            onPremium = { navigate(BloomScreen.PREMIUM) },
                            onTogether = { navigate(BloomScreen.TOGETHER) },
                            onBabyArrived = { navigate(BloomScreen.BABY_ARRIVED) },
                            onDeleteEverything = ::deleteEverything
                        )
                        BloomScreen.PREMIUM -> PremiumScreen(
                            onNotNow = { navigate(BloomScreen.ME) }
                        )
                        BloomScreen.PAUSE_OR_END -> PauseScreen(
                            onPause = {
                                trackingPaused = true
                                preferences.edit().putBoolean("tracking_paused", true).apply()
                                navigate(BloomScreen.TODAY)
                            },
                            onLoss = {
                                trackingPaused = true
                                pregnancyEnded = true
                                preferences.edit()
                                    .putBoolean("tracking_paused", true)
                                    .putBoolean("pregnancy_ended", true)
                                    .apply()
                                navigate(BloomScreen.LOSS_CONFIRMATION)
                            },
                            onChanged = {
                                trackingPaused = true
                                preferences.edit().putBoolean("tracking_paused", true).apply()
                                navigate(BloomScreen.TODAY)
                            },
                            onDelete = ::deleteEverything
                        )
                        BloomScreen.LOSS_CONFIRMATION -> LossConfirmationScreen(
                            onKeepNotes = { navigate(BloomScreen.JOURNAL) },
                            onDelete = ::deleteEverything
                        )
                        BloomScreen.BABY_ARRIVED -> BabyArrivedScreen(
                            onArchive = {
                                pregnancyArchived = true
                                preferences.edit().putBoolean("pregnancy_archived", true).apply()
                                navigate(BloomScreen.TODAY)
                            },
                            onSkip = { navigate(BloomScreen.TODAY) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BloomLocaleProvider(
    language: String,
    content: @Composable () -> Unit
) {
    val baseContext = LocalContext.current
    val baseConfiguration = LocalConfiguration.current
    val locale = remember(language) { Locale.forLanguageTag(language) }
    val configuration = remember(baseConfiguration, locale) {
        Configuration(baseConfiguration).apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }
    }
    val localizedContext = remember(baseContext, configuration) {
        baseContext.createConfigurationContext(configuration)
    }
    CompositionLocalProvider(
        LocalContext provides localizedContext,
        LocalConfiguration provides configuration,
        LocalLayoutDirection provides if (language == "ur") LayoutDirection.Rtl else LayoutDirection.Ltr,
        content = content
    )
}

private fun formatDate(millis: Long, language: String): String {
    val locale = Locale.forLanguageTag(language)
    return java.text.SimpleDateFormat("d MMMM yyyy", locale).format(java.util.Date(millis))
}

private fun formatDuration(totalSeconds: Long): String {
    val minutes = totalSeconds / 60L
    val seconds = totalSeconds % 60L
    return String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
}
