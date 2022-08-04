package com.cpa.cpa_word_problem.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ysshin.cpaquiz.feature.home.presentation.navigation.homeGraph
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.noteGraph
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.settingsGraph
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Consumers
import com.ysshin.shared.navigation.CpaQuizNavigationDestination

@Composable
fun CpaQuizNavHost(
    navigator: ProblemDetailNavigator,
    navController: NavHostController,
    onNavigateToDestination: Consumers<CpaQuizNavigationDestination, String>,
    onBackClick: Action,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeGraph(navigator)
        noteGraph()
        settingsGraph()
    }
}
