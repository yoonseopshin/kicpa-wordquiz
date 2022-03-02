package com.cpa.cpa_word_problem;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.cpa.cpa_word_problem.di.ApiModule;
import com.cpa.cpa_word_problem.di.ApiModule_ProvideBaseUrlFactory;
import com.cpa.cpa_word_problem.di.ApiModule_ProvideNetworkFlipperPluginFactory;
import com.cpa.cpa_word_problem.di.ApiModule_ProvideOkHttpClientFactory;
import com.cpa.cpa_word_problem.di.ApiModule_ProvideQuizServiceFactory;
import com.cpa.cpa_word_problem.di.ApiModule_ProvideRetrofitFactory;
import com.cpa.cpa_word_problem.di.PersistenceModule;
import com.cpa.cpa_word_problem.di.PersistenceModule_ProvideAppDatabaseFactory;
import com.cpa.cpa_word_problem.di.PersistenceModule_ProvideProblemDaoFactory;
import com.cpa.cpa_word_problem.di.PersistenceModule_ProvideQuizDataStoreFactory;
import com.cpa.cpa_word_problem.di.PersistenceModule_ProvideQuizDataStoreManagerFactory;
import com.cpa.cpa_word_problem.di.PersistenceModule_ProvideWrongProblemDaoFactory;
import com.cpa.cpa_word_problem.di.RepositoryModule;
import com.cpa.cpa_word_problem.di.RepositoryModule_ProvideQuizRepositoryFactory;
import com.cpa.cpa_word_problem.di.UseCaseModule;
import com.cpa.cpa_word_problem.di.UseCaseModule_ProvideProblemUseCasesFactory;
import com.cpa.cpa_word_problem.di.UseCaseModule_ProvideQuizUseCasesFactory;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao;
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService;
import com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository;
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases;
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainActivity;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainViewModel;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeFragment;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteFragment;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.settings.SettingsFragment;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailActivity;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsActivity;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsViewModel;
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cpa.cpa_word_problem.initializers.FlipperInitializer;
import com.cpa.cpa_word_problem.initializers.FlipperInitializer_MembersInjector;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_Lifecycle_Factory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.SetBuilder;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerCpaQuizApp_HiltComponents_SingletonC extends CpaQuizApp_HiltComponents.SingletonC {
  private final ApplicationContextModule applicationContextModule;

  private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC = this;

  private Provider<NetworkFlipperPlugin> provideNetworkFlipperPluginProvider;

  private Provider<OkHttpClient> provideOkHttpClientProvider;

  private Provider<String> provideBaseUrlProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<QuizService> provideQuizServiceProvider;

  private Provider<AppDatabase> provideAppDatabaseProvider;

  private Provider<ProblemDao> provideProblemDaoProvider;

  private Provider<WrongProblemDao> provideWrongProblemDaoProvider;

  private Provider<QuizRepository> provideQuizRepositoryProvider;

  private Provider<QuizUseCases> provideQuizUseCasesProvider;

  private Provider<DataStore<Preferences>> provideQuizDataStoreProvider;

  private Provider<QuizDatastoreManager> provideQuizDataStoreManagerProvider;

  private Provider<ProblemUseCases> provideProblemUseCasesProvider;

  private DaggerCpaQuizApp_HiltComponents_SingletonC(
      ApplicationContextModule applicationContextModuleParam) {
    this.applicationContextModule = applicationContextModuleParam;
    initialize(applicationContextModuleParam);

  }

  public static Builder builder() {
    return new Builder();
  }

  private Retrofit retrofit() {
    return ApiModule_ProvideRetrofitFactory.provideRetrofit(provideOkHttpClientProvider.get(), provideBaseUrlProvider.get());
  }

  private QuizService quizService() {
    return ApiModule_ProvideQuizServiceFactory.provideQuizService(provideRetrofitProvider.get());
  }

  private AppDatabase appDatabase() {
    return PersistenceModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule));
  }

  private ProblemDao problemDao() {
    return PersistenceModule_ProvideProblemDaoFactory.provideProblemDao(provideAppDatabaseProvider.get());
  }

  private WrongProblemDao wrongProblemDao() {
    return PersistenceModule_ProvideWrongProblemDaoFactory.provideWrongProblemDao(provideAppDatabaseProvider.get());
  }

  private QuizRepository quizRepository() {
    return RepositoryModule_ProvideQuizRepositoryFactory.provideQuizRepository(provideQuizServiceProvider.get(), provideProblemDaoProvider.get(), provideWrongProblemDaoProvider.get());
  }

  private QuizUseCases quizUseCases() {
    return UseCaseModule_ProvideQuizUseCasesFactory.provideQuizUseCases(provideQuizRepositoryProvider.get());
  }

  private DataStore<Preferences> namedDataStoreOfPreferences() {
    return PersistenceModule_ProvideQuizDataStoreFactory.provideQuizDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule));
  }

  private QuizDatastoreManager quizDatastoreManager() {
    return PersistenceModule_ProvideQuizDataStoreManagerFactory.provideQuizDataStoreManager(provideQuizDataStoreProvider.get());
  }

  private ProblemUseCases problemUseCases() {
    return UseCaseModule_ProvideProblemUseCasesFactory.provideProblemUseCases(provideQuizRepositoryProvider.get());
  }

  @SuppressWarnings("unchecked")
  private void initialize(final ApplicationContextModule applicationContextModuleParam) {
    this.provideNetworkFlipperPluginProvider = DoubleCheck.provider(new SwitchingProvider<NetworkFlipperPlugin>(singletonC, 0));
    this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonC, 5));
    this.provideBaseUrlProvider = DoubleCheck.provider(new SwitchingProvider<String>(singletonC, 6));
    this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonC, 4));
    this.provideQuizServiceProvider = DoubleCheck.provider(new SwitchingProvider<QuizService>(singletonC, 3));
    this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonC, 8));
    this.provideProblemDaoProvider = DoubleCheck.provider(new SwitchingProvider<ProblemDao>(singletonC, 7));
    this.provideWrongProblemDaoProvider = DoubleCheck.provider(new SwitchingProvider<WrongProblemDao>(singletonC, 9));
    this.provideQuizRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<QuizRepository>(singletonC, 2));
    this.provideQuizUseCasesProvider = DoubleCheck.provider(new SwitchingProvider<QuizUseCases>(singletonC, 1));
    this.provideQuizDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonC, 11));
    this.provideQuizDataStoreManagerProvider = DoubleCheck.provider(new SwitchingProvider<QuizDatastoreManager>(singletonC, 10));
    this.provideProblemUseCasesProvider = DoubleCheck.provider(new SwitchingProvider<ProblemUseCases>(singletonC, 12));
  }

  @Override
  public void injectCpaQuizApp(CpaQuizApp cpaQuizApp) {
  }

  @Override
  public void inject(FlipperInitializer initializer) {
    injectFlipperInitializer(initializer);
  }

  @Override
  public ActivityRetainedComponentBuilder retainedComponentBuilder() {
    return new ActivityRetainedCBuilder(singletonC);
  }

  @Override
  public ServiceComponentBuilder serviceComponentBuilder() {
    return new ServiceCBuilder(singletonC);
  }

  private FlipperInitializer injectFlipperInitializer(FlipperInitializer instance) {
    FlipperInitializer_MembersInjector.injectNetworkFlipperPlugin(instance, provideNetworkFlipperPluginProvider.get());
    return instance;
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder apiModule(ApiModule apiModule) {
      Preconditions.checkNotNull(apiModule);
      return this;
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder persistenceModule(PersistenceModule persistenceModule) {
      Preconditions.checkNotNull(persistenceModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder repositoryModule(RepositoryModule repositoryModule) {
      Preconditions.checkNotNull(repositoryModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder useCaseModule(UseCaseModule useCaseModule) {
      Preconditions.checkNotNull(useCaseModule);
      return this;
    }

    public CpaQuizApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new DaggerCpaQuizApp_HiltComponents_SingletonC(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CpaQuizApp_HiltComponents.ActivityRetainedC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private ActivityRetainedCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC) {
      this.singletonC = singletonC;
    }

    @Override
    public CpaQuizApp_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonC);
    }
  }

  private static final class ActivityCBuilder implements CpaQuizApp_HiltComponents.ActivityC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonC, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CpaQuizApp_HiltComponents.FragmentC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonC, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CpaQuizApp_HiltComponents.ViewWithFragmentC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonC, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CpaQuizApp_HiltComponents.ViewC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonC, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CpaQuizApp_HiltComponents.ViewModelC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      return new ViewModelCImpl(singletonC, activityRetainedCImpl, savedStateHandle);
    }
  }

  private static final class ServiceCBuilder implements CpaQuizApp_HiltComponents.ServiceC.Builder {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private Service service;

    private ServiceCBuilder(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC) {
      this.singletonC = singletonC;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CpaQuizApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonC, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CpaQuizApp_HiltComponents.ViewWithFragmentC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CpaQuizApp_HiltComponents.FragmentC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public void injectHomeFragment(HomeFragment homeFragment) {
    }

    @Override
    public void injectNoteFragment(NoteFragment noteFragment) {
    }

    @Override
    public void injectSettingsFragment(SettingsFragment settingsFragment) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonC, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CpaQuizApp_HiltComponents.ViewC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl, View viewParam) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CpaQuizApp_HiltComponents.ActivityC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public void injectProblemDetailActivity(ProblemDetailActivity problemDetailActivity) {
    }

    @Override
    public void injectQuizStatisticsActivity(QuizStatisticsActivity quizStatisticsActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(ApplicationContextModule_ProvideApplicationFactory.provideApplication(singletonC.applicationContextModule), getViewModelKeys(), new ViewModelCBuilder(singletonC, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return SetBuilder.<String>newSetBuilder(5).add(HomeViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(MainViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(NoteViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(ProblemDetailViewModel_HiltModules_KeyModule_ProvideFactory.provide()).add(QuizStatisticsViewModel_HiltModules_KeyModule_ProvideFactory.provide()).build();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonC, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonC, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonC, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends CpaQuizApp_HiltComponents.ViewModelC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<NoteViewModel> noteViewModelProvider;

    private Provider<ProblemDetailViewModel> problemDetailViewModelProvider;

    private Provider<QuizStatisticsViewModel> quizStatisticsViewModelProvider;

    private ViewModelCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam) {
      this.singletonC = singletonC;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam);

    }

    private HomeViewModel homeViewModel() {
      return new HomeViewModel(singletonC.provideQuizUseCasesProvider.get(), singletonC.provideQuizDataStoreManagerProvider.get());
    }

    private MainViewModel mainViewModel() {
      return new MainViewModel(singletonC.provideProblemUseCasesProvider.get());
    }

    private NoteViewModel noteViewModel() {
      return new NoteViewModel(singletonC.provideProblemUseCasesProvider.get());
    }

    private ProblemDetailViewModel problemDetailViewModel() {
      return new ProblemDetailViewModel(singletonC.provideProblemUseCasesProvider.get());
    }

    private QuizStatisticsViewModel quizStatisticsViewModel() {
      return new QuizStatisticsViewModel(singletonC.provideProblemUseCasesProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam) {
      this.homeViewModelProvider = new SwitchingProvider<>(singletonC, activityRetainedCImpl, viewModelCImpl, 0);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonC, activityRetainedCImpl, viewModelCImpl, 1);
      this.noteViewModelProvider = new SwitchingProvider<>(singletonC, activityRetainedCImpl, viewModelCImpl, 2);
      this.problemDetailViewModelProvider = new SwitchingProvider<>(singletonC, activityRetainedCImpl, viewModelCImpl, 3);
      this.quizStatisticsViewModelProvider = new SwitchingProvider<>(singletonC, activityRetainedCImpl, viewModelCImpl, 4);
    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return MapBuilder.<String, Provider<ViewModel>>newMapBuilder(5).put("com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel", (Provider) homeViewModelProvider).put("com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainViewModel", (Provider) mainViewModelProvider).put("com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel", (Provider) noteViewModelProvider).put("com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel", (Provider) problemDetailViewModelProvider).put("com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsViewModel", (Provider) quizStatisticsViewModelProvider).build();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
          ActivityRetainedCImpl activityRetainedCImpl, ViewModelCImpl viewModelCImpl, int id) {
        this.singletonC = singletonC;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.home.HomeViewModel 
          return (T) viewModelCImpl.homeViewModel();

          case 1: // com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainViewModel 
          return (T) viewModelCImpl.mainViewModel();

          case 2: // com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note.NoteViewModel 
          return (T) viewModelCImpl.noteViewModel();

          case 3: // com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailViewModel 
          return (T) viewModelCImpl.problemDetailViewModel();

          case 4: // com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics.QuizStatisticsViewModel 
          return (T) viewModelCImpl.quizStatisticsViewModel();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CpaQuizApp_HiltComponents.ActivityRetainedC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    @SuppressWarnings("rawtypes")
    private Provider lifecycleProvider;

    private ActivityRetainedCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC) {
      this.singletonC = singletonC;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.lifecycleProvider = DoubleCheck.provider(new SwitchingProvider<Object>(singletonC, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonC, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return (ActivityRetainedLifecycle) lifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
          ActivityRetainedCImpl activityRetainedCImpl, int id) {
        this.singletonC = singletonC;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.internal.managers.ActivityRetainedComponentManager.Lifecycle 
          return (T) ActivityRetainedComponentManager_Lifecycle_Factory.newInstance();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CpaQuizApp_HiltComponents.ServiceC {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC,
        Service serviceParam) {
      this.singletonC = singletonC;


    }
  }

  private static final class SwitchingProvider<T> implements Provider<T> {
    private final DaggerCpaQuizApp_HiltComponents_SingletonC singletonC;

    private final int id;

    SwitchingProvider(DaggerCpaQuizApp_HiltComponents_SingletonC singletonC, int id) {
      this.singletonC = singletonC;
      this.id = id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
      switch (id) {
        case 0: // com.facebook.flipper.plugins.network.NetworkFlipperPlugin 
        return (T) ApiModule_ProvideNetworkFlipperPluginFactory.provideNetworkFlipperPlugin();

        case 1: // com.cpa.cpa_word_problem.feature.quiz.domain.usecase.quiz.QuizUseCases 
        return (T) singletonC.quizUseCases();

        case 2: // com.cpa.cpa_word_problem.feature.quiz.domain.repository.QuizRepository 
        return (T) singletonC.quizRepository();

        case 3: // com.cpa.cpa_word_problem.feature.quiz.data.datasource.remote.api.QuizService 
        return (T) singletonC.quizService();

        case 4: // retrofit2.Retrofit 
        return (T) singletonC.retrofit();

        case 5: // okhttp3.OkHttpClient 
        return (T) ApiModule_ProvideOkHttpClientFactory.provideOkHttpClient();

        case 6: // @javax.inject.Named("BaseUrl") java.lang.String 
        return (T) ApiModule_ProvideBaseUrlFactory.provideBaseUrl();

        case 7: // com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.ProblemDao 
        return (T) singletonC.problemDao();

        case 8: // com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.AppDatabase 
        return (T) singletonC.appDatabase();

        case 9: // com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.WrongProblemDao 
        return (T) singletonC.wrongProblemDao();

        case 10: // com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager 
        return (T) singletonC.quizDatastoreManager();

        case 11: // @javax.inject.Named("QuizDataStore") androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
        return (T) singletonC.namedDataStoreOfPreferences();

        case 12: // com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases 
        return (T) singletonC.problemUseCases();

        default: throw new AssertionError(id);
      }
    }
  }
}
