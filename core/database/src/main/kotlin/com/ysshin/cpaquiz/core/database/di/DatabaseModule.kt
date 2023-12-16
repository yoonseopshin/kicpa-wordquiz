package com.ysshin.cpaquiz.core.database.di

import android.content.Context
import com.ysshin.cpaquiz.core.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideProblemDao(appDatabase: AppDatabase) = appDatabase.problemDao()

    @Singleton
    @Provides
    fun provideWrongProblemDao(appDatabase: AppDatabase) = appDatabase.wrongProblemDao()
}
