package com.cpa.cpa_word_problem.di;

import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ApiModule_ProvideNetworkFlipperPluginFactory implements Factory<NetworkFlipperPlugin> {
  @Override
  public NetworkFlipperPlugin get() {
    return provideNetworkFlipperPlugin();
  }

  public static ApiModule_ProvideNetworkFlipperPluginFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NetworkFlipperPlugin provideNetworkFlipperPlugin() {
    return Preconditions.checkNotNullFromProvides(ApiModule.INSTANCE.provideNetworkFlipperPlugin());
  }

  private static final class InstanceHolder {
    private static final ApiModule_ProvideNetworkFlipperPluginFactory INSTANCE = new ApiModule_ProvideNetworkFlipperPluginFactory();
  }
}
