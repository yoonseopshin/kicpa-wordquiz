package com.cpa.cpa_word_problem.feature.quiz.domain.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ)\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u0010H&J\u0011\u0010\u0012\u001a\u00020\u0013H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u0010H&J\u001f\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018J\u001f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u001a\u001a\u00020\u0013H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001bJ\u0011\u0010\u001c\u001a\u00020\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u001d"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/domain/repository/QuizRepository;", "", "deleteAllWrongProblems", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWrongProblem", "year", "", "pid", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "(IILcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalProblem", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLocalProblems", "Lkotlinx/coroutines/flow/Flow;", "", "getNextExamDate", "", "getWrongProblems", "insertWrongProblems", "wrongProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/WrongProblem;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchProblems", "text", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncRemoteProblems", "app_debug"})
public abstract interface QuizRepository {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLocalProblem(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLocalProblem(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> continuation);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getLocalProblems();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getWrongProblems();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchProblems(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertWrongProblems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem> wrongProblems, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteWrongProblem(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAllWrongProblems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object syncRemoteProblems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNextExamDate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> continuation);
}