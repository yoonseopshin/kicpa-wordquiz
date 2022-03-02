package com.cpa.cpa_word_problem.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/cpa/cpa_word_problem/di/RepositoryModule;", "", "()V", "provideQuizRepository", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/repository/QuizRepository;", "quizService", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/api/QuizService;", "problemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;", "wrongProblemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;", "app_debug"})
@dagger.Module()
public final class RepositoryModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.di.RepositoryModule INSTANCE = null;
    
    private RepositoryModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    @javax.inject.Singleton()
    @kotlinx.serialization.ExperimentalSerializationApi()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository provideQuizRepository(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService quizService, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao problemDao, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao wrongProblemDao) {
        return null;
    }
}