package com.cpa.cpa_word_problem.di;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@dagger.hilt.EntryPoint()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0007"}, d2 = {"Lcom/cpa/cpa_word_problem/di/InitializerEntryPoint;", "", "inject", "", "initializer", "Lcom/cpa/cpa_word_problem/initializers/FlipperInitializer;", "Companion", "app_debug"})
public abstract interface InitializerEntryPoint {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.di.InitializerEntryPoint.Companion Companion = null;
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.initializers.FlipperInitializer initializer);
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/cpa/cpa_word_problem/di/InitializerEntryPoint$Companion;", "", "()V", "resolve", "Lcom/cpa/cpa_word_problem/di/InitializerEntryPoint;", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cpa.cpa_word_problem.di.InitializerEntryPoint resolve(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}