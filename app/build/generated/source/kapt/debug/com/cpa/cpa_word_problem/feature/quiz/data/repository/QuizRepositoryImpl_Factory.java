package com.cpa.cpa_word_problem.feature.quiz.data.repository;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService;
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
public final class QuizRepositoryImpl_Factory implements Factory<QuizRepositoryImpl> {
  private final Provider<QuizService> quizServiceProvider;

  private final Provider<ProblemDao> problemDaoProvider;

  private final Provider<WrongProblemDao> wrongProblemDaoProvider;

  public QuizRepositoryImpl_Factory(Provider<QuizService> quizServiceProvider,
      Provider<ProblemDao> problemDaoProvider, Provider<WrongProblemDao> wrongProblemDaoProvider) {
    this.quizServiceProvider = quizServiceProvider;
    this.problemDaoProvider = problemDaoProvider;
    this.wrongProblemDaoProvider = wrongProblemDaoProvider;
  }

  @Override
  public QuizRepositoryImpl get() {
    return newInstance(quizServiceProvider.get(), problemDaoProvider.get(), wrongProblemDaoProvider.get());
  }

  public static QuizRepositoryImpl_Factory create(Provider<QuizService> quizServiceProvider,
      Provider<ProblemDao> problemDaoProvider, Provider<WrongProblemDao> wrongProblemDaoProvider) {
    return new QuizRepositoryImpl_Factory(quizServiceProvider, problemDaoProvider, wrongProblemDaoProvider);
  }

  public static QuizRepositoryImpl newInstance(QuizService quizService, ProblemDao problemDao,
      WrongProblemDao wrongProblemDao) {
    return new QuizRepositoryImpl(quizService, problemDao, wrongProblemDao);
  }
}
