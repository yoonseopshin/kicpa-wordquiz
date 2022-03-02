package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository;
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases;
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
public final class UseCaseModule_ProvideProblemUseCasesFactory implements Factory<ProblemUseCases> {
  private final Provider<QuizRepository> repositoryProvider;

  public UseCaseModule_ProvideProblemUseCasesFactory(Provider<QuizRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ProblemUseCases get() {
    return provideProblemUseCases(repositoryProvider.get());
  }

  public static UseCaseModule_ProvideProblemUseCasesFactory create(
      Provider<QuizRepository> repositoryProvider) {
    return new UseCaseModule_ProvideProblemUseCasesFactory(repositoryProvider);
  }

  public static ProblemUseCases provideProblemUseCases(QuizRepository repository) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideProblemUseCases(repository));
  }
}
