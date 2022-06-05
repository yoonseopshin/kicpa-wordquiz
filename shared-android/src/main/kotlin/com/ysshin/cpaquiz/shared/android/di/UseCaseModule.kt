package com.ysshin.cpaquiz.shared.android.di

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.usecase.problem.*
import com.ysshin.cpaquiz.domain.usecase.quiz.*
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
    fun provideProblemUseCases(repository: QuizRepository) = ProblemUseCases(
        GetLocalProblems(repository),
        GetWrongProblems(repository),
        SearchProblems(repository),
        InsertWrongProblems(repository),
        DeleteWrongProblem(repository),
        DeleteAllWrongProblems(repository),
        GetProblem(repository),
        GetProblems(repository),
        SyncRemoteProblems(repository),
        GetProblemCount(repository),
    )

    @Provides
    @Singleton
    fun provideQuizUseCases(repository: QuizRepository) =
        QuizUseCases(
            GetNextExamDate(repository),
            GetQuizNumber(repository),
            GetUseTimer(repository),
            SetQuizNumber(repository),
            SetUseTimer(repository),
            IncreaseSolvedQuiz(repository),
            GetSolvedQuiz(repository),
            GetShouldRequestInAppReview(repository),
        )
}
