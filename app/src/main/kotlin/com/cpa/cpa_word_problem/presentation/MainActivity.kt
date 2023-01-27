package com.cpa.cpa_word_problem.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.cpa.cpa_word_problem.presentation.ui.CpaQuizApp
import com.cpa.cpa_word_problem.presentation.ui.rememberCpaQuizAppState
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.data.util.NetworkMonitor
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberCpaQuizAppState(
                windowSizeClass = calculateWindowSizeClass(activity = this),
                networkMonitor = networkMonitor,
                startDestination = HomeDestination.route,
            )
            CpaQuizApp(appState = appState)
        }
    }
}
