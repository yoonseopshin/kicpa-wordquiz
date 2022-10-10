package com.ysshin.cpaquiz.feature.settings.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.settings.presentation.ui.SettingsScreen
import com.ysshin.core.navigation.CpaQuizNavigationDestination

object SettingsDestination : CpaQuizNavigationDestination {
    override val route = "SettingsRoute"
    override val destination = "SettingsDestination"
}

fun NavGraphBuilder.settingsGraph(windowSizeClass: WindowSizeClass) {
    composable(SettingsDestination.route) {
        SettingsScreen(windowSizeClass)
    }
}
