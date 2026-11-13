package app.restora.pregnancytracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BloomTheme(
    mode: BloomThemeMode = BloomThemeMode.DAWN,
    season: BloomSeason = BloomSeason.APRICOT,
    content: @Composable () -> Unit
) {
    val palette = bloomPalette(mode, season)
    val colorScheme = if (mode == BloomThemeMode.NIGHT) {
        darkColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            secondary = palette.deep,
            onSecondary = palette.ink,
            background = palette.backgroundTop,
            onBackground = palette.ink,
            surface = palette.surface,
            onSurface = palette.ink,
            surfaceVariant = palette.raised,
            onSurfaceVariant = palette.muted,
            error = palette.error
        )
    } else {
        lightColorScheme(
            primary = palette.primary,
            onPrimary = palette.onPrimary,
            secondary = palette.deep,
            onSecondary = palette.onPrimary,
            background = palette.backgroundTop,
            onBackground = palette.ink,
            surface = palette.surface,
            onSurface = palette.ink,
            surfaceVariant = palette.raised,
            onSurfaceVariant = palette.muted,
            error = palette.error
        )
    }

    CompositionLocalProvider(LocalBloomPalette provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = BloomTypography,
            content = content
        )
    }
}
