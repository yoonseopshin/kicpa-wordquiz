package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001 B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0018H\u0016J\u0018\u0010\u001c\u001a\u00020\u00022\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\"\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016\u00a8\u0006!"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/CommonNoteHeaderAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/CommonNoteHeaderAdapter$ItemViewHolder;", "()V", "headerTitle", "", "getHeaderTitle", "()Ljava/lang/String;", "setHeaderTitle", "(Ljava/lang/String;)V", "value", "", "isShowing", "()Z", "setShowing", "(Z)V", "onHeaderLongClick", "Lkotlin/Function0;", "", "getOnHeaderLongClick", "()Lkotlin/jvm/functions/Function0;", "setOnHeaderLongClick", "(Lkotlin/jvm/functions/Function0;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ItemViewHolder", "app_debug"})
public final class CommonNoteHeaderAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.CommonNoteHeaderAdapter.ItemViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String headerTitle = "";
    private boolean isShowing = true;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onHeaderLongClick;
    
    public CommonNoteHeaderAdapter() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getHeaderTitle() {
        return null;
    }
    
    public final void setHeaderTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean isShowing() {
        return false;
    }
    
    public final void setShowing(boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnHeaderLongClick() {
        return null;
    }
    
    public final void setOnHeaderLongClick(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.CommonNoteHeaderAdapter.ItemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.CommonNoteHeaderAdapter.ItemViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/CommonNoteHeaderAdapter$ItemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/cpa/cpa_word_problem/databinding/LayoutCommonNoteHeaderBinding;", "onHeaderLongClick", "Lkotlin/Function0;", "", "(Lcom/cpa/cpa_word_problem/databinding/LayoutCommonNoteHeaderBinding;Lkotlin/jvm/functions/Function0;)V", "bind", "title", "", "app_debug"})
    public static final class ItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBinding binding = null;
        
        public ItemViewHolder(@org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.databinding.LayoutCommonNoteHeaderBinding binding, @org.jetbrains.annotations.Nullable()
        kotlin.jvm.functions.Function0<kotlin.Unit> onHeaderLongClick) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        java.lang.String title) {
        }
    }
}