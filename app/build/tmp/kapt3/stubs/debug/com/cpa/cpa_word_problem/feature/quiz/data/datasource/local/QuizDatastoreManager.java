package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B\u0015\b\u0007\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u0019\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000eH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\nR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager;", "", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "(Landroidx/datastore/core/DataStore;)V", "quizNumber", "Lkotlinx/coroutines/flow/Flow;", "", "getQuizNumber", "()Lkotlinx/coroutines/flow/Flow;", "quizNumberKey", "Landroidx/datastore/preferences/core/Preferences$Key;", "useTimer", "", "getUseTimer", "useTimerKey", "setQuizNumber", "", "value", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setTimer", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "Key", "app_debug"})
public final class QuizDatastoreManager {
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager.Companion Companion = null;
    public static final int DEFAULT_QUIZ_NUMBER = 3;
    public static final boolean USE_TIMER = true;
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Integer> quizNumberKey = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> quizNumber = null;
    private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> useTimerKey = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> useTimer = null;
    
    @javax.inject.Inject()
    public QuizDatastoreManager(@org.jetbrains.annotations.NotNull()
    androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> getQuizNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setQuizNumber(int value, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Boolean> getUseTimer() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setTimer(boolean value, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager$Key;", "", "()V", "QUIZ_NUMBER", "", "USE_TIMER", "app_debug"})
    public static final class Key {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager.Key INSTANCE = null;
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String QUIZ_NUMBER = "quiz_number";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String USE_TIMER = "use_timer";
        
        private Key() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager$Companion;", "", "()V", "DEFAULT_QUIZ_NUMBER", "", "USE_TIMER", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}