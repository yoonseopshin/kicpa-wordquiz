package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@androidx.room.TypeConverters(value = {com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.Converters.class})
@androidx.room.Database(entities = {com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemEntity.class, com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemEntity.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "problemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;", "wrongProblemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;", "Companion", "app_debug"})
@kotlinx.serialization.ExperimentalSerializationApi()
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase.Companion Companion = null;
    @kotlin.jvm.Volatile()
    private static volatile com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase instance;
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao problemDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao wrongProblemDao();
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppDatabase$Companion;", "", "()V", "instance", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppDatabase;", "buildDatabase", "context", "Landroid/content/Context;", "getInstance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase buildDatabase(android.content.Context context) {
            return null;
        }
    }
}