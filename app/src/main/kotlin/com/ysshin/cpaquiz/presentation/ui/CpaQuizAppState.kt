package com.ysshin.cpaquiz.presentation.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.ysshin.core.navigation.CpaQuizNavigationDestination
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.data.util.NetworkMonitor
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.NoteDestination
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.SettingsDestination
import com.ysshin.cpaquiz.presentation.navigation.TopLevelDestination
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberCpaQuizAppState(
    windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    startDestination: String,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): CpaQuizAppState {
    return remember(navController) {
        CpaQuizAppState(
            navController = navController,
            coroutineScope = coroutineScope,
            windowSizeClass = windowSizeClass,
            networkMonitor = networkMonitor,
            startDestination = startDestination,
        )
    }
}

@Stable
class CpaQuizAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val windowSizeClass: WindowSizeClass,
    networkMonitor: NetworkMonitor,
    val startDestination: String,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val topLevelDestinations = persistentListOf(
        TopLevelDestination(
            route = HomeDestination.route,
            destination = HomeDestination.destination,
            selectedIcon = CpaIcons.HomeFilled,
            unselectedIcon = CpaIcons.Home,
            iconTextResourceId = R.string.home,
        ),
        TopLevelDestination(
            route = NoteDestination.route,
            destination = NoteDestination.destination,
            selectedIcon = CpaIcons.EditFilled,
            unselectedIcon = CpaIcons.Edit,
            iconTextResourceId = R.string.note,
        ),
        TopLevelDestination(
            route = SettingsDestination.route,
            destination = SettingsDestination.destination,
            selectedIcon = CpaIcons.SettingsFilled,
            unselectedIcon = CpaIcons.Settings,
            iconTextResourceId = R.string.settings,
        ),
    )

    val shouldShowBottomBar: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowNavRail: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium ||
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    fun navigate(destination: CpaQuizNavigationDestination) {
        if (destination is TopLevelDestination) {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            navController.navigate(destination.route, topLevelNavOptions)
        } else {
            navController.navigate(destination.route)
        }
    }
}
