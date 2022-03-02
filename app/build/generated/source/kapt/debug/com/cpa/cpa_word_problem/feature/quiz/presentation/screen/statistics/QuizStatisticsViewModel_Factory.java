package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics;

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
public final class QuizStatisticsViewModel_Factory implements Factory<QuizStatisticsViewModel> {
  private final Provider<ProblemUseCases> problemUseCasesProvider;

  public QuizStatisticsViewModel_Factory(Provider<ProblemUseCases> problemUseCasesProvider) {
    this.problemUseCasesProvider = problemUseCasesProvider;
  }

  @Override
  public QuizStatisticsViewModel get() {
    return newInstance(problemUseCasesProvider.get());
  }

  public static QuizStatisticsViewModel_Factory create(
      Provider<ProblemUseCases> problemUseCasesProvider) {
    return new QuizStatisticsViewModel_Factory(problemUseCasesProvider);
  }

  public static QuizStatisticsViewModel newInstance(ProblemUseCases problemUseCases) {
    return new QuizStatisticsViewModel(problemUseCases);
  }
}
