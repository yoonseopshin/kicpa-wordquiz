package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz;

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
public final class ProblemDetailViewModel_Factory implements Factory<ProblemDetailViewModel> {
  private final Provider<ProblemUseCases> problemUseCasesProvider;

  public ProblemDetailViewModel_Factory(Provider<ProblemUseCases> problemUseCasesProvider) {
    this.problemUseCasesProvider = problemUseCasesProvider;
  }

  @Override
  public ProblemDetailViewModel get() {
    return newInstance(problemUseCasesProvider.get());
  }

  public static ProblemDetailViewModel_Factory create(
      Provider<ProblemUseCases> problemUseCasesProvider) {
    return new ProblemDetailViewModel_Factory(problemUseCasesProvider);
  }

  public static ProblemDetailViewModel newInstance(ProblemUseCases problemUseCases) {
    return new ProblemDetailViewModel(problemUseCases);
  }
}
