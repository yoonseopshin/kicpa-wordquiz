package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u000eJ\u000e\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0012R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010\u00a8\u0006\u0019"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/home/HomeViewModel;", "Lcom/cpa/cpa_word_problem/base/BaseViewModel;", "quizUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/quiz/QuizUseCases;", "quizDatastoreManager", "Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/quiz/QuizUseCases;Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/QuizDatastoreManager;)V", "nextExamDate", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getNextExamDate", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "quizNumber", "Lkotlinx/coroutines/flow/StateFlow;", "", "getQuizNumber", "()Lkotlinx/coroutines/flow/StateFlow;", "useTimer", "", "getUseTimer", "requestNextExamDate", "", "setQuizNumber", "value", "setTimer", "app_debug"})
public final class HomeViewModel extends com.cpa.cpa_word_problem.base.BaseViewModel {
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases quizUseCases = null;
    private final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager quizDatastoreManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> nextExamDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> quizNumber = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> useTimer = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases quizUseCases, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager quizDatastoreManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getNextExamDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getQuizNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getUseTimer() {
        return null;
    }
    
    public final void requestNextExamDate() {
    }
    
    public final void setQuizNumber(int value) {
    }
    
    public final void setTimer(boolean value) {
    }
}