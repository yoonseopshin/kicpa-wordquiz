package com.cpa.cpa_word_problem.di

import android.content.Context
import android.content.Intent
import com.cpa.cpa_word_problem.presentation.MainActivity
import com.cpa.cpa_word_problem.presentation.MainTab
import com.ysshin.cpaquiz.shared.android.bridge.MainTabNavigator
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
    fun provideMainTabNavigator() = object : MainTabNavigator {
        override fun homeTabIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, MainTab.Home)
                flags?.let { addFlags(it) }
            }

        override fun noteTabIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, MainTab.Note)
                flags?.let { addFlags(it) }
            }

        override fun settingsTabIntent(context: Context, flags: Int?) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(Constants.destination, MainTab.Settings)
                flags?.let { addFlags(it) }
            }
    }
}
