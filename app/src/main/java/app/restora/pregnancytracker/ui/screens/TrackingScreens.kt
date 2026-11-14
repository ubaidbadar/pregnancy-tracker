package app.restora.pregnancytracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import app.restora.pregnancytracker.R
import app.restora.pregnancytracker.ui.components.BloomButton
import app.restora.pregnancytracker.ui.components.BloomCard
import app.restora.pregnancytracker.ui.components.BloomOrb
import app.restora.pregnancytracker.ui.components.BloomPage
import app.restora.pregnancytracker.ui.components.BloomPageTitle
import app.restora.pregnancytracker.ui.components.BloomSecondaryButton
import app.restora.pregnancytracker.ui.components.BloomSectionLabel
import app.restora.pregnancytracker.ui.components.BloomTextButton
import app.restora.pregnancytracker.ui.theme.LocalBloomPalette
import java.util.Locale

@Composable
fun KickCounterScreen(
    count: Long,
    elapsedSeconds: Long,
    onTap: () -> Unit,
    onEnd: () -> Unit
) {
    val palette = LocalBloomPalette.current
    val haptics = LocalHapticFeedback.current
    BloomPage {
        BloomPageTitle(stringResource(R.string.kick_counter))

        Surface(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(18.dp),
            color = palette.tint
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.elapsed),
                    color = palette.muted,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = formatDuration(elapsedSeconds),
                    color = palette.ink,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = count.toString(),
                color = palette.deep,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 68.sp,
                    lineHeight = 76.sp
                )
            )
            Text(
                text = stringResource(R.string.kick_count_label),
                color = palette.muted,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(28.dp))
            Surface(
                onClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onTap()
                },
                modifier = Modifier.size(168.dp),
                shape = CircleShape,
                color = palette.tint,
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(144.dp)
                            .clip(CircleShape)
                            .background(palette.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.tap),
                            color = palette.onPrimary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
            }
        }

        BloomTextButton(
            text = stringResource(R.string.end_session),
            onClick = onEnd,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun KickSummaryScreen(
    count: Long,
    durationSeconds: Long,
    onShare: () -> Unit,
    onDone: () -> Unit
) {
    BloomPage {
        BloomPageTitle(stringResource(R.string.session_finished))
        BloomOrb(
            value = count.toString(),
            label = stringResource(R.string.kicks_in_duration, formatDuration(durationSeconds)),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            width = 230.dp,
            height = 207.dp,
            valueSize = 76
        )
        BloomCard(padding = 20.dp) {
            Text(
                text = stringResource(R.string.midwife_kick_note),
                color = LocalBloomPalette.current.ink,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(Modifier.height(8.dp))
        BloomSecondaryButton(
            text = stringResource(R.string.share_with_midwife),
            onClick = onShare
        )
        BloomButton(
            text = stringResource(R.string.done),
            onClick = onDone
        )
    }
}

@Composable
fun ContractionTimerScreen(
    elapsedSeconds: Long,
    lastGapSeconds: Long,
    durations: List<Long>,
    isRunning: Boolean,
    onStart: () -> Unit,
    onStop: () -> Unit,
    onBack: () -> Unit
) {
    val palette = LocalBloomPalette.current
    BloomPage {
        BloomSectionLabel(
            text = stringResource(R.string.night_theme),
            color = palette.deep
        )
        BloomPageTitle(stringResource(R.string.contractions_title), size = 30)
        Text(
            text = formatDuration(elapsedSeconds),
            modifier = Modifier.fillMaxWidth(),
            color = palette.ink,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 84.sp,
                lineHeight = 90.sp,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = stringResource(
                R.string.last_gap,
                if (lastGapSeconds > 0L) formatDuration(lastGapSeconds) else stringResource(R.string.not_available)
            ),
            modifier = Modifier.fillMaxWidth(),
            color = palette.muted,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        if (durations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.contraction_intervals_hint),
                    color = palette.muted,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            val maxDuration = durations.maxOrNull()?.coerceAtLeast(1L) ?: 1L
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                durations.takeLast(8).forEach { duration ->
                    val barHeight = (20f + 60f * duration.toFloat() / maxDuration.toFloat()).dp
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(barHeight)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(palette.primary.copy(alpha = 0.78f))
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Surface(
            onClick = { if (isRunning) onStop() else onStart() },
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.CenterHorizontally),
            shape = CircleShape,
            color = palette.raised
        ) {
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(222.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFB68A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isRunning) stringResource(R.string.stop) else stringResource(R.string.start),
                        color = Color(0xFF2B2024),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
        BloomCard {
            Text(
                text = stringResource(R.string.midwife_guidance_placeholder),
                color = palette.muted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        BloomTextButton(
            text = stringResource(R.string.back_to_tools),
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private fun formatDuration(totalSeconds: Long): String {
    val minutes = totalSeconds / 60L
    val seconds = totalSeconds % 60L
    return String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
}
