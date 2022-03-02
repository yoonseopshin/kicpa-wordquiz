package com.cpa.cpa_word_problem.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\t"}, d2 = {"Lcom/cpa/cpa_word_problem/di/UseCaseModule;", "", "()V", "provideProblemUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;", "repository", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/repository/QuizRepository;", "provideQuizUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/quiz/QuizUseCases;", "app_debug"})
@dagger.Module()
public final class UseCaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.di.UseCaseModule INSTANCE = null;
    
    private UseCaseModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases provideProblemUseCases(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository repository) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases provideQuizUseCases(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository repository) {
        return null;
    }
}