package app.restora.pregnancytracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.restora.pregnancytracker.R
import app.restora.pregnancytracker.ui.components.BloomButton
import app.restora.pregnancytracker.ui.components.BloomCard
import app.restora.pregnancytracker.ui.components.BloomChip
import app.restora.pregnancytracker.ui.components.BloomEmptyState
import app.restora.pregnancytracker.ui.components.BloomIcon
import app.restora.pregnancytracker.ui.components.BloomIconType
import app.restora.pregnancytracker.ui.components.BloomInput
import app.restora.pregnancytracker.ui.components.BloomMoodOption
import app.restora.pregnancytracker.ui.components.BloomOrb
import app.restora.pregnancytracker.ui.components.BloomPage
import app.restora.pregnancytracker.ui.components.BloomPageTitle
import app.restora.pregnancytracker.ui.components.BloomSecondaryButton
import app.restora.pregnancytracker.ui.components.BloomSectionLabel
import app.restora.pregnancytracker.ui.components.BloomSettingRow
import app.restora.pregnancytracker.ui.components.BloomTextButton
import app.restora.pregnancytracker.ui.theme.LocalBloomPalette
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TodayScreen(
    dueDateMillis: Long,
    language: String,
    nickname: String,
    mood: String,
    trackingPaused: Boolean,
    pregnancyEnded: Boolean,
    pregnancyArchived: Boolean,
    onProfile: () -> Unit,
    onMoodSelected: (String) -> Unit,
    onKicks: () -> Unit,
    onContractions: () -> Unit,
    onNote: () -> Unit,
    onWeekStory: () -> Unit,
    onResumeTracking: () -> Unit
) {
    val palette = LocalBloomPalette.current
    val dueDate = if (dueDateMillis > 0L) Date(dueDateMillis) else null
    val dayValue = dueDate?.let { SimpleDateFormat("d", Locale.forLanguageTag(language)).format(it) } ?: "—"
    val dateLabel = dueDate?.let {
        SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag(language)).format(it)
    } ?: stringResource(R.string.due_date_label)

    BloomPage {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                BloomSectionLabel(stringResource(R.string.today_eyebrow))
                Text(
                    text = if (nickname.isBlank()) stringResource(R.string.good_morning) else stringResource(R.string.good_morning_named, nickname),
                    color = palette.ink,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(onClick = onProfile),
                shape = CircleShape,
                color = palette.surface,
                shadowElevation = 3.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    BloomIcon(BloomIconType.PROFILE, palette.primary)
                }
            }
        }

        BloomOrb(
            value = dayValue,
            label = stringResource(R.string.due_date_short) + " · " + dateLabel,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 260.dp,
            height = 234.dp,
            valueSize = 83
        )

        if (pregnancyEnded || pregnancyArchived) {
            BloomCard {
                Text(
                    text = if (pregnancyEnded) stringResource(R.string.tracking_ended_title) else stringResource(R.string.pregnancy_archived_title),
                    color = palette.ink,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.data_remains_on_device),
                    color = palette.muted,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else if (trackingPaused) {
            BloomCard {
                Text(
                    text = stringResource(R.string.tracking_paused_title),
                    color = palette.ink,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = stringResource(R.string.tracking_paused_body),
                    color = palette.muted,
                    style = MaterialTheme.typography.bodyMedium
                )
                BloomTextButton(
                    text = stringResource(R.string.resume_tracking),
                    onClick = onResumeTracking,
                    color = palette.primary
                )
            }
        }

        if (!pregnancyEnded && !pregnancyArchived) {
            BloomCard {
                BloomSectionLabel(stringResource(R.string.today_label))
                Text(
                    text = stringResource(R.string.how_are_you_today),
                    color = palette.ink,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    BloomMoodOption(
                        label = stringResource(R.string.mood_calm),
                        selected = mood == "Calm",
                        index = 0,
                        onClick = { onMoodSelected("Calm") },
                        modifier = Modifier.weight(1f)
                    )
                    BloomMoodOption(
                        label = stringResource(R.string.mood_okay),
                        selected = mood == "Okay",
                        index = 1,
                        onClick = { onMoodSelected("Okay") },
                        modifier = Modifier.weight(1f)
                    )
                    BloomMoodOption(
                        label = stringResource(R.string.mood_tired),
                        selected = mood == "Tired",
                        index = 2,
                        onClick = { onMoodSelected("Tired") },
                        modifier = Modifier.weight(1f)
                    )
                    BloomMoodOption(
                        label = stringResource(R.string.mood_queasy),
                        selected = mood == "Queasy",
                        index = 3,
                        onClick = { onMoodSelected("Queasy") },
                        modifier = Modifier.weight(1f)
                    )
                    BloomMoodOption(
                        label = stringResource(R.string.mood_low),
                        selected = mood == "Low",
                        index = 4,
                        onClick = { onMoodSelected("Low") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                QuickAction(
                    text = stringResource(R.string.kicks),
                    modifier = Modifier.weight(1f),
                    onClick = onKicks
                )
                QuickAction(
                    text = stringResource(R.string.contractions),
                    modifier = Modifier.weight(1f),
                    onClick = onContractions
                )
                QuickAction(
                    text = stringResource(R.string.note),
                    modifier = Modifier.weight(1f),
                    onClick = onNote
                )
            }

            BloomTextButton(
                text = stringResource(R.string.open_week_story),
                onClick = onWeekStory,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = palette.deep
            )
        }
}

@Composable
private fun QuickAction(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = CircleShape,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = palette.primary,
            contentColor = palette.onPrimary
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp), maxLines = 1)
    }
}

@Composable
fun CheckInDoneScreen(
    mood: String,
    onDone: () -> Unit
) {
    val palette = LocalBloomPalette.current
    BloomPage {
        BloomPageTitle(stringResource(R.string.checkin_thanks))
        BloomOrb(
            value = stringResource(R.string.checkin_saved_value),
            label = stringResource(R.string.today_label),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 200.dp,
            height = 180.dp,
            valueSize = 58
        )
        BloomCard(padding = 20.dp) {
            BloomSectionLabel(stringResource(R.string.checkin_logged))
            Text(
                text = if (mood.isBlank()) stringResource(R.string.checkin_saved) else moodLabel(mood),
                color = palette.ink,
                style = MaterialTheme.typography.titleLarge
            )
        }
        BloomCard(padding = 18.dp) {
            Text(
                text = stringResource(R.string.nothing_logged_yesterday),
                color = palette.muted,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(8.dp))
        BloomButton(
            text = stringResource(R.string.done),
            onClick = onDone
        )
    }
}

@Composable
fun WeekStoryScreen(
    onAddQuestion: () -> Unit,
    onNext: () -> Unit
) {
    BloomPage {
        BloomPageTitle(stringResource(R.string.week_story_title), size = 34)
        BloomOrb(
            value = stringResource(R.string.week_story_mark),
            label = stringResource(R.string.week_story_label),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 210.dp,
            height = 189.dp,
            valueSize = 48
        )
        BloomCard(padding = 20.dp) {
            Text(
                text = stringResource(R.string.week_story_content_pending),
                color = LocalBloomPalette.current.muted,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(8.dp))
        BloomSecondaryButton(
            text = stringResource(R.string.add_to_questions),
            onClick = onAddQuestion
        )
        BloomButton(
            text = stringResource(R.string.next),
            onClick = onNext
        )
    }
}

@Composable
fun ToolsScreen(
    onKickCounter: () -> Unit,
    onContractions: () -> Unit,
    onQuestions: () -> Unit,
    onHospitalBag: () -> Unit
) {
    BloomPage {
        BloomPageTitle(stringResource(R.string.tools_title))
        ToolCard(
            title = stringResource(R.string.kick_counter),
            subtitle = stringResource(R.string.kick_counter_subtitle),
            onClick = onKickCounter
        )
        ToolCard(
            title = stringResource(R.string.contractions),
            subtitle = stringResource(R.string.contractions_subtitle),
            onClick = onContractions
        )
        ToolCard(
            title = stringResource(R.string.questions),
            subtitle = stringResource(R.string.questions_subtitle),
            onClick = onQuestions
        )
        ToolCard(
            title = stringResource(R.string.hospital_bag),
            subtitle = stringResource(R.string.hospital_bag_subtitle),
            onClick = onHospitalBag
        )
    }
}

@Composable
private fun ToolCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    val palette = LocalBloomPalette.current
    BloomCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 2.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(title, color = palette.ink, style = MaterialTheme.typography.titleLarge)
            Text(subtitle, color = palette.muted, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun PlanScreen(
    appointment: String,
    question: String,
    checklistItem: String,
    onAppointmentChange: (String) -> Unit,
    onQuestionChange: (String) -> Unit,
    onChecklistChange: (String) -> Unit
) {
    var editor by rememberSaveable { mutableStateOf("") }
    var draft by rememberSaveable { mutableStateOf("") }
    var selectedDay by rememberSaveable { mutableStateOf(0) }
    var checklistDone by rememberSaveable { mutableStateOf(false) }
    val palette = LocalBloomPalette.current
    val days = remember {
        (0..6).map { offset ->
            Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, offset) }
        }
    }
    val locale = LocalContext.current.resources.configuration.locales[0] ?: Locale.getDefault()

    BloomPage {
        BloomPageTitle(stringResource(R.string.plan_title))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            days.forEachIndexed { index, calendar ->
                val selected = selectedDay == index
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedDay = index },
                    shape = RoundedCornerShape(18.dp),
                    color = if (selected) palette.primary else palette.surface
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            SimpleDateFormat("EEE", locale).format(calendar.time),
                            color = if (selected) palette.onPrimary else palette.muted,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            calendar.get(Calendar.DAY_OF_MONTH).toString(),
                            color = if (selected) palette.onPrimary else palette.ink,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

        BloomCard(padding = 20.dp) {
            BloomSectionLabel(stringResource(R.string.appointments))
            if (appointment.isBlank()) {
                BloomEmptyState(stringResource(R.string.no_appointments))
            } else {
                Text(appointment, color = palette.ink, style = MaterialTheme.typography.titleLarge)
            }
            BloomSecondaryButton(
                text = stringResource(R.string.add_appointment),
                onClick = {
                    draft = appointment
                    editor = "appointment"
                }
            )
        }

        BloomCard {
            BloomSectionLabel(stringResource(R.string.questions_to_ask))
            if (question.isBlank()) {
                BloomEmptyState(stringResource(R.string.no_questions))
            } else {
                Text(question, color = palette.ink, style = MaterialTheme.typography.bodyLarge)
            }
            BloomSecondaryButton(
                text = stringResource(R.string.add_question),
                onClick = {
                    draft = question
                    editor = "question"
                }
            )
        }

        BloomCard {
            BloomSectionLabel(stringResource(R.string.checklist))
            if (checklistItem.isBlank()) {
                BloomEmptyState(stringResource(R.string.no_checklist_items))
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checklistDone,
                        onCheckedChange = { checklistDone = it }
                    )
                    Text(
                        text = checklistItem,
                        color = if (checklistDone) palette.muted else palette.ink,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            BloomSecondaryButton(
                text = stringResource(R.string.add_checklist_item),
                onClick = {
                    draft = checklistItem
                    editor = "checklist"
                }
            )
        }
    }

    if (editor.isNotEmpty()) {
        val title = when (editor) {
            "appointment" -> stringResource(R.string.add_appointment)
            "question" -> stringResource(R.string.add_question)
            else -> stringResource(R.string.add_checklist_item)
        }
        BloomTextDialog(
            title = title,
            value = draft,
            placeholder = stringResource(R.string.write_something),
            onValueChange = { draft = it },
            onDismiss = { editor = "" },
            onSave = {
                when (editor) {
                    "appointment" -> onAppointmentChange(it)
                    "question" -> onQuestionChange(it)
                    else -> {
                        checklistDone = false
                        onChecklistChange(it)
                    }
                }
                editor = ""
            }
        )
    }
}

@Composable
fun JournalScreen(
    mood: String,
    note: String,
    onSaveNote: (String) -> Unit
) {
    var showEditor by rememberSaveable { mutableStateOf(false) }
    var draft by rememberSaveable { mutableStateOf(note) }
    val palette = LocalBloomPalette.current

    BloomPage {
        BloomPageTitle(stringResource(R.string.journal_title))
        if (mood.isNotBlank()) {
            BloomCard {
                BloomSectionLabel(stringResource(R.string.today_label))
                Text(
                    text = moodLabel(mood),
                    color = palette.ink,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
        if (note.isNotBlank()) {
            BloomCard {
                BloomSectionLabel(stringResource(R.string.your_note))
                Text(note, color = palette.ink, style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            BloomEmptyState(stringResource(R.string.nothing_logged_yesterday))
        }
        BloomButton(
            text = stringResource(R.string.new_note),
            onClick = {
                draft = note
                showEditor = true
            }
        )
    }

    if (showEditor) {
        BloomTextDialog(
            title = stringResource(R.string.new_note),
            value = draft,
            placeholder = stringResource(R.string.write_note_placeholder),
            onValueChange = { draft = it },
            onDismiss = { showEditor = false },
            onSave = {
                onSaveNote(it)
                showEditor = false
            },
            multiline = true
        )
    }
}

@Composable
fun TogetherScreen(
    dueDateMillis: Long,
    language: String
) {
    var showSharingInfo by rememberSaveable { mutableStateOf(false) }
    val palette = LocalBloomPalette.current
    val dateLabel = if (dueDateMillis > 0L) {
        SimpleDateFormat("d MMMM yyyy", Locale.forLanguageTag(language)).format(Date(dueDateMillis))
    } else {
        stringResource(R.string.due_date_not_set)
    }

    BloomPage {
        BloomPageTitle(stringResource(R.string.together_title))
        BloomOrb(
            value = stringResource(R.string.together_mark),
            label = dateLabel,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 210.dp,
            height = 189.dp,
            valueSize = 67
        )
        BloomCard(padding = 20.dp) {
            BloomSectionLabel(stringResource(R.string.today_you_could))
            Text(
                text = stringResource(R.string.together_gentle_copy),
                color = palette.ink,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        BloomCard {
            BloomSectionLabel(stringResource(R.string.shared_with_partner))
            BloomEmptyState(stringResource(R.string.no_shared_items))
            Text(
                text = stringResource(R.string.mood_checkins_private),
                color = palette.muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        BloomSecondaryButton(
            text = stringResource(R.string.manage_sharing),
            onClick = { showSharingInfo = true }
        )
    }

    if (showSharingInfo) {
        BloomInfoDialog(
            title = stringResource(R.string.sharing_not_ready_title),
            message = stringResource(R.string.sharing_not_ready_body),
            onDismiss = { showSharingInfo = false }
        )
    }
}

@Composable
fun MeScreen(
    userLabel: String,
    partnerLabel: String,
    language: String,
    quietMode: Boolean,
    onLanguageChange: (String) -> Unit,
    onExport: () -> Unit,
    onPauseOrEnd: () -> Unit,
    onPremium: () -> Unit,
    onTogether: () -> Unit,
    onBabyArrived: () -> Unit,
    onDeleteEverything: () -> Unit
) {
    var showLanguageDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val palette = LocalBloomPalette.current
    val userLabelText = when (userLabel) {
        "Mum" -> stringResource(R.string.label_mum)
        "Mama" -> stringResource(R.string.label_mama)
        "Parent" -> stringResource(R.string.label_parent)
        else -> ""
    }
    val partnerLabelText = when (partnerLabel) {
        "Partner" -> stringResource(R.string.label_partner)
        "Husband" -> stringResource(R.string.label_husband)
        "Wife" -> stringResource(R.string.label_wife)
        else -> ""
    }
    val labels = listOf(userLabelText, partnerLabelText).filter { it.isNotBlank() }
    val labelsText = if (labels.isEmpty()) stringResource(R.string.label_none) else labels.joinToString(", ")

    BloomPage {
        BloomPageTitle(stringResource(R.string.me_title))
        BloomCard(padding = 20.dp) {
            Text(
                text = stringResource(R.string.your_privacy),
                color = palette.deep,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(R.string.privacy_promise),
                color = palette.ink,
                style = MaterialTheme.typography.bodyLarge
            )
            BloomSettingRow(
                label = stringResource(R.string.app_lock),
                value = stringResource(R.string.not_configured)
            )
            BloomSettingRow(
                label = stringResource(R.string.hide_notification_text),
                value = stringResource(R.string.no_reminders_scheduled)
            )
            BloomSettingRow(
                label = stringResource(R.string.discreet_mode),
                value = stringResource(R.string.not_active)
            )
            BloomSettingRow(
                label = stringResource(R.string.export_my_data),
                onClick = onExport
            )
            BloomSettingRow(
                label = stringResource(R.string.delete_everything),
                onClick = { showDeleteDialog = true }
            )
        }

        BloomCard {
            BloomSettingRow(
                label = stringResource(R.string.language),
                value = if (language == "ur") stringResource(R.string.language_urdu) else stringResource(R.string.language_english),
                onClick = { showLanguageDialog = true }
            )
            BloomSettingRow(
                label = stringResource(R.string.labels),
                value = labelsText
            )
            if (!quietMode) {
                BloomSettingRow(
                    label = stringResource(R.string.pause_or_end_tracking),
                    onClick = onPauseOrEnd
                )
                BloomSettingRow(
                    label = stringResource(R.string.together_title),
                    onClick = onTogether
                )
                BloomSettingRow(
                    label = stringResource(R.string.baby_arrived),
                    onClick = onBabyArrived
                )
            }
        }

        if (!quietMode) {
            BloomCard {
                BloomSectionLabel(stringResource(R.string.bloom_plus))
                Text(
                    text = stringResource(R.string.premium_short_description),
                    color = palette.ink,
                    style = MaterialTheme.typography.bodyLarge
                )
                BloomSecondaryButton(
                    text = stringResource(R.string.learn_about_bloom_plus),
                    onClick = onPremium
                )
            }
        }
    }

    if (showLanguageDialog) {
        BloomChoiceDialog(
            title = stringResource(R.string.language),
            firstLabel = stringResource(R.string.language_english),
            secondLabel = stringResource(R.string.language_urdu),
            selectedFirst = language == "en",
            onFirst = {
                onLanguageChange("en")
                showLanguageDialog = false
            },
            onSecond = {
                onLanguageChange("ur")
                showLanguageDialog = false
            },
            onDismiss = { showLanguageDialog = false }
        )
    }

    if (showDeleteDialog) {
        BloomConfirmDialog(
            title = stringResource(R.string.delete_everything_title),
            message = stringResource(R.string.delete_everything_body),
            confirmLabel = stringResource(R.string.delete_everything),
            onDismiss = { showDeleteDialog = false },
            onConfirm = onDeleteEverything
        )
    }
}

@Composable
private fun BloomTextDialog(
    title: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    multiline: Boolean = false
) {
    val palette = LocalBloomPalette.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = palette.surface,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(title, color = palette.ink, style = MaterialTheme.typography.headlineMedium)
                BloomInput(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = placeholder,
                    singleLine = !multiline,
                    minHeight = if (multiline) 132.dp else 60.dp
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    androidx.compose.material3.TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancel), color = palette.muted)
                    }
                    Button(
                        onClick = { onSave(value.trim()) },
                        enabled = value.isNotBlank(),
                        modifier = Modifier.weight(1f),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = palette.primary,
                            contentColor = palette.onPrimary
                        )
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }
            }
        }
    }
}

@Composable
private fun BloomInfoDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(28.dp), color = palette.surface) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(title, color = palette.ink, style = MaterialTheme.typography.headlineMedium)
                Text(message, color = palette.muted, style = MaterialTheme.typography.bodyLarge)
                BloomButton(stringResource(R.string.done), onDismiss)
            }
        }
    }
}

@Composable
private fun BloomChoiceDialog(
    title: String,
    firstLabel: String,
    secondLabel: String,
    selectedFirst: Boolean,
    onFirst: () -> Unit,
    onSecond: () -> Unit,
    onDismiss: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(28.dp), color = palette.surface) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(title, color = palette.ink, style = MaterialTheme.typography.headlineMedium)
                BloomChip(firstLabel, selectedFirst, onFirst)
                BloomChip(secondLabel, !selectedFirst, onSecond)
            }
        }
    }
}

@Composable
private fun BloomConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(shape = RoundedCornerShape(28.dp), color = palette.surface) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(title, color = palette.ink, style = MaterialTheme.typography.headlineMedium)
                Text(message, color = palette.muted, style = MaterialTheme.typography.bodyLarge)
                BloomSecondaryButton(stringResource(R.string.cancel), onDismiss)
                BloomButton(confirmLabel, onConfirm)
            }
        }
    }
}

@Composable
private fun moodLabel(value: String): String = when (value) {
    "Calm" -> stringResource(R.string.mood_calm)
    "Okay" -> stringResource(R.string.mood_okay)
    "Tired" -> stringResource(R.string.mood_tired)
    "Queasy" -> stringResource(R.string.mood_queasy)
    "Low" -> stringResource(R.string.mood_low)
    else -> value
}
