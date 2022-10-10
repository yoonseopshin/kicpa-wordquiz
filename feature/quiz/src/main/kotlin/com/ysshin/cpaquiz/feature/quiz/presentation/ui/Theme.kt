package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_background
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_error
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_onBackground
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_onError
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_onPrimary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_onSecondary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_onSurface
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_primary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_secondary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_dark_surface
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_background
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_error
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_onBackground
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_onError
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_onPrimary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_onSecondary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_onSurface
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_primary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_secondary
import com.ysshin.cpaquiz.core.android.ui.theme.md_theme_light_surface

private val LightColorScheme = lightColors(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
)

private val DarkColorScheme = darkColors(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
)

// FIXME: If material3 supports BottomSheetScaffold, remove this legacy theme.
@Composable
fun CpaQuizLegacyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colors = colors,
        content = content
    )
}
