package com.ysshin.cpaquiz.data.di

import com.ysshin.cpaquiz.data.database.ProblemDao
import com.ysshin.cpaquiz.data.database.WrongProblemDao
import com.ysshin.cpaquiz.data.datastore.QuizDatastoreManager
import com.ysshin.cpaquiz.data.network.api.QuizService
import com.ysshin.cpaquiz.data.repository.QuizRepositoryImpl
import com.ysshin.cpaquiz.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.ExperimentalSerializationApi

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideQuizRepository(
        quizService: QuizService,
        problemDao: ProblemDao,
        wrongProblemDao: WrongProblemDao,
        quizDataStoreManager: QuizDatastoreManager,
    ): QuizRepository =
        QuizRepositoryImpl(quizService, problemDao, wrongProblemDao, quizDataStoreManager)
}
