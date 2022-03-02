package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService;
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository;
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
public final class RepositoryModule_ProvideQuizRepositoryFactory implements Factory<QuizRepository> {
  private final Provider<QuizService> quizServiceProvider;

  private final Provider<ProblemDao> problemDaoProvider;

  private final Provider<WrongProblemDao> wrongProblemDaoProvider;

  public RepositoryModule_ProvideQuizRepositoryFactory(Provider<QuizService> quizServiceProvider,
      Provider<ProblemDao> problemDaoProvider, Provider<WrongProblemDao> wrongProblemDaoProvider) {
    this.quizServiceProvider = quizServiceProvider;
    this.problemDaoProvider = problemDaoProvider;
    this.wrongProblemDaoProvider = wrongProblemDaoProvider;
  }

  @Override
  public QuizRepository get() {
    return provideQuizRepository(quizServiceProvider.get(), problemDaoProvider.get(), wrongProblemDaoProvider.get());
  }

  public static RepositoryModule_ProvideQuizRepositoryFactory create(
      Provider<QuizService> quizServiceProvider, Provider<ProblemDao> problemDaoProvider,
      Provider<WrongProblemDao> wrongProblemDaoProvider) {
    return new RepositoryModule_ProvideQuizRepositoryFactory(quizServiceProvider, problemDaoProvider, wrongProblemDaoProvider);
  }

  public static QuizRepository provideQuizRepository(QuizService quizService, ProblemDao problemDao,
      WrongProblemDao wrongProblemDao) {
    return Preconditions.checkNotNullFromProvides(RepositoryModule.INSTANCE.provideQuizRepository(quizService, problemDao, wrongProblemDao));
  }
}
