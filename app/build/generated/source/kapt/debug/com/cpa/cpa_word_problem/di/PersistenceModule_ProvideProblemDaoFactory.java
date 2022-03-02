package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao;
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
public final class PersistenceModule_ProvideProblemDaoFactory implements Factory<ProblemDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public PersistenceModule_ProvideProblemDaoFactory(Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public ProblemDao get() {
    return provideProblemDao(appDatabaseProvider.get());
  }

  public static PersistenceModule_ProvideProblemDaoFactory create(
      Provider<AppDatabase> appDatabaseProvider) {
    return new PersistenceModule_ProvideProblemDaoFactory(appDatabaseProvider);
  }

  public static ProblemDao provideProblemDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(PersistenceModule.INSTANCE.provideProblemDao(appDatabase));
  }
}
