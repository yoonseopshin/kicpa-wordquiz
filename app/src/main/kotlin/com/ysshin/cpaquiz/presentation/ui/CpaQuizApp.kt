package com.ysshin.cpaquiz.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.network.NetworkConnectivityStatusBox
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.presentation.navigation.CpaQuizNavHost
import com.ysshin.cpaquiz.presentation.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CpaQuizApp(appState: CpaQuizAppState) {
    CpaQuizTheme {
        val isOffline by appState.isOffline.collectAsStateWithLifecycle()

        Scaffold(
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    CpaQuizBottomBar(
                        destinations = appState.topLevelDestinations,
                        onNavigateToDestination = appState::navigate,
                        currentDestination = appState.currentDestination
                    )
                }
            }
        ) { padding ->
            Column {
                NetworkConnectivityStatusBox(isOffline = isOffline)

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                ) {
                    if (appState.shouldShowNavRail) {
                        CpaQuizNavigationRail(
                            destinations = appState.topLevelDestinations,
                            onNavigateToDestination = appState::navigate,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.safeDrawingPadding(),
                        )
                    }

                    CpaQuizNavHost(
                        navController = appState.navController,
                        windowSizeClass = appState.windowSizeClass,
                        modifier = Modifier
                            .padding(padding)
                            .consumeWindowInsets(padding),
                        startDestination = appState.startDestination,
                    )
                }
            }
        }
    }
}

@Composable
private fun CpaQuizBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: Consumer<TopLevelDestination>,
    currentDestination: NavDestination?,
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = Modifier.windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destination) },
                    icon = {
                        val icon = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        Icon(imageVector = icon, contentDescription = null)
                    },
                    label = {
                        Text(
                            modifier = Modifier.resourceTestTag(stringResource(id = destination.iconTextResourceId)),
                            text = stringResource(id = destination.iconTextResourceId)
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun CpaQuizNavigationRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: Consumer<TopLevelDestination>,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }
                    Icon(imageVector = icon, contentDescription = null)
                },
                label = {
                    Text(
                        modifier = Modifier.resourceTestTag(stringResource(id = destination.iconTextResourceId)),
                        text = stringResource(id = destination.iconTextResourceId)
                    )
                }
            )
        }
    }
}
