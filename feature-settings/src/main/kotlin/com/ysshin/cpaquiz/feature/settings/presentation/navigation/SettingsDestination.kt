package com.ysshin.cpaquiz.feature.settings.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.settings.presentation.ui.SettingsScreen
import com.ysshin.shared.navigation.CpaQuizNavigationDestination

object SettingsDestination : CpaQuizNavigationDestination {
    override val route = "SettingsRoute"
    override val destination = "SettingsDestination"
}

fun NavGraphBuilder.settingsGraph() {
    composable(SettingsDestination.route) {
        SettingsScreen()
    }
}