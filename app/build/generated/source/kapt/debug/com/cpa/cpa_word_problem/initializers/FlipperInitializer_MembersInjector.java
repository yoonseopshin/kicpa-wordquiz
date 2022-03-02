package com.cpa.cpa_word_problem.initializers;

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class FlipperInitializer_MembersInjector implements MembersInjector<FlipperInitializer> {
  private final Provider<NetworkFlipperPlugin> networkFlipperPluginProvider;

  public FlipperInitializer_MembersInjector(
      Provider<NetworkFlipperPlugin> networkFlipperPluginProvider) {
    this.networkFlipperPluginProvider = networkFlipperPluginProvider;
  }

  public static MembersInjector<FlipperInitializer> create(
      Provider<NetworkFlipperPlugin> networkFlipperPluginProvider) {
    return new FlipperInitializer_MembersInjector(networkFlipperPluginProvider);
  }

  @Override
  public void injectMembers(FlipperInitializer instance) {
    injectNetworkFlipperPlugin(instance, networkFlipperPluginProvider.get());
  }

  @InjectedFieldSignature("com.cpa.cpa_word_problem.initializers.FlipperInitializer.networkFlipperPlugin")
  public static void injectNetworkFlipperPlugin(FlipperInitializer instance,
      NetworkFlipperPlugin networkFlipperPlugin) {
    instance.networkFlipperPlugin = networkFlipperPlugin;
  }
}
