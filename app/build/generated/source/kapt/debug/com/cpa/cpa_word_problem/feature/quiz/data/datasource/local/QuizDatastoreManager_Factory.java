package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
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
public final class QuizDatastoreManager_Factory implements Factory<QuizDatastoreManager> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public QuizDatastoreManager_Factory(Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public QuizDatastoreManager get() {
    return newInstance(dataStoreProvider.get());
  }

  public static QuizDatastoreManager_Factory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new QuizDatastoreManager_Factory(dataStoreProvider);
  }

  public static QuizDatastoreManager newInstance(DataStore<Preferences> dataStore) {
    return new QuizDatastoreManager(dataStore);
  }
}
