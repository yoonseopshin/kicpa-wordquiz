package com.ysshin.cpaquiz.data.database.di

import android.content.Context
import com.ysshin.cpaquiz.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

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
}
