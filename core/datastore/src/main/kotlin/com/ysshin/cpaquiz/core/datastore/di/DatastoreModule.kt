package com.ysshin.cpaquiz.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ysshin.cpaquiz.core.datastore.AppPreferenceDataSource
import com.ysshin.cpaquiz.core.datastore.QuizDatastoreManager
import com.ysshin.cpaquiz.core.datastore.appDataStore
import com.ysshin.cpaquiz.core.datastore.quizDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Singleton
    @Provides
    @Named("QuizDataStore")
    fun provideQuizDataStore(@ApplicationContext context: Context) = context.quizDataStore

    @Singleton
    @Provides
    fun provideQuizDataStoreManager(@Named("QuizDataStore") dataStore: DataStore<Preferences>) =
        QuizDatastoreManager(dataStore)

    @Singleton
    @Provides
    @Named("AppDataStore")
    fun provideAppDataStore(@ApplicationContext context: Context) = context.appDataStore

    @Singleton
    @Provides
    fun provideAppPreferenceDataSource(@Named("AppDataStore") dataStore: DataStore<Preferences>) =
        AppPreferenceDataSource(dataStore)
}
