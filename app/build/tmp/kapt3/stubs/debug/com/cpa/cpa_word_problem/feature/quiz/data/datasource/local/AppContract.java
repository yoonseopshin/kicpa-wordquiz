package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002\u0005\u0006B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppContract;", "", "()V", "DATABASE_NAME", "", "Problem", "WrongProblem", "app_debug"})
public final class AppContract {
    @org.jetbrains.annotations.NotNull()
    public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "cpaquiz_db";
    
    private AppContract() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppContract$Problem;", "", "()V", "ANSWER", "", "DESCRIPTION", "PID", "QUESTIONS", "SUB_DESCRIPTION", "TABLE_NAME", "TYPE", "YEAR", "app_debug"})
    public static final class Problem {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.Problem INSTANCE = null;
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String TABLE_NAME = "problems";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String PID = "pid";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String YEAR = "year";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String DESCRIPTION = "description";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String SUB_DESCRIPTION = "sub_description";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String QUESTIONS = "questions";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ANSWER = "answer";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String TYPE = "type";
        
        private Problem() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/data/datasource/local/AppContract$WrongProblem;", "", "()V", "CREATED_AT", "", "TABLE_NAME", "app_debug"})
    public static final class WrongProblem {
        @org.jetbrains.annotations.NotNull()
        public static final com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppContract.WrongProblem INSTANCE = null;
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String TABLE_NAME = "wrong_problems";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String CREATED_AT = "created_at";
        
        private WrongProblem() {
            super();
        }
    }
}