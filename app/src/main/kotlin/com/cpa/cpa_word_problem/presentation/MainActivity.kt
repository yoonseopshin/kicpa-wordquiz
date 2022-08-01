package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.cpa.cpa_word_problem.presentation.navigation.CpaQuizNavHost
import com.cpa.cpa_word_problem.presentation.navigation.TopLevelDestination
import com.cpa.cpa_word_problem.presentation.ui.CpaQuizAppState
import com.cpa.cpa_word_problem.presentation.ui.rememberCpaQuizAppState
import com.ysshin.cpaquiz.shared.android.base.BaseActivity
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.base.Consumer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    // FIXME: How to intent to original android view based activity?
    // This way doesn't seem nice.
    @Inject
    lateinit var problemDetailNavigator: ProblemDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(problemDetailNavigator)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: ProblemDetailNavigator,
    appState: CpaQuizAppState = rememberCpaQuizAppState(),
) {
    // TODO: Handle intent with destination from QuizDetailActivity.
    CpaQuizTheme {
        Scaffold(
            bottomBar = {
                TopLevelBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigate,
                    currentDestination = appState.currentDestination
                )
            }
        ) { padding ->
            CpaQuizNavHost(
                navigator = navigator,
                navController = appState.navController,
                onNavigateToDestination = appState::navigate,
                onBackClick = appState::onBackClick,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun TopLevelBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: Consumer<TopLevelDestination>,
    currentDestination: NavDestination?,
) {
    NavigationBar {
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
                    Text(text = stringResource(id = destination.iconTextResourceId))
                }
            )
        }
    }
}
