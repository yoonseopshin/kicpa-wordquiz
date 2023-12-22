package com.ysshin.cpaquiz.core.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.profileinstaller.ProfileVerifier
import com.google.firebase.analytics.FirebaseAnalytics
import com.ysshin.cpaquiz.core.android.util.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
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
