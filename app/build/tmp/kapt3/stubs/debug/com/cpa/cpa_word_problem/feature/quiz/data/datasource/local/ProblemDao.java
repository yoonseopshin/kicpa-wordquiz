package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u000b0\nH\'J\u001f\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\u001f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;", "", "get", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemEntity;", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "year", "", "pid", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "insert", "", "problems", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "search", "text", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ProblemDao {
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM problems")
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity>> getAll();
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "\n        SELECT * \n        FROM problems \n        WHERE type = :type \n        ORDER BY RANDOM() \n        LIMIT 1")
    public abstract com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity get(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "\n            SELECT *\n            FROM problems\n            WHERE type = :type\n            AND year= :year\n            AND pid= :pid\n            LIMIT 1\n        ")
    public abstract com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity get(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Query(value = "\n        SELECT *\n        FROM problems\n        WHERE description LIKE \'%\' || :text || \'%\'\n        OR sub_description LIKE \'%\' || :text || \'%\'\n        OR questions LIKE \'%\' || :text || \'%\'\n        ")
    public abstract java.lang.Object search(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity>> continuation);
    
    @org.jetbrains.annotations.Nullable()
    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity> problems, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation);
}