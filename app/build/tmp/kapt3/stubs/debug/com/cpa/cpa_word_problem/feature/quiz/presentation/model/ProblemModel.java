package com.cpa.cpa_word_problem.feature.quiz.presentation.model;

import java.lang.System;

@kotlinx.parcelize.Parcelize()
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BW\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\fH\u00c6\u0003J[\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u00d6\u0003J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0006H\u00d6\u0001J\u0019\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006-"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/model/ProblemModel;", "Landroid/os/Parcelable;", "year", "", "pid", "description", "", "subDescriptions", "", "questions", "answer", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "(IILjava/lang/String;Ljava/util/List;Ljava/util/List;ILcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;)V", "getAnswer", "()I", "getDescription", "()Ljava/lang/String;", "getPid", "getQuestions", "()Ljava/util/List;", "getSubDescriptions", "getType", "()Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "getYear", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "describeContents", "equals", "", "other", "", "hashCode", "toString", "writeToParcel", "", "parcel", "Landroid/os/Parcel;", "flags", "app_debug"})
public final class ProblemModel implements android.os.Parcelable {
    private final int year = 0;
    private final int pid = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> subDescriptions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> questions = null;
    private final int answer = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type = null;
    public static final android.os.Parcelable.Creator<com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel> CREATOR = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel copy(int year, int pid, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> subDescriptions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> questions, int answer, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type) {
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
    
    public ProblemModel() {
        super();
    }
    
    public ProblemModel(int year, int pid, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> subDescriptions, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> questions, int answer, @org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int getYear() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int getPid() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSubDescriptions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getQuestions() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getAnswer() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType getType() {
        return null;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 3)
    public static final class Creator implements android.os.Parcelable.Creator<com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel> {
        
        public Creator() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public final com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel in) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public final com.cpa.cpa_word_problem.feature.quiz.presentation.model.ProblemModel[] newArray(int size) {
            return null;
        }
    }
}