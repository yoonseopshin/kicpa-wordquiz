package com.cpa.cpa_word_problem.di;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
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
public final class PersistenceModule_ProvideQuizDataStoreFactory implements Factory<DataStore<Preferences>> {
  private final Provider<Context> contextProvider;

  public PersistenceModule_ProvideQuizDataStoreFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DataStore<Preferences> get() {
    return provideQuizDataStore(contextProvider.get());
  }

  public static PersistenceModule_ProvideQuizDataStoreFactory create(
      Provider<Context> contextProvider) {
    return new PersistenceModule_ProvideQuizDataStoreFactory(contextProvider);
  }

  public static DataStore<Preferences> provideQuizDataStore(Context context) {
    return Preconditions.checkNotNullFromProvides(PersistenceModule.INSTANCE.provideQuizDataStore(context));
  }
}
