package com.ysshin.cpaquiz.core.android.di

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.repository.UserRepository
import com.ysshin.cpaquiz.domain.usecase.problem.*
import com.ysshin.cpaquiz.domain.usecase.quiz.*
import com.ysshin.cpaquiz.domain.usecase.shared.SharedUseCases
import com.ysshin.cpaquiz.domain.usecase.user.DenyPostNotification
import com.ysshin.cpaquiz.domain.usecase.user.GetPostNotification
import com.ysshin.cpaquiz.domain.usecase.user.GrantPostNotification
import com.ysshin.cpaquiz.domain.usecase.user.UserUseCases
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
        GetTotalProblems(repository),
        GetWrongProblems(repository),
        SearchProblems(repository),
        InsertWrongProblems(repository),
        DeleteWrongProblem(repository),
        DeleteAllWrongProblems(repository),
        GetProblems(repository),
        SyncRemoteProblems(repository),
        GetProblemCount(repository),
        GetSubtypesByQuizType(repository),
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
        )

    @Provides
    @Singleton
    fun provideSharedUseCases(repository: QuizRepository) =
        SharedUseCases(
            GetShouldRequestInAppReview(repository),
            GetShouldShowInterstitialAd(repository)
        )

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        GetPostNotification(repository),
        GrantPostNotification(repository),
        DenyPostNotification(repository),
    )
}
