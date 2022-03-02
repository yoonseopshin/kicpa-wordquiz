package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0015\u001a\u00020\u0016J\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u0010R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\f\u00a8\u0006\u001f"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/main/note/NoteViewModel;", "Lcom/cpa/cpa_word_problem/base/BaseViewModel;", "problemUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;)V", "_searchedProblems", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "problems", "Lkotlinx/coroutines/flow/StateFlow;", "getProblems", "()Lkotlinx/coroutines/flow/StateFlow;", "searchedProblems", "getSearchedProblems", "userInputText", "", "getUserInputText", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "wrongProblems", "getWrongProblems", "deleteAllWrongProblems", "", "deleteWrongProblem", "year", "", "pid", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "search", "text", "app_debug"})
public final class NoteViewModel extends com.cpa.cpa_word_problem.base.BaseViewModel {
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> problems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> wrongProblems = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> _searchedProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> searchedProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> userInputText = null;
    
    @javax.inject.Inject()
    public NoteViewModel(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getWrongProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem>> getSearchedProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getUserInputText() {
        return null;
    }
    
    public final void deleteWrongProblem(int year, int pid, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type) {
    }
    
    public final void deleteAllWrongProblems() {
    }
    
    public final void search(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
}