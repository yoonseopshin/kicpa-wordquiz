package com.ysshin.cpaquiz.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.core.navigation.CpaQuizNavigationDestination
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeRoute

object HomeDestination : CpaQuizNavigationDestination {
    override val route = "HomeRoute"
    override val destination = "HomeDestination"
}

fun NavGraphBuilder.homeGraph() {
    composable(route = HomeDestination.route) {
        HomeRoute()
    }
}
