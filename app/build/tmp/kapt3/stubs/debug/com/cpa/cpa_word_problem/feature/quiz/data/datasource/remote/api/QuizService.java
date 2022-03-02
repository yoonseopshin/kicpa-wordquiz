package com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\b"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/api/QuizService;", "", "getCpaProblems", "", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/ProblemResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCpaScheduledDate", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/remote/ScheduledDateResponse;", "app_debug"})
public abstract interface QuizService {
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "cpa-data.json")
    public abstract java.lang.Object getCpaProblems(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ProblemResponse>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @retrofit2.http.GET(value = "cpa-scheduled.json")
    public abstract java.lang.Object getCpaScheduledDate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.ScheduledDateResponse>> continuation);
}