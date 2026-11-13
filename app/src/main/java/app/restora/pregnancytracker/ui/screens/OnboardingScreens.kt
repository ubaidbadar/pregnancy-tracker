package app.restora.pregnancytracker.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import app.restora.pregnancytracker.R
import app.restora.pregnancytracker.ui.components.BloomButton
import app.restora.pregnancytracker.ui.components.BloomCard
import app.restora.pregnancytracker.ui.components.BloomChip
import app.restora.pregnancytracker.ui.components.BloomInput
import app.restora.pregnancytracker.ui.components.BloomOrb
import app.restora.pregnancytracker.ui.components.BloomPage
import app.restora.pregnancytracker.ui.components.BloomPageTitle
import app.restora.pregnancytracker.ui.components.BloomProgressSegments
import app.restora.pregnancytracker.ui.components.BloomSectionLabel
import app.restora.pregnancytracker.ui.theme.LocalBloomPalette
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun LanguageScreen(
    selectedLanguage: String,
    onSelectLanguage: (String) -> Unit,
    onContinue: () -> Unit
) {
    BloomPage {
        BloomProgressSegments(activeSegments = 1)
        BloomPageTitle(stringResource(R.string.welcome_to_bloom), size = 34)
        BloomOrb(
            value = stringResource(R.string.welcome_hello),
            label = stringResource(R.string.welcome_tagline),
            width = 230.dp,
            height = 207.dp,
            valueSize = 73
        )

        LanguageChoice(
            title = stringResource(R.string.language_english),
            subtitle = stringResource(R.string.language_english_subtitle),
            selected = selectedLanguage == "en",
            onClick = { onSelectLanguage("en") }
        )
        LanguageChoice(
            title = stringResource(R.string.language_urdu),
            subtitle = stringResource(R.string.language_urdu_subtitle),
            selected = selectedLanguage == "ur",
            onClick = { onSelectLanguage("ur") }
        )

        Spacer(Modifier.height(8.dp))
        BloomButton(
            text = stringResource(R.string.continue_label),
            onClick = onContinue
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun LanguageChoice(
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = palette.surface,
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = if (selected) palette.primary else palette.raised
        ),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                color = palette.ink,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = subtitle,
                color = palette.muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DueDateScreen(
    dueDateMillis: Long,
    language: String,
    onDateSelected: (Long) -> Unit,
    onContinue: () -> Unit
) {
    val context = LocalContext.current
    val palette = LocalBloomPalette.current
    val selectedDate = if (dueDateMillis > 0L) Date(dueDateMillis) else null
    val dayValue = selectedDate?.let {
        SimpleDateFormat("d", Locale.forLanguageTag(language)).format(it)
    } ?: "—"
    val monthValue = selectedDate?.let {
        SimpleDateFormat("MMMM yyyy", Locale.forLanguageTag(language)).format(it)
    } ?: stringResource(R.string.choose_due_date)

    BloomPage {
        BloomProgressSegments(activeSegments = 2)
        BloomPageTitle(stringResource(R.string.due_date_title))
        BloomOrb(
            value = dayValue,
            label = monthValue,
            width = 290.dp,
            height = 261.dp,
            valueSize = 84
        )

        BloomSectionLabel(stringResource(R.string.due_date_label))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(role = Role.Button) {
                    val calendar = Calendar.getInstance().apply {
                        if (dueDateMillis > 0L) timeInMillis = dueDateMillis
                    }
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val chosen = Calendar.getInstance().apply {
                                set(year, month, day, 12, 0, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            onDateSelected(chosen.timeInMillis)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
            shape = RoundedCornerShape(22.dp),
            color = palette.surface,
            border = BorderStroke(2.dp, if (dueDateMillis > 0L) palette.primary else palette.raised),
            shadowElevation = 3.dp
        ) {
            Text(
                text = selectedDate?.let {
                    SimpleDateFormat("d MMMM yyyy", Locale.forLanguageTag(language)).format(it)
                } ?: stringResource(R.string.choose_due_date),
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                color = if (selectedDate != null) palette.ink else palette.muted,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Text(
            text = stringResource(R.string.privacy_promise),
            color = palette.muted,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        BloomButton(
            text = stringResource(R.string.continue_label),
            onClick = onContinue,
            enabled = dueDateMillis > 0L
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun NameAndLabelScreen(
    nickname: String,
    userLabel: String,
    partnerLabel: String,
    onNicknameChange: (String) -> Unit,
    onUserLabelChange: (String) -> Unit,
    onPartnerLabelChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    BloomPage {
        BloomProgressSegments(activeSegments = 3)
        BloomPageTitle(stringResource(R.string.name_label_title))
        BloomSectionLabel(stringResource(R.string.nickname_optional))
        BloomInput(
            value = nickname,
            onValueChange = onNicknameChange,
            placeholder = stringResource(R.string.nickname_placeholder)
        )

        BloomSectionLabel(stringResource(R.string.your_label))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BloomChip(
                text = stringResource(R.string.label_mum),
                selected = userLabel == "Mum",
                onClick = { onUserLabelChange("Mum") }
            )
            BloomChip(
                text = stringResource(R.string.label_mama),
                selected = userLabel == "Mama",
                onClick = { onUserLabelChange("Mama") }
            )
            BloomChip(
                text = stringResource(R.string.label_parent),
                selected = userLabel == "Parent",
                onClick = { onUserLabelChange("Parent") }
            )
            BloomChip(
                text = stringResource(R.string.label_none),
                selected = userLabel == "No label",
                onClick = { onUserLabelChange("No label") }
            )
        }

        BloomSectionLabel(stringResource(R.string.partner_label))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BloomChip(
                text = stringResource(R.string.label_partner),
                selected = partnerLabel == "Partner",
                onClick = { onPartnerLabelChange("Partner") }
            )
            BloomChip(
                text = stringResource(R.string.label_husband),
                selected = partnerLabel == "Husband",
                onClick = { onPartnerLabelChange("Husband") }
            )
            BloomChip(
                text = stringResource(R.string.label_wife),
                selected = partnerLabel == "Wife",
                onClick = { onPartnerLabelChange("Wife") }
            )
            BloomChip(
                text = stringResource(R.string.skip),
                selected = partnerLabel == "Skip",
                onClick = { onPartnerLabelChange("Skip") }
            )
        }

        Text(
            text = stringResource(R.string.privacy_promise),
            color = LocalBloomPalette.current.muted,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(8.dp))
        BloomButton(
            text = stringResource(R.string.go_to_today),
            onClick = onContinue
        )
        Spacer(Modifier.height(8.dp))
    }
}
