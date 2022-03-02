package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager;
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<QuizUseCases> quizUseCasesProvider;

  private final Provider<QuizDatastoreManager> quizDatastoreManagerProvider;

  public HomeViewModel_Factory(Provider<QuizUseCases> quizUseCasesProvider,
      Provider<QuizDatastoreManager> quizDatastoreManagerProvider) {
    this.quizUseCasesProvider = quizUseCasesProvider;
    this.quizDatastoreManagerProvider = quizDatastoreManagerProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(quizUseCasesProvider.get(), quizDatastoreManagerProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<QuizUseCases> quizUseCasesProvider,
      Provider<QuizDatastoreManager> quizDatastoreManagerProvider) {
    return new HomeViewModel_Factory(quizUseCasesProvider, quizDatastoreManagerProvider);
  }

  public static HomeViewModel newInstance(QuizUseCases quizUseCases,
      QuizDatastoreManager quizDatastoreManager) {
    return new HomeViewModel(quizUseCases, quizDatastoreManager);
  }
}
