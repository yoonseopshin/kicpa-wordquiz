package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\fH\u0016J\u0012\u0010\u000e\u001a\u00020\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\fH\u0002J\b\u0010\u0012\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u0014"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/MainActivity;", "Lcom/cpa/cpa_word_problem/base/BaseActivity;", "()V", "binding", "Lcom/cpa/cpa_word_problem/databinding/ActivityMainBinding;", "viewModel", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/MainViewModel;", "getViewModel", "()Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/MainViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "initView", "", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "parseIntent", "syncRemoteProblems", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class MainActivity extends com.cpa.cpa_word_problem.base.BaseActivity {
    private com.cpa.cpa_word_problem.databinding.ActivityMainBinding binding;
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainActivity.Companion Companion = null;
    
    public MainActivity() {
        super();
    }
    
    private final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initView() {
    }
    
    private final void syncRemoteProblems() {
    }
    
    private final void parseIntent() {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/MainActivity$Companion;", "", "()V", "newIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "destination", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/home/MainTab;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.MainTab destination) {
            return null;
        }
    }
}