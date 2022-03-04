package com.cpa.cpa_word_problem.di

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.*
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.GetNextExamDate
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProblemUseCases(repository: QuizRepository) =
        ProblemUseCases(
            GetLocalProblems(repository),
            GetWrongProblems(repository),
            SearchProblems(repository),
            InsertWrongProblems(repository),
            DeleteWrongProblem(repository),
            DeleteAllWrongProblems(repository),
            GetProblem(repository),
            SyncRemoteProblems(repository),
            GetProblemCount(repository),
        )

    @Provides
    @Singleton
    fun provideQuizUseCases(repository: QuizRepository) =
        QuizUseCases(GetNextExamDate(repository))

}
