package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\b\u0003\u0004\u0005\u0006\u0007\b\t\nB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\b\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "", "()V", "Calculating", "Correct", "Ended", "Incorrect", "Next", "Paused", "Resumed", "Started", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Started;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Paused;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Resumed;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Calculating;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Correct;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Incorrect;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Next;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Ended;", "app_debug"})
public abstract class QuizEvent {
    
    private QuizEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Started;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Started extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Started INSTANCE = null;
        
        private Started() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Paused;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Paused extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Paused INSTANCE = null;
        
        private Paused() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Resumed;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Resumed extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Resumed INSTANCE = null;
        
        private Resumed() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Calculating;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Calculating extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Calculating INSTANCE = null;
        
        private Calculating() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Correct;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Correct extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Correct INSTANCE = null;
        
        private Correct() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Incorrect;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Incorrect extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Incorrect INSTANCE = null;
        
        private Incorrect() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Next;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Next extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Next INSTANCE = null;
        
        private Next() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent$Ended;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/QuizEvent;", "()V", "app_debug"})
    public static final class Ended extends com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.QuizEvent.Ended INSTANCE = null;
        
        private Ended() {
            super();
        }
    }
}