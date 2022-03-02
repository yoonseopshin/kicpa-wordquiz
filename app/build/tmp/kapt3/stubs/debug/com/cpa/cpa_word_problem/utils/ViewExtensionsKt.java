package com.cpa.cpa_word_problem.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a$\u0010\u0004\u001a\u00020\u0005*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n\u001a\u001e\u0010\u000b\u001a\u00020\u0005*\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u0007\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\b\b\u0001\u0010\u0011\u001a\u00020\u0007\u001a\u0014\u0010\u0012\u001a\u00020\u0007*\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0005*\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\n\u001a\n\u0010\u0015\u001a\u00020\u0005*\u00020\u0003\u001a\n\u0010\u0016\u001a\u00020\u0005*\u00020\u0003\u001a\u001c\u0010\u0017\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\n\u001aC\u0010\u0019\u001a\u00020\u0005*\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\n2#\b\u0002\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u00050\u001e\u001a\n\u0010!\u001a\u00020\u0005*\u00020\"\u001a\u001c\u0010#\u001a\u00020\u0005*\u00020\u00032\u0006\u0010$\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\n\u001a\n\u0010%\u001a\u00020\u0005*\u00020\u0003\u001a\u0012\u0010&\u001a\u00020\u0005*\u00020\u00032\u0006\u0010\'\u001a\u00020(\u001a\u0012\u0010)\u001a\u00020\u0005*\u00020\u00032\u0006\u0010\'\u001a\u00020(\u00a8\u0006*"}, d2 = {"calculateRectOnScreen", "Landroid/graphics/Rect;", "view", "Landroid/view/View;", "blink", "", "backgroundColor", "", "animColor", "animDuration", "", "collapse", "duration", "height", "color", "Landroid/content/res/ColorStateList;", "Landroid/content/Context;", "resId", "computeDistanceToView", "Landroid/widget/ScrollView;", "expand", "gone", "invisible", "scrollToView", "maxDuration", "setOnThrottleClick", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "interval", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "showKeyboard", "Landroid/widget/EditText;", "slideView", "newHeight", "visible", "visibleOrGone", "isVisible", "", "visibleOrInvisible", "app_debug"})
public final class ViewExtensionsKt {
    
    public static final void expand(@org.jetbrains.annotations.NotNull()
    android.view.View $this$expand, long duration) {
    }
    
    public static final void collapse(@org.jetbrains.annotations.NotNull()
    android.view.View $this$collapse, long duration, int height) {
    }
    
    public static final void slideView(@org.jetbrains.annotations.NotNull()
    android.view.View $this$slideView, int newHeight, long duration) {
    }
    
    public static final void setOnThrottleClick(@org.jetbrains.annotations.NotNull()
    android.view.View $this$setOnThrottleClick, @org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.CoroutineDispatcher dispatcher, long interval, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.view.View, kotlin.Unit> action) {
    }
    
    public static final void visible(@org.jetbrains.annotations.NotNull()
    android.view.View $this$visible) {
    }
    
    public static final void invisible(@org.jetbrains.annotations.NotNull()
    android.view.View $this$invisible) {
    }
    
    public static final void gone(@org.jetbrains.annotations.NotNull()
    android.view.View $this$gone) {
    }
    
    public static final void visibleOrGone(@org.jetbrains.annotations.NotNull()
    android.view.View $this$visibleOrGone, boolean isVisible) {
    }
    
    public static final void visibleOrInvisible(@org.jetbrains.annotations.NotNull()
    android.view.View $this$visibleOrInvisible, boolean isVisible) {
    }
    
    public static final void blink(@org.jetbrains.annotations.NotNull()
    android.view.View $this$blink, int backgroundColor, int animColor, long animDuration) {
    }
    
    private static final android.graphics.Rect calculateRectOnScreen(android.view.View view) {
        return null;
    }
    
    private static final int computeDistanceToView(android.widget.ScrollView $this$computeDistanceToView, android.view.View view) {
        return 0;
    }
    
    public static final void scrollToView(@org.jetbrains.annotations.NotNull()
    android.widget.ScrollView $this$scrollToView, @org.jetbrains.annotations.NotNull()
    android.view.View view, long maxDuration) {
    }
    
    public static final void showKeyboard(@org.jetbrains.annotations.NotNull()
    android.widget.EditText $this$showKeyboard) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final android.content.res.ColorStateList color(@org.jetbrains.annotations.NotNull()
    android.content.Context $this$color, @androidx.annotation.ColorRes()
    int resId) {
        return null;
    }
}