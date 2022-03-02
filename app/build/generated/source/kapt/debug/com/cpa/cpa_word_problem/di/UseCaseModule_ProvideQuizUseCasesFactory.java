package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository;
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class UseCaseModule_ProvideQuizUseCasesFactory implements Factory<QuizUseCases> {
  private final Provider<QuizRepository> repositoryProvider;

  public UseCaseModule_ProvideQuizUseCasesFactory(Provider<QuizRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public QuizUseCases get() {
    return provideQuizUseCases(repositoryProvider.get());
  }

  public static UseCaseModule_ProvideQuizUseCasesFactory create(
      Provider<QuizRepository> repositoryProvider) {
    return new UseCaseModule_ProvideQuizUseCasesFactory(repositoryProvider);
  }

  public static QuizUseCases provideQuizUseCases(QuizRepository repository) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideQuizUseCases(repository));
  }
}
