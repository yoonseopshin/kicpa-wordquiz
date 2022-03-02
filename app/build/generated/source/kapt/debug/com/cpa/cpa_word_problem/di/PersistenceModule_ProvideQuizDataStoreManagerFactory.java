package com.cpa.cpa_word_problem.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager;
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
public final class PersistenceModule_ProvideQuizDataStoreManagerFactory implements Factory<QuizDatastoreManager> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public PersistenceModule_ProvideQuizDataStoreManagerFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public QuizDatastoreManager get() {
    return provideQuizDataStoreManager(dataStoreProvider.get());
  }

  public static PersistenceModule_ProvideQuizDataStoreManagerFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new PersistenceModule_ProvideQuizDataStoreManagerFactory(dataStoreProvider);
  }

  public static QuizDatastoreManager provideQuizDataStoreManager(DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(PersistenceModule.INSTANCE.provideQuizDataStoreManager(dataStore));
  }
}
