package com.ysshin.cpaquiz.feature.home.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeRoute
import com.ysshin.core.navigation.CpaQuizNavigationDestination

object HomeDestination : CpaQuizNavigationDestination {
    override val route = "HomeRoute"
    override val destination = "HomeDestination"
}

fun NavGraphBuilder.homeGraph(windowSizeClass: WindowSizeClass) {
    composable(route = HomeDestination.route) {
        HomeRoute(windowSizeClass = windowSizeClass)
    }
}
