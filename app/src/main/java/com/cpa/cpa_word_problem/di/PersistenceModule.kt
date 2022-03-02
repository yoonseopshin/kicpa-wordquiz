package com.cpa.cpa_word_problem.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.quizDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideProblemDao(appDatabase: AppDatabase) = appDatabase.problemDao()

    @Singleton
    @Provides
    fun provideWrongProblemDao(appDatabase: AppDatabase) = appDatabase.wrongProblemDao()

    @Singleton
    @Provides
    @Named("QuizDataStore")
    fun provideQuizDataStore(@ApplicationContext context: Context) = context.quizDataStore

    @Singleton
    @Provides
    fun provideQuizDataStoreManager(@Named("QuizDataStore") dataStore: DataStore<Preferences>) =
        QuizDatastoreManager(dataStore)

}