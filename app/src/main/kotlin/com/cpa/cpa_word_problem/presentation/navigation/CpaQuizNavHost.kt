package com.cpa.cpa_word_problem.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ysshin.cpaquiz.feature.home.presentation.navigation.homeGraph
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.noteGraph
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.settingsGraph
import com.ysshin.cpaquiz.core.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.core.common.Action
import com.ysshin.cpaquiz.core.common.Consumers
import com.ysshin.core.navigation.CpaQuizNavigationDestination

@Composable
fun CpaQuizNavHost(
    navigator: ProblemDetailNavigator,
    navController: NavHostController,
    // TODO: Use this after applying single activity architecture.
    onNavigateToDestination: Consumers<CpaQuizNavigationDestination, String>,
    onBackClick: Action,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeGraph(navigator, windowSizeClass)
        noteGraph(windowSizeClass)
        settingsGraph(windowSizeClass)
    }
}
