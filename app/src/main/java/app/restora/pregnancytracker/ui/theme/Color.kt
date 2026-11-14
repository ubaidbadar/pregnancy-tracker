package app.restora.pregnancytracker.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

enum class BloomThemeMode {
    DAWN,
    NIGHT,
    QUIET
}

enum class BloomSeason {
    LAGOON,
    APRICOT,
    DUSK
}

data class BloomPalette(
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val surface: Color,
    val raised: Color,
    val ink: Color,
    val muted: Color,
    val primary: Color,
    val onPrimary: Color,
    val tint: Color,
    val deep: Color,
    val actionAccent: Color,
    val onActionAccent: Color,
    val error: Color
)

private val Paper = Color(0xFFFFF8F3)
private val Surface = Color(0xFFFFFFFF)
private val Raised = Color(0xFFF7EBE4)
private val Ink = Color(0xFF2B2024)
private val Muted = Color(0xFF6B5B61)
private val Error = Color(0xFFA3263A)

private val LagoonPalette = BloomPalette(
    backgroundTop = Color(0xFFF4FBF9),
    backgroundBottom = Color(0xFFDDF1EE),
    surface = Surface,
    raised = Raised,
    ink = Ink,
    muted = Muted,
    primary = Color(0xFF1F7A78),
    onPrimary = Color.White,
    tint = Color(0xFFDDF1EE),
    deep = Color(0xFF14504E),
    actionAccent = Color(0xFF1F7A78),
    onActionAccent = Color.White,
    error = Error
)

private val ApricotPalette = BloomPalette(
    backgroundTop = Paper,
    backgroundBottom = Color(0xFFFFEBDD),
    surface = Surface,
    raised = Raised,
    ink = Ink,
    muted = Muted,
    primary = Color(0xFFB4531A),
    onPrimary = Color.White,
    tint = Color(0xFFFFE6D2),
    deep = Color(0xFF7A3510),
    actionAccent = Color(0xFFB4531A),
    onActionAccent = Color.White,
    error = Error
)

private val DuskPalette = BloomPalette(
    backgroundTop = Color(0xFFFAF5FD),
    backgroundBottom = Color(0xFFEBDDF5),
    surface = Surface,
    raised = Raised,
    ink = Ink,
    muted = Muted,
    primary = Color(0xFF6E3B8E),
    onPrimary = Color.White,
    tint = Color(0xFFEBDDF5),
    deep = Color(0xFF4A2562),
    actionAccent = Color(0xFF6E3B8E),
    onActionAccent = Color.White,
    error = Error
)

private val NightPalette = BloomPalette(
    backgroundTop = Color(0xFF1C1519),
    backgroundBottom = Color(0xFF1C1519),
    surface = Color(0xFF271D23),
    raised = Color(0xFF33262D),
    ink = Color(0xFFF2E6EA),
    muted = Color(0xFFC9B8BF),
    primary = Color(0xFF7FD3CC),
    onPrimary = Color(0xFF1C1519),
    tint = Color(0xFF3A2A24),
    deep = Color(0xFFD3B3F0),
    actionAccent = Color(0xFFFFB68A),
    onActionAccent = Color(0xFF2B2024),
    error = Color(0xFFFF9BA8)
)

private val QuietPalette = BloomPalette(
    backgroundTop = Color(0xFFE9ECEF),
    backgroundBottom = Color(0xFFE9ECEF),
    surface = Color.White,
    raised = Color(0xFFDDE1E5),
    ink = Color(0xFF2B2E33),
    muted = Color(0xFF4A5159),
    primary = Color(0xFF5E666F),
    onPrimary = Color.White,
    tint = Color(0xFFDDE1E5),
    deep = Color(0xFF3C434A),
    actionAccent = Color(0xFF5E666F),
    onActionAccent = Color.White,
    error = Error
)

val LocalBloomPalette = staticCompositionLocalOf { ApricotPalette }

fun bloomPalette(
    mode: BloomThemeMode,
    season: BloomSeason = BloomSeason.APRICOT
): BloomPalette = when (mode) {
    BloomThemeMode.NIGHT -> NightPalette
    BloomThemeMode.QUIET -> QuietPalette
    BloomThemeMode.DAWN -> when (season) {
        BloomSeason.LAGOON -> LagoonPalette
        BloomSeason.APRICOT -> ApricotPalette
        BloomSeason.DUSK -> DuskPalette
    }
}
