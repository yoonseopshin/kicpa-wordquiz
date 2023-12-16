package com.ysshin.cpaquiz.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ysshin.cpaquiz.feature.home.presentation.navigation.homeGraph
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.noteGraph
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.settingsGraph

@Composable
fun CpaQuizNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph()
        noteGraph(windowSizeClass)
        settingsGraph(windowSizeClass)
    }
}
