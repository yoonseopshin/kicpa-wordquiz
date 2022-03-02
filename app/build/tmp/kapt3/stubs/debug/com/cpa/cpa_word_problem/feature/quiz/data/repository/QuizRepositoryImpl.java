package com.cpa.cpa_word_problem.feature.quiz.data.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ)\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J)\u0010\u0013\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00180\u0017H\u0016J\u0011\u0010\u0019\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00180\u0017H\u0016J\u001f\u0010\u001c\u001a\u00020\n2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u0018H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00140\u00182\u0006\u0010!\u001a\u00020\u001aH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"J\u0011\u0010#\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/repository/QuizRepositoryImpl;", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/repository/QuizRepository;", "quizService", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/api/QuizService;", "problemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;", "wrongProblemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;", "(Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/api/QuizService;Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;)V", "deleteAllWrongProblems", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWrongProblem", "year", "", "pid", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "(IILcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalProblem", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalProblems", "Lkotlinx/coroutines/flow/Flow;", "", "getNextExamDate", "", "getWrongProblems", "insertWrongProblems", "wrongProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/WrongProblem;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchProblems", "text", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncRemoteProblems", "app_debug"})
@kotlinx.serialization.ExperimentalSerializationApi()
public final class QuizRepositoryImpl implements com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository {
    private final com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService quizService = null;
    private final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao problemDao = null;
    private final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao wrongProblemDao = null;
    
    @javax.inject.Inject()
    public QuizRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService quizService, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao problemDao, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao wrongProblemDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getLocalProblem(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getLocalProblem(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getLocalProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getWrongProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object searchProblems(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object insertWrongProblems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem> wrongProblems, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object deleteWrongProblem(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object deleteAllWrongProblems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object syncRemoteProblems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getNextExamDate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> continuation) {
        return null;
    }
}