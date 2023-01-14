package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.navigation.compose.rememberNavController
import com.cpa.cpa_word_problem.presentation.ui.CpaQuizApp
import com.cpa.cpa_word_problem.presentation.ui.rememberCpaQuizAppState
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberCpaQuizAppState(
                navController = rememberNavController(),
                windowSizeClass = calculateWindowSizeClass(activity = this),
                startDestination = HomeDestination.route,
            )
            CpaQuizApp(appState = appState)
        }
    }
}
