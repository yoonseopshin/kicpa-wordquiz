package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/statistics/QuizStatisticsViewModel;", "Lcom/cpa/cpa_word_problem/base/BaseViewModel;", "problemUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;)V", "wrongProblems", "", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/WrongProblem;", "setWrongProblems", "", "problems", "", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "selected", "", "app_debug"})
public final class QuizStatisticsViewModel extends com.cpa.cpa_word_problem.base.BaseViewModel {
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases = null;
    private final java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem> wrongProblems = null;
    
    @javax.inject.Inject()
    public QuizStatisticsViewModel(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases) {
        super();
    }
    
    public final void setWrongProblems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> problems, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> selected) {
    }
}