package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0016H\u0016J\u0018\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0016H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\tR(\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R(\u0010\u0012\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0018\u00010\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011\u00a8\u0006\u001f"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/NoteAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/model/UserSolvedProblemModel;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/NoteAdapter$ProblemViewHolder;", "()V", "isShowing", "", "()Z", "setShowing", "(Z)V", "onProblemClick", "Lkotlin/Function1;", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "", "getOnProblemClick", "()Lkotlin/jvm/functions/Function1;", "setOnProblemClick", "(Lkotlin/jvm/functions/Function1;)V", "onProblemLongClick", "getOnProblemLongClick", "setOnProblemLongClick", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ProblemViewHolder", "app_debug"})
public final class NoteAdapter extends androidx.recyclerview.widget.ListAdapter<com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel, com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.NoteAdapter.ProblemViewHolder> {
    private boolean isShowing = true;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> onProblemClick;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> onProblemLongClick;
    
    public NoteAdapter() {
        super(null);
    }
    
    public final boolean isShowing() {
        return false;
    }
    
    public final void setShowing(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function1<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> getOnProblemClick() {
        return null;
    }
    
    public final void setOnProblemClick(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function1<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> getOnProblemLongClick() {
        return null;
    }
    
    public final void setOnProblemLongClick(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> p0) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.NoteAdapter.ProblemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.NoteAdapter.ProblemViewHolder holder, int position) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0016\b\u0002\u0010\u0004\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/NoteAdapter$ProblemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/cpa/cpa_word_problem/databinding/ListItemProblemBinding;", "onProblemClick", "Lkotlin/Function1;", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "", "onProblemLongClick", "(Lcom/cpa/cpa_word_problem/databinding/ListItemProblemBinding;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "bind", "userSolvedProblem", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/model/UserSolvedProblemModel;", "app_debug"})
    public static final class ProblemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.cpa.cpa_word_problem.databinding.ListItemProblemBinding binding = null;
        
        public ProblemViewHolder(@org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.databinding.ListItemProblemBinding binding, @org.jetbrains.annotations.Nullable()
        kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> onProblemClick, @org.jetbrains.annotations.Nullable()
        kotlin.jvm.functions.Function1<? super com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem, kotlin.Unit> onProblemLongClick) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.presentation.model.UserSolvedProblemModel userSolvedProblem) {
        }
    }
}