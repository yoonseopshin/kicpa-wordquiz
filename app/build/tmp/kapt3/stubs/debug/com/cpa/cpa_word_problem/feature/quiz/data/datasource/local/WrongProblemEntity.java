package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@androidx.room.Entity(tableName = "wrong_problems", primaryKeys = {"pid", "year", "type"})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u0003H\u0016J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r\u00a8\u0006\u001c"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemEntity;", "", "pid", "", "year", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "createdAt", "", "(IILcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;J)V", "getCreatedAt", "()J", "getPid", "()I", "getType", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "getYear", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class WrongProblemEntity {
    @androidx.room.ColumnInfo(name = "pid")
    private final int pid = 0;
    @androidx.room.ColumnInfo(name = "year")
    private final int year = 0;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "type")
    private final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type = null;
    @androidx.room.ColumnInfo(name = "created_at")
    private final long createdAt = 0L;
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity copy(int pid, int year, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, long createdAt) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public WrongProblemEntity() {
        super();
    }
    
    public WrongProblemEntity(int pid, int year, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type, long createdAt) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getPid() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getYear() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType getType() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
}