package com.ysshin.cpaquiz

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.profileinstaller.ProfileVerifier
import com.google.firebase.analytics.FirebaseAnalytics
import com.ysshin.cpaquiz.core.android.initializer.AdMob
import com.ysshin.cpaquiz.core.android.initializer.Timber
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActions
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActionsProvider
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
import com.ysshin.cpaquiz.sync.initializer.Sync
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltAndroidApp
class CpaQuizApplication :
    Application(),
    QuizStartNavigationActionsProvider,
    QuizEndNavigationActionsProvider {

    private val navigator: Navigator by lazy { Navigator() }

    override val quizStartNavActions: QuizStartNavigationActions = navigator

    override val quizEndNavActions: QuizEndNavigationActions = navigator

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
        Timber.initialize(context = this)
        AdMob.initialize(context = this)

        ProcessLifecycleOwner.get().lifecycleScope.launch {
            logCompilationStatus()
        }
    }

    /**
     * Logs the app's Baseline Profile Compilation Status using [ProfileVerifier].
     */
    private suspend fun logCompilationStatus() {
        val analytics = FirebaseAnalytics.getInstance(this)

        /*
        When delivering through Google Play, the baseline profile is compiled during installation.
        In this case you will see the correct state logged without any further action necessary.
        To verify baseline profile installation locally, you need to manually trigger baseline
        profile installation.
        For immediate compilation, call:
         `adb shell cmd package compile -f -m speed-profile com.cpa.cpa_word_problem`
        You can also trigger background optimizations:
         `adb shell pm bg-dexopt-job`
        Both jobs run asynchronously and might take some time complete.
        To see quick turnaround of the ProfileVerifier, we recommend using `speed-profile`.
        If you don't do either of these steps, you might only see the profile status reported as
        "enqueued for compilation" when running the sample locally.
         */
        withContext(Dispatchers.IO) {
            val status = ProfileVerifier.getCompilationStatusAsync().await()

            analytics.logEvent(
                "baseline_profile",
                Bundle().apply {
                    putString("status_code", "${status.profileInstallResultCode}")
                    putString(
                        "status_result",
                        when {
                            status.isCompiledWithProfile -> "Compiled with profile"
                            status.hasProfileEnqueuedForCompilation() ->
                                "Enqueued for compilation"

                            else -> "Profile not compiled or enqueued"
                        },
                    )
                },
            )
        }
    }
}
