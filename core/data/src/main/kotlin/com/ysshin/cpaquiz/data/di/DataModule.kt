package com.ysshin.cpaquiz.data.di

import com.ysshin.cpaquiz.data.repository.QuizRepositoryImpl
import com.ysshin.cpaquiz.data.util.ConnectivityManagerNetworkMonitor
import com.ysshin.cpaquiz.data.util.NetworkMonitor
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsQuizRepository(quizRepository: QuizRepositoryImpl): QuizRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}
