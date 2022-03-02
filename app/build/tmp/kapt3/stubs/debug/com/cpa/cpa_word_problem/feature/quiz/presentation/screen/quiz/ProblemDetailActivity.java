package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\fH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002J\u0012\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\fH\u0014J\b\u0010\u0015\u001a\u00020\fH\u0014J\b\u0010\u0016\u001a\u00020\fH\u0014J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\b\u0010\u0019\u001a\u00020\fH\u0002J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u001c"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailActivity;", "Lcom/cpa/cpa_word_problem/base/BaseActivity;", "()V", "binding", "Lcom/cpa/cpa_word_problem/databinding/ActivityProblemDetailBinding;", "viewModel", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailViewModel;", "getViewModel", "()Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "handleEvent", "", "event", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "initView", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "onStart", "parseDetailIntent", "extras", "parseIntent", "parseQuizIntent", "Companion", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class ProblemDetailActivity extends com.cpa.cpa_word_problem.base.BaseActivity {
    private com.cpa.cpa_word_problem.databinding.ActivityProblemDetailBinding binding;
    private final kotlin.Lazy viewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity.Companion Companion = null;
    
    public ProblemDetailActivity() {
        super();
    }
    
    private final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onStart() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    private final void initView() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void handleEvent(com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent event) {
    }
    
    private final void parseIntent() {
    }
    
    private final void parseQuizIntent(android.os.Bundle extras) {
    }
    
    private final void parseDetailIntent(android.os.Bundle extras) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eJ$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailActivity$Companion;", "", "()V", "newIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "mode", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailMode;", "quizType", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "quizNumbers", "", "useTimer", "", "problemModel", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/model/ProblemModel;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode mode, @org.jetbrains.annotations.Nullable()
        com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel problemModel) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent newIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode mode, @org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType quizType, int quizNumbers, boolean useTimer) {
            return null;
        }
    }
}