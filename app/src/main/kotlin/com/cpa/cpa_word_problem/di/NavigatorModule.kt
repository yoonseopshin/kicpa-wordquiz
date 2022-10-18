package com.cpa.cpa_word_problem.di

import android.content.Context
import android.content.Intent
import com.cpa.cpa_word_problem.presentation.MainActivity
import com.ysshin.cpaquiz.core.android.bridge.MainScreenNavigator
import com.ysshin.cpaquiz.core.android.util.Constants
import com.ysshin.cpaquiz.feature.home.presentation.navigation.HomeDestination
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.NoteDestination
import com.ysshin.cpaquiz.feature.settings.presentation.navigation.SettingsDestination
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
}
