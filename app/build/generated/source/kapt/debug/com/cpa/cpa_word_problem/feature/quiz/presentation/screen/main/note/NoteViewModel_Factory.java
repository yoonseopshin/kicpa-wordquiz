package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note;

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
public final class NoteViewModel_Factory implements Factory<NoteViewModel> {
  private final Provider<ProblemUseCases> problemUseCasesProvider;

  public NoteViewModel_Factory(Provider<ProblemUseCases> problemUseCasesProvider) {
    this.problemUseCasesProvider = problemUseCasesProvider;
  }

  @Override
  public NoteViewModel get() {
    return newInstance(problemUseCasesProvider.get());
  }

  public static NoteViewModel_Factory create(Provider<ProblemUseCases> problemUseCasesProvider) {
    return new NoteViewModel_Factory(problemUseCasesProvider);
  }

  public static NoteViewModel newInstance(ProblemUseCases problemUseCases) {
    return new NoteViewModel(problemUseCases);
  }
}
