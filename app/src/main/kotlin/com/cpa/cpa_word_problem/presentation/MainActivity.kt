package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.cpa.cpa_word_problem.presentation.ui.CpaQuizApp
import com.cpa.cpa_word_problem.presentation.ui.rememberCpaQuizAppState
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.core.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.core.android.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    // FIXME: How to intent to original android view based activity?
    // This way doesn't seem nice.
    @Inject
    lateinit var problemDetailNavigator: ProblemDetailNavigator

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val route = intent?.extras?.let { extras ->
            extras.getString(Constants.destination) ?: HomeDestination.route
        } ?: HomeDestination.route

        setContent {
            val appState = rememberCpaQuizAppState(
                navController = rememberNavController(),
                windowSizeClass = calculateWindowSizeClass(activity = this),
                startDestination = route,
            )
            CpaQuizApp(navigator = problemDetailNavigator, appState = appState)
        }
    }
}
