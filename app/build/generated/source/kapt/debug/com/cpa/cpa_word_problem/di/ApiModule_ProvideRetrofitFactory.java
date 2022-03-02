package com.cpa.cpa_word_problem.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ApiModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  private final Provider<String> urlProvider;

  public ApiModule_ProvideRetrofitFactory(Provider<OkHttpClient> okHttpClientProvider,
      Provider<String> urlProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
    this.urlProvider = urlProvider;
  }

  @Override
  public Retrofit get() {
    return provideRetrofit(okHttpClientProvider.get(), urlProvider.get());
  }

  public static ApiModule_ProvideRetrofitFactory create(Provider<OkHttpClient> okHttpClientProvider,
      Provider<String> urlProvider) {
    return new ApiModule_ProvideRetrofitFactory(okHttpClientProvider, urlProvider);
  }

  public static Retrofit provideRetrofit(OkHttpClient okHttpClient, String url) {
    return Preconditions.checkNotNullFromProvides(ApiModule.INSTANCE.provideRetrofit(okHttpClient, url));
  }
}
