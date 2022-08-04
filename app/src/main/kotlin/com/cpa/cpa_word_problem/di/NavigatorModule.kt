package com.cpa.cpa_word_problem.di

import android.content.Context
import android.content.Intent
import com.cpa.cpa_word_problem.presentation.MainActivity
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.NoteDestination
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.SettingsDestination
import com.ysshin.cpaquiz.shared.android.bridge.MainScreenNavigator
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.android.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {

    @Provides
    @Singleton
    fun provideMainScreenNavigator() = object : MainScreenNavigator {
        override fun homeScreenIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, HomeDestination.route)
                flags?.let { addFlags(it) }
            }

        override fun noteScreenIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, NoteDestination.route)
                flags?.let { addFlags(it) }
            }

        override fun settingsScreenIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, SettingsDestination.route)
                flags?.let { addFlags(it) }
            }
    }

    @Provides
    @Singleton
    fun provideProblemDetailNavigator() = object : ProblemDetailNavigator {
        override fun quizIntent(
            context: Context,
            quizType: QuizType,
            quizNumbers: Int,
            useTimer: Boolean,
        ) = ProblemDetailActivity.newIntent(
            context = context,
            mode = ProblemDetailMode.Quiz,
            quizType = quizType,
            quizNumbers = quizNumbers,
            useTimer = useTimer
        )
    }
}
