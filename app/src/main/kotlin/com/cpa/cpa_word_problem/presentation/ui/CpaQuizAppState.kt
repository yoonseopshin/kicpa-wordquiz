package com.cpa.cpa_word_problem.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.presentation.navigation.TopLevelDestination
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.NoteDestination
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.SettingsDestination
import com.ysshin.shared.navigation.CpaQuizNavigationDestination

@Composable
fun rememberCpaQuizAppState(navController: NavHostController, startDestination: String): CpaQuizAppState {
    return remember(navController) {
        CpaQuizAppState(navController, startDestination)
    }
}

@Stable
class CpaQuizAppState(val navController: NavHostController, val startDestination: String) {

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations = listOf(
        TopLevelDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            iconTextResourceId = R.string.home
        ),
        TopLevelDestination(
            route = NoteDestination.route,
            destination = NoteDestination.destination,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            iconTextResourceId = R.string.note
        ),
        TopLevelDestination(
            route = SettingsDestination.route,
            destination = SettingsDestination.destination,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            iconTextResourceId = R.string.settings
        )
    )

    fun navigate(destination: CpaQuizNavigationDestination, route: String? = null) {
        if (destination is TopLevelDestination) {
            navController.navigate(route ?: destination.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        } else {
            navController.navigate(route ?: destination.route)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
