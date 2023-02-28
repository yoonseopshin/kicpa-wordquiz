package com.ysshin.benchmark

import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import kotlin.random.Random

const val PACKAGE_NAME = "com.cpa.cpa_word_problem"

fun UiDevice.wait(timeMillis: Long) {
    waitForWindowUpdate(PACKAGE_NAME, timeMillis)
}

fun UiDevice.navigateQuizJourney() {
    // Move to home tab
    findObject(By.res("홈")).click()

    // Navigate to quiz screen
    findObject(By.res("quizCard${Random.nextInt(4)}")).click()

    repeat(5) {
        wait(1000L)
        findObject(By.res("rb${Random.nextInt(5)}")).click()
        wait(500L)
        findObject(By.res("fab")).click()
    }

    wait(1000L)
    findObject(By.text("확인")).click()
}

fun UiDevice.navigateNoteJourney() {
    // Move to note tab
    findObject(By.res("노트")).click()

    // Scroll LazyColumn
    findObject(By.res("noteLazyColumn")).scroll(Direction.DOWN, 1f)
}

fun UiDevice.navigateSettingsJourney() {
    // Move to settings tab
    findObject(By.res("설정")).click()
}
