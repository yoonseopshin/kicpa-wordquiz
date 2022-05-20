package com.ysshin.cpaquiz.shared.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.res.colorResource
import com.ysshin.cpaquiz.shared.android.R

private val DarkColors = darkColors(
    primary = MaterialColor.INDIGO_700,
    primaryVariant = MaterialColor.INDIGO_900,
    secondary = MaterialColor.DEEP_PURPLE_A200,
)

private val LightColors = lightColors(
    primary = MaterialColor.LIGHT_GREEN_700,
    primaryVariant = MaterialColor.LIGHT_GREEN_900,
    secondary = MaterialColor.AMBER_A200,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private object CpaQuizRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = colorResource(id = R.color.reverse_theme_color),
        lightTheme = isSystemInDarkTheme().not()
    )

    @Composable
    override fun rippleAlpha() = RippleAlpha(
        focusedAlpha = 0.12f,
        draggedAlpha = 0.12f,
        hoveredAlpha = 0.12f,
        pressedAlpha = 0.12f
    )

}

@Composable
fun CpaQuizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colors = colors,
        typography = Typography
    ) {
        CompositionLocalProvider(LocalRippleTheme provides CpaQuizRippleTheme, content = content)
    }
}