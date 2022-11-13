package com.cpa.cpa_word_problem

import android.app.Application
import com.ysshin.cpaquiz.core.android.initializer.AdMob
import com.ysshin.cpaquiz.core.android.initializer.Timber
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActions
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActionsProvider
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
import com.ysshin.cpaquiz.sync.initializer.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CpaQuizApplication :
    Application(),
    QuizStartNavigationActionsProvider,
    QuizEndNavigationActionsProvider {

    private val navigator: Navigator by lazy { Navigator() }

    override val quizStartNavActions: QuizStartNavigationActions = navigator

    override val quizEndNavActions: QuizEndNavigationActions = navigator

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
        Timber.initialize(context = this)
        AdMob.initialize(context = this)
    }
}
