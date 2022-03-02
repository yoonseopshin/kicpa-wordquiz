package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J)\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0011\u0010\n\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rH\'J\u001f\u0010\u0010\u001a\u00020\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;", "", "delete", "", "year", "", "pid", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "(IILcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemEntity;", "insert", "problems", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface WrongProblemDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM wrong_problems")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity>> getAll();
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "\n        DELETE FROM\n        wrong_problems \n        WHERE year = :year\n        AND pid = :pid\n        AND type = :type\n        ")
    public abstract java.lang.Object delete(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "DELETE FROM wrong_problems")
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity> problems, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}