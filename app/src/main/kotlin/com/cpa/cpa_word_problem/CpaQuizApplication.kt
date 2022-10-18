package com.cpa.cpa_word_problem

import android.app.Application
import com.ysshin.cpaquiz.core.android.initializer.AdMob
import com.ysshin.cpaquiz.core.android.initializer.Timber
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizNavigationActions
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizNavigationActionsProvider
import com.ysshin.cpaquiz.sync.initializer.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CpaQuizApplication : Application(), QuizNavigationActionsProvider {

    private val navigator = Navigator(this)

    override val quizNavActions: QuizNavigationActions = navigator

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
        Timber.initialize(context = this)
        AdMob.initialize(context = this)
    }
}
