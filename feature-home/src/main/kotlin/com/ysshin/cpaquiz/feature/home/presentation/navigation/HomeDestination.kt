package com.ysshin.cpaquiz.feature.home.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeRoute
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.shared.navigation.CpaQuizNavigationDestination

object HomeDestination : CpaQuizNavigationDestination {
    override val route = "HomeRoute"
    override val destination = "HomeDestination"
}

fun NavGraphBuilder.homeGraph(navigator: ProblemDetailNavigator, windowSizeClass: WindowSizeClass) {
    composable(route = HomeDestination.route) {
        HomeRoute(navigator = navigator, windowSizeClass = windowSizeClass)
    }
}
