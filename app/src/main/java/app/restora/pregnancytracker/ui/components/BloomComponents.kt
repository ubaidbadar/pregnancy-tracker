package app.restora.pregnancytracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.restora.pregnancytracker.ui.theme.LocalBloomPalette

@Composable
fun BloomPage(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val palette = LocalBloomPalette.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(palette.backgroundTop, palette.backgroundBottom)
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        content = content
    )
}

@Composable
fun BloomCard(
    modifier: Modifier = Modifier,
    padding: Dp = 20.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val palette = LocalBloomPalette.current
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = palette.surface,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun BloomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val palette = LocalBloomPalette.current
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = palette.primary,
            contentColor = palette.onPrimary,
            disabledContainerColor = palette.primary.copy(alpha = 0.38f),
            disabledContentColor = palette.onPrimary.copy(alpha = 0.75f)
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge.copy(fontSize = 16.sp))
    }
}

@Composable
fun BloomSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val palette = LocalBloomPalette.current
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        shape = CircleShape,
        border = androidx.compose.foundation.BorderStroke(2.dp, palette.primary),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = palette.primary,
            disabledContentColor = palette.muted
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun BloomTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    val palette = LocalBloomPalette.current
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = 48.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = color ?: palette.muted,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun BloomInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = true,
    minHeight: Dp = 60.dp
) {
    val palette = LocalBloomPalette.current
    val shape = RoundedCornerShape(22.dp)
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .clip(shape)
            .background(palette.surface)
            .border(2.dp, palette.raised, shape)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        singleLine = singleLine,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = palette.ink),
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty() && placeholder.isNotEmpty()) {
                    Text(
                        text = placeholder,
                        color = palette.muted,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun BloomChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = LocalBloomPalette.current
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        color = if (selected) palette.primary else palette.tint
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = if (selected) palette.onPrimary else palette.deep,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun BloomSectionLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null
) {
    val palette = LocalBloomPalette.current
    Text(
        text = text,
        modifier = modifier,
        color = color ?: palette.muted,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
    )
}

@Composable
fun BloomPageTitle(
    text: String,
    modifier: Modifier = Modifier,
    size: Int = 30
) {
    val palette = LocalBloomPalette.current
    Text(
        text = text,
        modifier = modifier.fillMaxWidth(),
        color = palette.ink,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = size.sp,
            lineHeight = (size + 6).sp
        )
    )
}

@Composable
fun BloomOrb(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    width: Dp = 250.dp,
    height: Dp = 225.dp,
    valueSize: Int = 76,
    contentDescription: String = label
) {
    val palette = LocalBloomPalette.current
    Box(
        modifier = modifier
            .size(width = width, height = height)
            .semantics(mergeDescendants = true) { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val outer = Path().apply {
                moveTo(w * 0.51f, h * 0.04f)
                cubicTo(w * 0.75f, h * 0.01f, w * 0.96f, h * 0.23f, w * 0.93f, h * 0.49f)
                cubicTo(w * 0.90f, h * 0.78f, w * 0.70f, h * 0.97f, w * 0.46f, h * 0.95f)
                cubicTo(w * 0.22f, h * 0.94f, w * 0.04f, h * 0.73f, w * 0.07f, h * 0.48f)
                cubicTo(w * 0.10f, h * 0.22f, w * 0.28f, h * 0.06f, w * 0.51f, h * 0.04f)
                close()
            }
            drawPath(
                outer,
                Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.9f),
                        palette.tint,
                        palette.primary.copy(alpha = 0.18f)
                    ),
                    center = Offset(w * 0.4f, h * 0.35f),
                    radius = maxOf(w, h) * 0.85f
                )
            )

            val inner = Path().apply {
                moveTo(w * 0.50f, h * 0.18f)
                cubicTo(w * 0.68f, h * 0.16f, w * 0.83f, h * 0.32f, w * 0.82f, h * 0.51f)
                cubicTo(w * 0.80f, h * 0.71f, w * 0.65f, h * 0.84f, w * 0.48f, h * 0.83f)
                cubicTo(w * 0.29f, h * 0.82f, w * 0.16f, h * 0.68f, w * 0.18f, h * 0.50f)
                cubicTo(w * 0.20f, h * 0.32f, w * 0.33f, h * 0.19f, w * 0.50f, h * 0.18f)
                close()
            }
            drawPath(inner, Color.White.copy(alpha = 0.55f))
            drawCircle(
                color = palette.primary.copy(alpha = 0.85f),
                radius = w * 0.04f,
                center = Offset(w * 0.82f, h * 0.20f)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                color = palette.deep,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = valueSize.sp,
                    lineHeight = (valueSize + 2).sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = label,
                color = palette.deep,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BloomProgressSegments(
    activeSegments: Int,
    modifier: Modifier = Modifier
) {
    val palette = LocalBloomPalette.current
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(4) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(if (index < activeSegments) palette.primary else palette.raised)
            )
        }
    }
}

enum class BloomTab {
    TODAY,
    JOURNAL,
    TOOLS,
    PLAN
}

private data class NavItem(
    val tab: BloomTab,
    val label: String,
    val icon: BloomIconType
)

