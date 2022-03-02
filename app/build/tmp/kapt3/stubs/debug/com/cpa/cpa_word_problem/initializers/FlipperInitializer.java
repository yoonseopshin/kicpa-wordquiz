package com.cpa.cpa_word_problem.initializers;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00010\u000f0\u000eH\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u0010"}, d2 = {"Lcom/cpa/cpa_word_problem/initializers/FlipperInitializer;", "Landroidx/startup/Initializer;", "", "()V", "networkFlipperPlugin", "Lcom/facebook/flipper/plugins/network/NetworkFlipperPlugin;", "getNetworkFlipperPlugin", "()Lcom/facebook/flipper/plugins/network/NetworkFlipperPlugin;", "setNetworkFlipperPlugin", "(Lcom/facebook/flipper/plugins/network/NetworkFlipperPlugin;)V", "create", "context", "Landroid/content/Context;", "dependencies", "", "Ljava/lang/Class;", "app_debug"})
public final class FlipperInitializer implements androidx.startup.Initializer<kotlin.Unit> {
    @javax.inject.Inject()
    public com.facebook.flipper.plugins.network.NetworkFlipperPlugin networkFlipperPlugin;
    
    public FlipperInitializer() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.facebook.flipper.plugins.network.NetworkFlipperPlugin getNetworkFlipperPlugin() {
        return null;
    }
    
    public final void setNetworkFlipperPlugin(@org.jetbrains.annotations.NotNull()
    com.facebook.flipper.plugins.network.NetworkFlipperPlugin p0) {
    }
    
    @java.lang.Override()
    public void create(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.List<java.lang.Class<? extends androidx.startup.Initializer<?>>> dependencies() {
        return null;
    }
}