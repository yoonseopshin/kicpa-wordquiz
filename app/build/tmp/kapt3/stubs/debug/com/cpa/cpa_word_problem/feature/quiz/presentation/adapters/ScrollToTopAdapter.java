package com.cpa.cpa_word_problem.feature.quiz.presentation.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0019B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0011H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0011H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0004\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/ScrollToTopAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/ScrollToTopAdapter$ItemViewHolder;", "()V", "isShowing", "", "()Z", "setShowing", "(Z)V", "onScrollToTopClick", "Lkotlin/Function0;", "", "getOnScrollToTopClick", "()Lkotlin/jvm/functions/Function0;", "setOnScrollToTopClick", "(Lkotlin/jvm/functions/Function0;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ItemViewHolder", "app_debug"})
public final class ScrollToTopAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.ScrollToTopAdapter.ItemViewHolder> {
    private boolean isShowing = true;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onScrollToTopClick;
    
    public ScrollToTopAdapter() {
        super();
    }
    
    public final boolean isShowing() {
        return false;
    }
    
    public final void setShowing(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final kotlin.jvm.functions.Function0<kotlin.Unit> getOnScrollToTopClick() {
        return null;
    }
    
    public final void setOnScrollToTopClick(@org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function0<kotlin.Unit> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.ScrollToTopAdapter.ItemViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.cpa.cpa_word_problem.feature.quiz.presentation.adapters.ScrollToTopAdapter.ItemViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\b\u0002\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0007J\u0006\u0010\b\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/presentation/adapters/ScrollToTopAdapter$ItemViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/cpa/cpa_word_problem/databinding/LayoutScrollToTopBinding;", "onScrollToTopClick", "Lkotlin/Function0;", "", "(Lcom/cpa/cpa_word_problem/databinding/LayoutScrollToTopBinding;Lkotlin/jvm/functions/Function0;)V", "bind", "app_debug"})
    public static final class ItemViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.cpa.cpa_word_problem.databinding.LayoutScrollToTopBinding binding = null;
        
        public ItemViewHolder(@org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.databinding.LayoutScrollToTopBinding binding, @org.jetbrains.annotations.Nullable()
        kotlin.jvm.functions.Function0<kotlin.Unit> onScrollToTopClick) {
            super(null);
        }
        
        public final void bind() {
        }
    }
}