@Composable
fun BloomBottomBar(
    selected: BloomTab,
    labels: List<String>,
    onSelect: (BloomTab) -> Unit
) {
    val palette = LocalBloomPalette.current
    val items = remember(labels) {
        listOf(
            NavItem(BloomTab.TODAY, labels.getOrElse(0) { "Today" }, BloomIconType.TODAY),
            NavItem(BloomTab.JOURNAL, labels.getOrElse(1) { "Journal" }, BloomIconType.JOURNAL),
            NavItem(BloomTab.TOOLS, labels.getOrElse(2) { "Tools" }, BloomIconType.TOOLS),
            NavItem(BloomTab.PLAN, labels.getOrElse(3) { "Plan" }, BloomIconType.PLAN)
        )
    }
    Surface(
        color = palette.surface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            items.forEach { item ->
                val active = selected == item.tab
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 52.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .selectable(selected = active, role = Role.Tab, onClick = { onSelect(item.tab) })
                        .padding(vertical = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 56.dp, height = 32.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (active) palette.tint else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        BloomIcon(
                            type = item.icon,
                            color = if (active) palette.primary else palette.muted
                        )
                    }
                    Text(
                        text = item.label,
                        color = if (active) palette.primary else palette.muted,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

enum class BloomIconType {
    TODAY,
    JOURNAL,
    TOOLS,
    PLAN,
    PROFILE
}

@Composable
fun BloomIcon(
    type: BloomIconType,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val stroke = 2.dp.toPx()
        val center = Offset(size.width / 2f, size.height / 2f)
        when (type) {
            BloomIconType.TODAY -> {
                drawCircle(color, radius = size.width * 0.17f, center = center, style = Stroke(stroke))
                val inner = size.width * 0.30f
                val outer = size.width * 0.43f
                repeat(4) { index ->
                    val angle = Math.toRadians((index * 90.0))
                    val dx = kotlin.math.cos(angle).toFloat()
                    val dy = kotlin.math.sin(angle).toFloat()
                    drawLine(
                        color,
                        Offset(center.x + dx * inner, center.y + dy * inner),
                        Offset(center.x + dx * outer, center.y + dy * outer),
                        stroke,
                        cap = StrokeCap.Round
                    )
                }
            }
            BloomIconType.JOURNAL -> {
                drawRoundRect(
                    color,
                    topLeft = Offset(size.width * 0.22f, size.height * 0.10f),
                    size = Size(size.width * 0.56f, size.height * 0.80f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
                    style = Stroke(stroke)
                )
                repeat(2) { index ->
                    val y = size.height * (0.38f + index * 0.18f)
                    drawLine(color, Offset(size.width * 0.38f, y), Offset(size.width * 0.63f, y), stroke)
                }
            }
            BloomIconType.TOOLS -> {
                val cell = size.width * 0.29f
                val gap = size.width * 0.12f
                listOf(
                    Offset(size.width * 0.12f, size.height * 0.12f),
                    Offset(size.width * 0.12f + cell + gap, size.height * 0.12f),
                    Offset(size.width * 0.12f, size.height * 0.12f + cell + gap),
                    Offset(size.width * 0.12f + cell + gap, size.height * 0.12f + cell + gap)
                ).forEach { point ->
                    drawRoundRect(
                        color,
                        topLeft = point,
                        size = Size(cell, cell),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx()),
                        style = Stroke(stroke)
                    )
                }
            }
            BloomIconType.PLAN -> {
                drawRoundRect(
                    color,
                    topLeft = Offset(size.width * 0.12f, size.height * 0.20f),
                    size = Size(size.width * 0.76f, size.height * 0.68f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx()),
                    style = Stroke(stroke)
                )
                drawLine(color, Offset(size.width * 0.12f, size.height * 0.42f), Offset(size.width * 0.88f, size.height * 0.42f), stroke)
                drawLine(color, Offset(size.width * 0.34f, size.height * 0.10f), Offset(size.width * 0.34f, size.height * 0.30f), stroke, cap = StrokeCap.Round)
                drawLine(color, Offset(size.width * 0.66f, size.height * 0.10f), Offset(size.width * 0.66f, size.height * 0.30f), stroke, cap = StrokeCap.Round)
            }
            BloomIconType.PROFILE -> {
                drawCircle(color, radius = size.width * 0.15f, center = Offset(center.x, size.height * 0.34f), style = Stroke(stroke))
                drawArc(
                    color,
                    startAngle = 200f,
                    sweepAngle = 140f,
                    useCenter = false,
                    topLeft = Offset(size.width * 0.18f, size.height * 0.44f),
                    size = Size(size.width * 0.64f, size.height * 0.48f),
                    style = Stroke(stroke, cap = StrokeCap.Round)
                )
            }
        }
    }
}

@Composable
fun BloomEmptyState(
    text: String,
    modifier: Modifier = Modifier
) {
    val palette = LocalBloomPalette.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.5.dp, palette.raised, RoundedCornerShape(24.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = palette.muted,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BloomMoodOption(
    label: String,
    selected: Boolean,
    index: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = LocalBloomPalette.current
    val shapes = listOf(
        RoundedCornerShape(50),
        RoundedCornerShape(32),
        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 8.dp, bottomEnd = 8.dp),
        RoundedCornerShape(topStart = 38.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 38.dp),
        RoundedCornerShape(14.dp)
    )
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(shapes[index.coerceIn(0, 4)])
                .background(if (selected) palette.primary else palette.tint)
                .then(
                    if (selected) Modifier.border(2.dp, palette.deep, shapes[index.coerceIn(0, 4)])
                    else Modifier
                )
        )
        Text(
            text = label,
            color = palette.ink,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
fun BloomSettingRow(
    label: String,
    value: String = "",
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true
) {
    val palette = LocalBloomPalette.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 52.dp)
            .then(if (onClick != null && enabled) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = if (enabled) palette.ink else palette.muted,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                color = palette.muted,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
