package com.ysshin.baselineprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`.
 */
class BaselineProfileGenerator {

    @RequiresApi(Build.VERSION_CODES.P)
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @RequiresApi(Build.VERSION_CODES.P)
    @Test
    fun generate() =
        baselineProfileRule.collect(packageName = PACKAGE_NAME, maxIterations = 3) {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            pressHome()
            startActivityAndWait()

            // Wait network call and database queries.
            device.wait(2000L)
            device.closeNotificationRequestDialogIfOpened()

            device.wait(100L)
            device.navigateQuizJourney()

            device.wait(100L)
            device.navigateNoteJourney()

            device.wait(100L)
            device.navigateSettingsJourney()
        }
}
