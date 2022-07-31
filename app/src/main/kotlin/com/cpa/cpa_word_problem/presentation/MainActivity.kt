package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cpa.cpa_word_problem.presentation.ui.components.MainTabRow
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeViewModel
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeScreen
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.NoteScreen
import com.ysshin.cpaquiz.feature.settings.presentation.screen.main.SettingsViewModel
import com.ysshin.cpaquiz.feature.settings.presentation.ui.SettingsScreen
import com.ysshin.cpaquiz.shared.android.base.BaseActivity
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.android.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var problemDetailNavigator: ProblemDetailNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(problemDetailNavigator, getDestinationTab())
        }
    }

    private fun getDestinationTab(): MainScreen {
        return intent?.extras?.let { extras ->
            (extras.getSerializable(Constants.destination) as? MainScreen)
        } ?: MainScreen.Home
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navigator: ProblemDetailNavigator, destination: MainScreen) {
    CpaQuizTheme {
        val allScreens = MainScreen.values().toList()
        val navController = rememberNavController()
        var currentTab = destination

        Scaffold(
            bottomBar = {
                // TODO: Improve tab
                MainTabRow(
                    allScreens = allScreens,
                    onTabSelected = { tab ->
                        currentTab = tab
                        navController.navigate(tab.name)
                    },
                    currentScreen = currentTab
                )
            }
        ) { padding ->
            NavHost(
                navController = navController,
                startDestination = destination.toString(),
                modifier = Modifier.padding(padding)
            ) {
                composable(MainScreen.Home.toString()) {
                    val homeViewModel = hiltViewModel<HomeViewModel>()
                    HomeScreen(navigator = navigator, viewModel = homeViewModel)
                }
                composable(MainScreen.Note.toString()) {
                    val noteViewModel = hiltViewModel<NoteViewModel>()
                    NoteScreen(viewModel = noteViewModel)
                }
                composable(MainScreen.Settings.toString()) {
                    val settingsViewModel = hiltViewModel<SettingsViewModel>()
                    SettingsScreen(viewModel = settingsViewModel)
                }
            }
        }
    }
}
