package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010C\u001a\u00020DJ\u0006\u0010E\u001a\u00020DJ\u000e\u0010F\u001a\u00020D2\u0006\u0010G\u001a\u00020\fJ\u0018\u0010H\u001a\u00020D2\u0010\u0010I\u001a\f\u0012\u0004\u0012\u00020D0Jj\u0002`KJ\u0018\u0010L\u001a\u00020D2\u0010\u0010M\u001a\f\u0012\u0004\u0012\u00020D0Jj\u0002`KJ\u0006\u0010N\u001a\u00020DJ\u0006\u0010O\u001a\u00020DJ\u0018\u0010P\u001a\u00020D2\u0010\u0010Q\u001a\f\u0012\u0004\u0012\u00020D0Jj\u0002`KJ\u0006\u0010R\u001a\u00020DJ\b\u0010S\u001a\u00020DH\u0002J\u0006\u0010T\u001a\u00020DJ\u000e\u0010U\u001a\u00020D2\u0006\u0010V\u001a\u00020\fJ\b\u0010W\u001a\u00020DH\u0002J\b\u0010X\u001a\u00020DH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00070%8F\u00a2\u0006\u0006\u001a\u0004\b&\u0010\'R\u0017\u0010(\u001a\b\u0012\u0004\u0012\u00020\n0)\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u0019R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\f0%8F\u00a2\u0006\u0006\u001a\u0004\b0\u0010\'R\u0017\u00101\u001a\b\u0012\u0004\u0012\u00020\f02\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0017\u00105\u001a\b\u0012\u0004\u0012\u00020\u00160\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u0019R\u001c\u00107\u001a\u0004\u0018\u000108X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u0017\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00160%8F\u00a2\u0006\u0006\u001a\u0004\b>\u0010\'R\u0017\u0010?\u001a\b\u0012\u0004\u0012\u00020\f02\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00104R\u0017\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010\u0019\u00a8\u0006Y"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailViewModel;", "Lcom/cpa/cpa_word_problem/base/BaseViewModel;", "problemUseCases", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;)V", "_problems", "", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "_quizEvent", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "_selected", "", "_solvedProblemNumbers", "Lkotlinx/coroutines/flow/MutableStateFlow;", "_totalProblemNumbers", "isTimerActive", "", "()Z", "setTimerActive", "(Z)V", "laps", "", "lastLapTime", "getLastLapTime", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "lastTimestamp", "getLastTimestamp", "()J", "setLastTimestamp", "(J)V", "mode", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailMode;", "getMode", "problem", "getProblem", "problems", "", "getProblems", "()Ljava/util/List;", "quizEvent", "Lkotlinx/coroutines/flow/SharedFlow;", "getQuizEvent", "()Lkotlinx/coroutines/flow/SharedFlow;", "quizType", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "getQuizType", "selected", "getSelected", "solvedProblemNumbers", "Lkotlinx/coroutines/flow/StateFlow;", "getSolvedProblemNumbers", "()Lkotlinx/coroutines/flow/StateFlow;", "timeMillis", "getTimeMillis", "timerJob", "Lkotlinx/coroutines/Job;", "getTimerJob", "()Lkotlinx/coroutines/Job;", "setTimerJob", "(Lkotlinx/coroutines/Job;)V", "timesPerProblem", "getTimesPerProblem", "totalProblemNumbers", "getTotalProblemNumbers", "useTimer", "getUseTimer", "calculate", "", "next", "onCalculating", "selectedIndexByUser", "onEnd", "onEndResult", "Lkotlin/Function0;", "Lcom/cpa/cpa_word_problem/utils/Action;", "onNext", "onNextResult", "onPause", "onResume", "onStart", "onStartResult", "pause", "pauseTimer", "resume", "start", "quizNumbers", "startTimer", "stopTimer", "app_debug"})
public final class ProblemDetailViewModel extends com.cpa.cpa_word_problem.base.BaseViewModel {
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> useTimer = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    private boolean isTimerActive = false;
    private long lastTimestamp = 0L;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> timeMillis = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> lastLapTime = null;
    private final java.util.List<java.lang.Long> laps = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode> mode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> problem = null;
    private final java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> _problems = null;
    private final java.util.List<java.lang.Integer> _selected = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType> quizType = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _totalProblemNumbers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> totalProblemNumbers = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _solvedProblemNumbers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> solvedProblemNumbers = null;
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent> _quizEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent> quizEvent = null;
    
    @javax.inject.Inject()
    public ProblemDetailViewModel(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases problemUseCases) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> getUseTimer() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlinx.coroutines.Job getTimerJob() {
        return null;
    }
    
    public final void setTimerJob(@org.jetbrains.annotations.Nullable()
    kotlinx.coroutines.Job p0) {
    }
    
    public final boolean isTimerActive() {
        return false;
    }
    
    public final void setTimerActive(boolean p0) {
    }
    
    public final long getLastTimestamp() {
        return 0L;
    }
    
    public final void setLastTimestamp(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> getTimeMillis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> getLastLapTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Long> getTimesPerProblem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode> getMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> getProblem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> getProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getSelected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType> getQuizType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getTotalProblemNumbers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getSolvedProblemNumbers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent> getQuizEvent() {
        return null;
    }
    
    public final void start(int quizNumbers) {
    }
    
    public final void onStart(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStartResult) {
    }
    
    public final void onPause() {
    }
    
    public final void onResume() {
    }
    
    private final void startTimer() {
    }
    
    public final void resume() {
    }
    
    public final void pause() {
    }
    
    private final void pauseTimer() {
    }
    
    private final void stopTimer() {
    }
    
    public final void calculate() {
    }
    
    public final void onCalculating(int selectedIndexByUser) {
    }
    
    public final void next() {
    }
    
    public final void onNext(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextResult) {
    }
    
    public final void onEnd(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEndResult) {
    }
}