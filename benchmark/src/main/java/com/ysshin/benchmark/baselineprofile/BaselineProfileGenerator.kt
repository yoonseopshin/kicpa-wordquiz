package com.ysshin.benchmark.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.ysshin.benchmark.PACKAGE_NAME
import com.ysshin.benchmark.navigateQuizJourney
import com.ysshin.benchmark.navigateNoteJourney
import com.ysshin.benchmark.navigateSettingsJourney
import com.ysshin.benchmark.wait
import org.junit.Rule
import org.junit.Test

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`.
 */
@ExperimentalBaselineProfilesApi
class BaselineProfileGenerator {

    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collectBaselineProfile(PACKAGE_NAME) {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            pressHome()
            startActivityAndWait()

            // Wait for network, database job
            device.wait(2000L)
            device.navigateQuizJourney()

            device.wait(100L)
            device.navigateNoteJourney()

            device.wait(100L)
            device.navigateSettingsJourney()
        }
}

