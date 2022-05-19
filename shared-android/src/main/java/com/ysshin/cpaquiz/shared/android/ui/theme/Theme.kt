package com.ysshin.cpaquiz.shared.android.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.ViewCompat
import com.ysshin.cpaquiz.shared.android.R
import dagger.hilt.android.internal.managers.FragmentComponentManager

private val DarkColors = darkColors(
    primary = Purple80,
    secondary = PurpleGrey80,
)

private val LightColors = lightColors(
    primary = Purple40,
    secondary = PurpleGrey40,

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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (FragmentComponentManager.findActivity(view.context) as Activity).window.statusBarColor =
                colors.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography
    ) {
        CompositionLocalProvider(LocalRippleTheme provides CpaQuizRippleTheme, content = content)
    }
}