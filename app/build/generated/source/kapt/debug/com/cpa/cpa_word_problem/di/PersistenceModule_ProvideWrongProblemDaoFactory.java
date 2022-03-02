package com.cpa.cpa_word_problem.di;

import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao;
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
public final class PersistenceModule_ProvideWrongProblemDaoFactory implements Factory<WrongProblemDao> {
  private final Provider<AppDatabase> appDatabaseProvider;

  public PersistenceModule_ProvideWrongProblemDaoFactory(
      Provider<AppDatabase> appDatabaseProvider) {
    this.appDatabaseProvider = appDatabaseProvider;
  }

  @Override
  public WrongProblemDao get() {
    return provideWrongProblemDao(appDatabaseProvider.get());
  }

  public static PersistenceModule_ProvideWrongProblemDaoFactory create(
      Provider<AppDatabase> appDatabaseProvider) {
    return new PersistenceModule_ProvideWrongProblemDaoFactory(appDatabaseProvider);
  }

  public static WrongProblemDao provideWrongProblemDao(AppDatabase appDatabase) {
    return Preconditions.checkNotNullFromProvides(PersistenceModule.INSTANCE.provideWrongProblemDao(appDatabase));
  }
}
