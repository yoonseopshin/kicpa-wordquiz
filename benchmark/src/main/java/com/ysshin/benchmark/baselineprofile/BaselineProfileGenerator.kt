package com.ysshin.benchmark.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import com.ysshin.benchmark.PACKAGE_NAME
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

            // Navigate to home screen
            device.findObject(By.text("홈")).click()

            // Navigate to quiz screen
            device.findObject(By.text("회계학")).click()

            // Return to home screen
            device.pressBack()

            // Navigate to note screen
            device.findObject(By.text("노트")).click()

            // Navigate to settings screen
            device.findObject(By.text("설정")).click()
        }
}
