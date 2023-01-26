package com.cpa.cpa_word_problem.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ysshin.core.navigation.CpaQuizNavigationDestination
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumers
import com.ysshin.cpaquiz.feature.home.presentation.navigation.homeGraph
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.noteGraph
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.settingsGraph

@Composable
fun CpaQuizNavHost(
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
        homeGraph(windowSizeClass)
        noteGraph(windowSizeClass)
        settingsGraph(windowSizeClass)
    }
}
