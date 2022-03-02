package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
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
public final class ApiModule_ProvideQuizServiceFactory implements Factory<QuizService> {
  private final Provider<Retrofit> retrofitProvider;

  public ApiModule_ProvideQuizServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public QuizService get() {
    return provideQuizService(retrofitProvider.get());
  }

  public static ApiModule_ProvideQuizServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new ApiModule_ProvideQuizServiceFactory(retrofitProvider);
  }

  public static QuizService provideQuizService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(ApiModule.INSTANCE.provideQuizService(retrofit));
  }
}
