package app.restora.pregnancytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.restora.pregnancytracker.R
import app.restora.pregnancytracker.ui.components.BloomButton
import app.restora.pregnancytracker.ui.components.BloomOrb
import app.restora.pregnancytracker.ui.components.BloomPage
import app.restora.pregnancytracker.ui.components.BloomPageTitle
import app.restora.pregnancytracker.ui.components.BloomSecondaryButton
import app.restora.pregnancytracker.ui.components.BloomTextButton
import app.restora.pregnancytracker.ui.theme.LocalBloomPalette

@Composable
fun PremiumScreen(
    onNotNow: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC9B8BF)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = palette.surface,
            shadowElevation = 12.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = stringResource(R.string.bloom_plus),
                    color = palette.primary,
                    style = MaterialTheme.typography.labelLarge
                )
                BloomPageTitle(stringResource(R.string.premium_title), size = 28)
                Text(
                    text = stringResource(R.string.premium_always_free),
                    color = palette.ink,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(R.string.premium_plus_features),
                    color = palette.muted,
                    style = MaterialTheme.typography.bodyLarge
                )
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = palette.tint
                ) {
                    Text(
                        text = stringResource(R.string.premium_pricing_pending),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                        color = palette.deep,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                BloomButton(
                    text = stringResource(R.string.start_free_trial),
                    onClick = onNotNow,
                    enabled = false
                )
                BloomTextButton(
                    text = stringResource(R.string.not_now),
                    onClick = onNotNow,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun PauseScreen(
    onPause: () -> Unit,
    onLoss: () -> Unit,
    onChanged: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirmation by rememberSaveable { mutableStateOf(false) }
    var showChangedDialog by rememberSaveable { mutableStateOf(false) }
    BloomPage {
        Spacer(Modifier.height(8.dp))
        BloomPageTitle(stringResource(R.string.quiet_title), size = 32)
        Text(
            text = stringResource(R.string.quiet_question),
            color = LocalBloomPalette.current.deep,
            style = MaterialTheme.typography.titleLarge
        )
        QuietChoice(
            title = stringResource(R.string.pause_updates),
            description = stringResource(R.string.pause_updates_description),
            onClick = onPause
        )
        QuietChoice(
            title = stringResource(R.string.loss_option),
            description = stringResource(R.string.loss_option_description),
            onClick = onLoss
        )
        QuietChoice(
            title = stringResource(R.string.changed_situation),
            description = stringResource(R.string.changed_situation_description),
            onClick = { showChangedDialog = true }
        )
        QuietChoice(
            title = stringResource(R.string.delete_everything),
            description = stringResource(R.string.delete_everything_description),
            onClick = { showDeleteConfirmation = true }
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.support_content_pending),
            modifier = Modifier.fillMaxWidth(),
            color = LocalBloomPalette.current.muted,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (showDeleteConfirmation) {
        QuietDeleteDialog(
            onDismiss = { showDeleteConfirmation = false },
            onConfirm = onDelete
        )
    }
    if (showChangedDialog) {
        QuietInfoDialog(
            title = stringResource(R.string.changed_situation),
            message = stringResource(R.string.changed_situation_body),
            confirmLabel = stringResource(R.string.keep_my_data),
            onDismiss = { showChangedDialog = false },
            onConfirm = onChanged
        )
    }
}

@Composable
private fun QuietChoice(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    val palette = LocalBloomPalette.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = palette.surface,
        shadowElevation = 1.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(title, color = palette.ink, style = MaterialTheme.typography.titleLarge)
            Text(description, color = palette.muted, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun LossConfirmationScreen(
    onKeepNotes: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteConfirmation by rememberSaveable { mutableStateOf(false) }
    var showSupportInfo by rememberSaveable { mutableStateOf(false) }
    val palette = LocalBloomPalette.current

    BloomPage {
        Spacer(Modifier.height(8.dp))
        BloomPageTitle(stringResource(R.string.loss_confirmation_title), size = 34)
        Text(
            text = stringResource(R.string.loss_confirmation_body),
            color = palette.deep,
            style = MaterialTheme.typography.titleLarge
        )
        BloomButton(
            text = stringResource(R.string.keep_private_notes),
            onClick = onKeepNotes
        )
        BloomSecondaryButton(
            text = stringResource(R.string.support_and_resources),
            onClick = { showSupportInfo = true }
        )
        BloomTextButton(
            text = stringResource(R.string.delete_everything),
            onClick = { showDeleteConfirmation = true },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = palette.deep
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.support_content_pending),
            color = palette.muted,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (showDeleteConfirmation) {
        QuietDeleteDialog(
            onDismiss = { showDeleteConfirmation = false },
            onConfirm = onDelete
        )
    }
    if (showSupportInfo) {
        QuietInfoDialog(
            title = stringResource(R.string.support_and_resources),
            message = stringResource(R.string.support_content_pending),
            onDismiss = { showSupportInfo = false }
        )
    }
}

@Composable
fun BabyArrivedScreen(
    onArchive: () -> Unit,
    onSkip: () -> Unit
) {
    val palette = LocalBloomPalette.current
    BloomPage(
        bottomContent = {
            BloomButton(
                text = stringResource(R.string.switch_postpartum),
                onClick = onSkip,
                enabled = false
            )
            Text(
                text = stringResource(R.string.postpartum_not_available),
                color = palette.muted,
                style = MaterialTheme.typography.bodyMedium
            )
            BloomSecondaryButton(
                text = stringResource(R.string.archive_pregnancy),
                onClick = onArchive
            )
            BloomTextButton(
                text = stringResource(R.string.skip),
                onClick = onSkip,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    ) {
        Spacer(Modifier.height(8.dp))
        BloomPageTitle(stringResource(R.string.baby_arrived_title), size = 34)
        BloomOrb(
            value = stringResource(R.string.baby_arrived_hello),
            label = stringResource(R.string.baby_is_here),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 176.dp,
            height = 168.dp,
            valueSize = 70
        )
        Text(
            text = stringResource(R.string.take_your_time),
            modifier = Modifier.fillMaxWidth(),
            color = palette.muted,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun QuietDeleteDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    QuietInfoDialog(
        title = stringResource(R.string.delete_everything_title),
        message = stringResource(R.string.delete_everything_body),
        confirmLabel = stringResource(R.string.delete_everything),
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}

@Composable
private fun QuietInfoDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    confirmLabel: String = stringResource(R.string.done),
    onConfirm: (() -> Unit)? = null
) {
    val palette = LocalBloomPalette.current
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = palette.surface
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(title, color = palette.ink, style = MaterialTheme.typography.headlineMedium)
                Text(message, color = palette.muted, style = MaterialTheme.typography.bodyLarge)
                if (onConfirm != null) {
                    BloomSecondaryButton(stringResource(R.string.cancel), onDismiss)
                    BloomButton(confirmLabel, onConfirm)
                } else {
                    BloomButton(confirmLabel, onDismiss)
                }
            }
        }
    }
}
