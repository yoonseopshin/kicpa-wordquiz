package com.ysshin.cpaquiz.core.android.di

import com.ysshin.cpaquiz.domain.repository.QuizRepository
import com.ysshin.cpaquiz.domain.repository.UserRepository
import com.ysshin.cpaquiz.domain.usecase.problem.DeleteAllWrongProblems
import com.ysshin.cpaquiz.domain.usecase.problem.DeleteWrongProblem
import com.ysshin.cpaquiz.domain.usecase.problem.GetProblemCount
import com.ysshin.cpaquiz.domain.usecase.problem.GetProblems
import com.ysshin.cpaquiz.domain.usecase.problem.GetSubtypesByQuizType
import com.ysshin.cpaquiz.domain.usecase.problem.GetTotalProblems
import com.ysshin.cpaquiz.domain.usecase.problem.GetWrongProblems
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.problem.SearchProblems
import com.ysshin.cpaquiz.domain.usecase.problem.SyncRemoteProblems
import com.ysshin.cpaquiz.domain.usecase.problem.UpsertWrongProblems
import com.ysshin.cpaquiz.domain.usecase.quiz.GetNextExamDate
import com.ysshin.cpaquiz.domain.usecase.quiz.GetQuizNumber
import com.ysshin.cpaquiz.domain.usecase.quiz.GetShouldRequestInAppReview
import com.ysshin.cpaquiz.domain.usecase.quiz.GetShouldShowInterstitialAd
import com.ysshin.cpaquiz.domain.usecase.quiz.GetSolvedQuiz
import com.ysshin.cpaquiz.domain.usecase.quiz.GetUseTimer
import com.ysshin.cpaquiz.domain.usecase.quiz.IncreaseSolvedQuiz
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.SetQuizNumber
import com.ysshin.cpaquiz.domain.usecase.quiz.SetUseTimer
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
        UpsertWrongProblems(repository),
        DeleteWrongProblem(repository),
        DeleteAllWrongProblems(repository),
        GetProblems(repository),
        SyncRemoteProblems(repository),
        GetProblemCount(repository),
        GetSubtypesByQuizType(repository),
    )

    @Provides
    @Singleton
    fun provideQuizUseCases(repository: QuizRepository) = QuizUseCases(
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
    fun provideSharedUseCases(repository: QuizRepository) = SharedUseCases(
        GetShouldRequestInAppReview(repository),
        GetShouldShowInterstitialAd(repository),
    )

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository) = UserUseCases(
        GetPostNotification(repository),
        GrantPostNotification(repository),
        DenyPostNotification(repository),
    )
}
