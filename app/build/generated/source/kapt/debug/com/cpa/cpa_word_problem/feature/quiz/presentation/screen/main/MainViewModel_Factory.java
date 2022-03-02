package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main;

import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<ProblemUseCases> problemUseCasesProvider;

  public MainViewModel_Factory(Provider<ProblemUseCases> problemUseCasesProvider) {
    this.problemUseCasesProvider = problemUseCasesProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(problemUseCasesProvider.get());
  }

  public static MainViewModel_Factory create(Provider<ProblemUseCases> problemUseCasesProvider) {
    return new MainViewModel_Factory(problemUseCasesProvider);
  }

  public static MainViewModel newInstance(ProblemUseCases problemUseCases) {
    return new MainViewModel(problemUseCases);
  }
}
