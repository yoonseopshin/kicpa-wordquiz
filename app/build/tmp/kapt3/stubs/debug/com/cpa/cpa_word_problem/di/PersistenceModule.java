package com.cpa.cpa_word_problem.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0018\u0010\r\u001a\u00020\u000e2\u000e\b\u0001\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/cpa/cpa_word_problem/di/PersistenceModule;", "", "()V", "provideAppDatabase", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppDatabase;", "context", "Landroid/content/Context;", "provideProblemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/ProblemDao;", "appDatabase", "provideQuizDataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "provideQuizDataStoreManager", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager;", "dataStore", "provideWrongProblemDao", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/WrongProblemDao;", "app_debug"})
@dagger.Module()
@kotlinx.serialization.ExperimentalSerializationApi()
public final class PersistenceModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.di.PersistenceModule INSTANCE = null;
    
    private PersistenceModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    @javax.inject.Singleton()
    public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase provideAppDatabase(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    @javax.inject.Singleton()
    public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao provideProblemDao(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase appDatabase) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    @javax.inject.Singleton()
    public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao provideWrongProblemDao(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase appDatabase) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "QuizDataStore")
    @dagger.Provides()
    @javax.inject.Singleton()
    public final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> provideQuizDataStore(@org.jetbrains.annotations.NotNull()
    @dagger.hilt.android.qualifiers.ApplicationContext()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    @javax.inject.Singleton()
    public final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager provideQuizDataStoreManager(@org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "QuizDataStore")
    androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore) {
        return null;
    }
}