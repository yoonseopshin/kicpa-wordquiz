package com.cpa.cpa_word_problem.di;

import android.content.Context;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase;
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
public final class PersistenceModule_ProvideAppDatabaseFactory implements Factory<AppDatabase> {
  private final Provider<Context> contextProvider;

  public PersistenceModule_ProvideAppDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AppDatabase get() {
    return provideAppDatabase(contextProvider.get());
  }

  public static PersistenceModule_ProvideAppDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new PersistenceModule_ProvideAppDatabaseFactory(contextProvider);
  }

  public static AppDatabase provideAppDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(PersistenceModule.INSTANCE.provideAppDatabase(context));
  }
}
