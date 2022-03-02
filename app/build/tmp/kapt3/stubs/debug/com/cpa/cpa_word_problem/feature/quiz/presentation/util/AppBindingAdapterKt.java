package com.cpa.cpa_word_problem.feature.quiz.presentation.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0007\u001a\u0016\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0007\u001a\u0016\u0010\t\u001a\u00020\u0001*\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007\u001a\u0016\u0010\t\u001a\u00020\u0001*\u00020\r2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007\u001a%\u0010\u000e\u001a\u00020\u0001*\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0007\u00a2\u0006\u0002\u0010\u0012\u001a\u001c\u0010\u0013\u001a\u00020\u0001*\u00020\u00142\u000e\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0016H\u0007\u001a%\u0010\u0017\u001a\u00020\u0001*\u00020\u00142\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019H\u0007\u00a2\u0006\u0002\u0010\u001b\u001a%\u0010\u001c\u001a\u00020\u0001*\u00020\u00142\b\u0010\u001d\u001a\u0004\u0018\u00010\u00102\b\u0010\u001e\u001a\u0004\u0018\u00010\u0010H\u0007\u00a2\u0006\u0002\u0010\u001f\u00a8\u0006 "}, d2 = {"bindByType", "", "Lcom/google/android/material/chip/Chip;", "type", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/QuizType;", "bindDDay", "Landroidx/appcompat/widget/Toolbar;", "nextExamDate", "", "bindProblemDetailMode", "Landroid/widget/RadioGroup;", "mode", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/screen/quiz/ProblemDetailMode;", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "bindProblems", "solved", "", "total", "(Landroidx/appcompat/widget/Toolbar;Ljava/lang/Integer;Ljava/lang/Integer;)V", "bindSubDescription", "Landroid/widget/TextView;", "subDescriptions", "", "bindTimer", "timeMillis", "", "lastLapTime", "(Landroid/widget/TextView;Ljava/lang/Long;Ljava/lang/Long;)V", "bindYearAndPid", "year", "pid", "(Landroid/widget/TextView;Ljava/lang/Integer;Ljava/lang/Integer;)V", "app_debug"})
public final class AppBindingAdapterKt {
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @androidx.databinding.BindingAdapter(value = {"year", "pid"}, requireAll = true)
    public static final void bindYearAndPid(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$bindYearAndPid, @org.jetbrains.annotations.Nullable()
    java.lang.Integer year, @org.jetbrains.annotations.Nullable()
    java.lang.Integer pid) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"sub_descriptions"})
    public static final void bindSubDescription(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$bindSubDescription, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> subDescriptions) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"problem_detail_mode"})
    public static final void bindProblemDetailMode(@org.jetbrains.annotations.NotNull()
    com.google.android.material.floatingactionbutton.FloatingActionButton $this$bindProblemDetailMode, @org.jetbrains.annotations.Nullable()
    com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode mode) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"problem_detail_mode"})
    public static final void bindProblemDetailMode(@org.jetbrains.annotations.NotNull()
    android.widget.RadioGroup $this$bindProblemDetailMode, @org.jetbrains.annotations.Nullable()
    com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode mode) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"problem_type"})
    public static final void bindByType(@org.jetbrains.annotations.NotNull()
    com.google.android.material.chip.Chip $this$bindByType, @org.jetbrains.annotations.Nullable()
    com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType type) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @androidx.databinding.BindingAdapter(value = {"dday"})
    public static final void bindDDay(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar $this$bindDDay, @org.jetbrains.annotations.Nullable()
    java.lang.String nextExamDate) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"timer", "last_lap_time"}, requireAll = true)
    public static final void bindTimer(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$bindTimer, @org.jetbrains.annotations.Nullable()
    java.lang.Long timeMillis, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastLapTime) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"solved", "total"}, requireAll = true)
    public static final void bindProblems(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.widget.Toolbar $this$bindProblems, @org.jetbrains.annotations.Nullable()
    java.lang.Integer solved, @org.jetbrains.annotations.Nullable()
    java.lang.Integer total) {
    }
}