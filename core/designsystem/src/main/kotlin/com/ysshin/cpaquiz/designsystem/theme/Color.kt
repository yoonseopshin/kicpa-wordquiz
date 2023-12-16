package com.ysshin.cpaquiz.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.ysshin.cpaquiz.designsystem.R

val md_theme_light_primary = Color(0xFF71585F)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFBDAE3)
val md_theme_light_onPrimaryContainer = Color(0xFF28161D)
val md_theme_light_secondary = Color(0xFF655C5E)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFECDFE1)
val md_theme_light_onSecondaryContainer = Color(0xFF201A1C)
val md_theme_light_tertiary = Color(0xFF685C53)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFF1DFD4)
val md_theme_light_onTertiaryContainer = Color(0xFF231A13)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1D1B1B)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1D1B1B)
val md_theme_light_surfaceVariant = Color(0xFFE9E1E1)
val md_theme_light_onSurfaceVariant = Color(0xFF4A4646)
val md_theme_light_outline = Color(0xFF7B7676)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_inverseSurface = Color(0xFF323030)
val md_theme_light_inverseOnSurface = Color(0xFFF5EFEF)
val md_theme_light_inversePrimary = Color(0xFFDEBEC7)
val md_theme_light_surfaceTint = Color(0xFF71585F)

val md_theme_dark_primary = Color(0xFFDEBEC7)
val md_theme_dark_onPrimary = Color(0xFF3F2B32)
val md_theme_dark_primaryContainer = Color(0xFF574148)
val md_theme_dark_onPrimaryContainer = Color(0xFFFBDAE3)
val md_theme_dark_secondary = Color(0xFFD0C4C6)
val md_theme_dark_onSecondary = Color(0xFF362F30)
val md_theme_dark_secondaryContainer = Color(0xFF4D4547)
val md_theme_dark_onSecondaryContainer = Color(0xFFECDFE1)
val md_theme_dark_tertiary = Color(0xFFD4C3B9)
val md_theme_dark_onTertiary = Color(0xFF392E27)
val md_theme_dark_tertiaryContainer = Color(0xFF50453D)
val md_theme_dark_onTertiaryContainer = Color(0xFFF1DFD4)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onErrorContainer = Color(0xFFFFB4AB)
val md_theme_dark_background = Color(0xFF1D1B1B)
val md_theme_dark_onBackground = Color(0xFFE7E1E1)
val md_theme_dark_surface = Color(0xFF1D1B1B)
val md_theme_dark_onSurface = Color(0xFFE7E1E1)
val md_theme_dark_surfaceVariant = Color(0xFF4A4646)
val md_theme_dark_onSurfaceVariant = Color(0xFFCCC5C5)
val md_theme_dark_outline = Color(0xFF958F90)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_inverseSurface = Color(0xFFE7E1E1)
val md_theme_dark_inverseOnSurface = Color(0xFF323030)
val md_theme_dark_inversePrimary = Color(0xFF71585F)
val md_theme_dark_surfaceTint = Color(0xFFDEBEC7)

val DayNightGray900S
    @Composable
    get() = colorResource(id = R.color.daynight_gray900s)

val DayNightGray800S
    @Composable
    get() = colorResource(id = R.color.daynight_gray800s)

val DayNightGray600S
    @Composable
    get() = colorResource(id = R.color.daynight_gray600s)

val DayNightGray100A
    @Composable
    get() = colorResource(id = R.color.daynight_gray100a)

val DayNightGray070S
    @Composable
    get() = colorResource(id = R.color.daynight_gray070s)

val ColorScheme.shimmerColorShades: List<Color>
    @Composable
    get() = if (isSystemInDarkTheme()) {
        listOf(
            Color.DarkGray.copy(alpha = 0.7f),
            Color.DarkGray.copy(alpha = 0.1f),
            Color.DarkGray.copy(alpha = 0.7f),
        )
    } else {
        listOf(
            Color.LightGray.copy(alpha = 0.5f),
            Color.LightGray.copy(alpha = 0.1f),
            Color.LightGray.copy(alpha = 0.5f),
        )
    }
