package com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0007H\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J\t\u0010\'\u001a\u00020\u000bH\u00c6\u0003J\t\u0010(\u001a\u00020\rH\u00c6\u0003J\t\u0010)\u001a\u00020\u000fH\u00c6\u0003J\t\u0010*\u001a\u00020\u0011H\u00c6\u0003JY\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u00c6\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010/\u001a\u000200H\u00d6\u0001J\t\u00101\u001a\u000202H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00063"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/ProblemUseCases;", "", "getLocalProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetLocalProblems;", "getWrongProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetWrongProblems;", "searchProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SearchProblems;", "insertWrongProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/InsertWrongProblems;", "deleteWrongProblem", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteWrongProblem;", "deleteAllWrongProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteAllWrongProblems;", "getProblem", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetProblem;", "syncRemoteProblems", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SyncRemoteProblems;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetLocalProblems;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetWrongProblems;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SearchProblems;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/InsertWrongProblems;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteWrongProblem;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteAllWrongProblems;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetProblem;Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SyncRemoteProblems;)V", "getDeleteAllWrongProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteAllWrongProblems;", "getDeleteWrongProblem", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/DeleteWrongProblem;", "getGetLocalProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetLocalProblems;", "getGetProblem", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetProblem;", "getGetWrongProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/GetWrongProblems;", "getInsertWrongProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/InsertWrongProblems;", "getSearchProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SearchProblems;", "getSyncRemoteProblems", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/usecase/problem/SyncRemoteProblems;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
public final class ProblemUseCases {
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetLocalProblems getLocalProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetWrongProblems getWrongProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SearchProblems searchProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.InsertWrongProblems insertWrongProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteWrongProblem deleteWrongProblem = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteAllWrongProblems deleteAllWrongProblems = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetProblem getProblem = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SyncRemoteProblems syncRemoteProblems = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases copy(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetLocalProblems getLocalProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetWrongProblems getWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SearchProblems searchProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.InsertWrongProblems insertWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteWrongProblem deleteWrongProblem, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteAllWrongProblems deleteAllWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetProblem getProblem, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SyncRemoteProblems syncRemoteProblems) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public ProblemUseCases(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetLocalProblems getLocalProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetWrongProblems getWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SearchProblems searchProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.InsertWrongProblems insertWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteWrongProblem deleteWrongProblem, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteAllWrongProblems deleteAllWrongProblems, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetProblem getProblem, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SyncRemoteProblems syncRemoteProblems) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetLocalProblems component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetLocalProblems getGetLocalProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetWrongProblems component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetWrongProblems getGetWrongProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SearchProblems component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SearchProblems getSearchProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.InsertWrongProblems component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.InsertWrongProblems getInsertWrongProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteWrongProblem component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteWrongProblem getDeleteWrongProblem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteAllWrongProblems component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.DeleteAllWrongProblems getDeleteAllWrongProblems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetProblem component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.GetProblem getGetProblem() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SyncRemoteProblems component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.SyncRemoteProblems getSyncRemoteProblems() {
        return null;
    }
}