package app.restora.pregnancytracker.ui.theme

import android.os.Build
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.restora.pregnancytracker.R

private val SupportsVariableFonts = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

@OptIn(ExperimentalTextApi::class)
private val BloomDisplay = if (SupportsVariableFonts) {
    FontFamily(
        Font(
            R.font.fraunces_variable,
            weight = FontWeight.SemiBold,
            variationSettings = FontVariation.Settings(FontVariation.weight(600))
        )
    )
} else {
    FontFamily.Serif
}

@OptIn(ExperimentalTextApi::class)
private val BloomSans = if (SupportsVariableFonts) {
    FontFamily(
        Font(
            R.font.figtree_variable,
            weight = FontWeight.Normal,
            variationSettings = FontVariation.Settings(FontVariation.weight(400))
        ),
        Font(
            R.font.figtree_variable,
            weight = FontWeight.SemiBold,
            variationSettings = FontVariation.Settings(FontVariation.weight(600))
        )
    )
} else {
    FontFamily.SansSerif
}

@OptIn(ExperimentalTextApi::class)
private val BloomUrduUi = if (SupportsVariableFonts) {
    FontFamily(
        Font(
            R.font.noto_naskh_arabic_variable,
            weight = FontWeight.Normal,
            variationSettings = FontVariation.Settings(FontVariation.weight(400))
        ),
        Font(
            R.font.noto_naskh_arabic_variable,
            weight = FontWeight.SemiBold,
            variationSettings = FontVariation.Settings(FontVariation.weight(600))
        )
    )
} else {
    FontFamily.SansSerif
}

@OptIn(ExperimentalTextApi::class)
private val BloomUrduDisplay = if (SupportsVariableFonts) {
    FontFamily(
        Font(
            R.font.noto_nastaliq_urdu_variable,
            weight = FontWeight.Normal,
            variationSettings = FontVariation.Settings(FontVariation.weight(400))
        )
    )
} else {
    BloomUrduUi
}

private val LatinTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = BloomDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 56.sp,
        lineHeight = 60.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = BloomDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BloomDisplay,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleLarge = TextStyle(
        fontFamily = BloomSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BloomSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = BloomSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = BloomSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelMedium = TextStyle(
        fontFamily = BloomSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

private val UrduTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = BloomUrduDisplay,
        fontWeight = FontWeight.Normal,
        fontSize = 56.sp,
        lineHeight = 82.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 42.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 34.sp
    ),
    titleLarge = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 26.sp
    ),
    labelLarge = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 26.sp
    ),
    labelMedium = TextStyle(
        fontFamily = BloomUrduUi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 24.sp
    )
)

fun bloomTypography(language: String): Typography =
    if (language == "ur") UrduTypography else LatinTypography
