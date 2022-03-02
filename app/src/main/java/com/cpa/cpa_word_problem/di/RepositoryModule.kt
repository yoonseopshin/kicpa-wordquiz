package com.cpa.cpa_word_problem.di

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService
import com.cpa.cpa_word_problem.feature.quiz.data.repository.QuizRepositoryImpl
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

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
    ): QuizRepository = QuizRepositoryImpl(quizService, problemDao, wrongProblemDao)

}
