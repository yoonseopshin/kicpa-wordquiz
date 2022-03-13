package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.quizDataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_prefs")

class QuizDatastoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val DEFAULT_QUIZ_NUMBER = 3
        const val DEFAULT_USE_TIMER = true
        const val DEFAULT_USE_ALARM = false
        const val DEFAULT_ALARM_TIME = -1
        const val DEFAULT_SOLVED_QUIZ = 0
        const val DEFAULT_IN_APP_REVIEW_THRESHOLD = 12
        const val DEFAULT_SOLVED_QUIZ_THRESHOLD = 5
    }

    object Key {
        const val QUIZ_NUMBER = "quiz_number"
        const val USE_TIMER = "use_timer"
        const val USE_ALARM = "use_alarm"
        const val ALARM_HOUR_OF_DAY = "alarm_hour_of_day"
        const val ALARM_MINUTE = "alarm_minute"
        const val SOLVED_QUIZ = "SOLVED_QUIZ"
    }

    private val quizNumberKey = intPreferencesKey(Key.QUIZ_NUMBER)
    val quizNumber = dataStore.data.map { pref -> pref[quizNumberKey] ?: DEFAULT_QUIZ_NUMBER }

    suspend fun setQuizNumber(value: Int) {
        dataStore.edit { pref -> pref[quizNumberKey] = value }
    }

    private val useTimerKey = booleanPreferencesKey(Key.USE_TIMER)
    val useTimer = dataStore.data.map { pref -> pref[useTimerKey] ?: DEFAULT_USE_TIMER }

    suspend fun setTimer(value: Boolean) {
        dataStore.edit { pref -> pref[useTimerKey] = value }
    }

    private val useAlarmKey = booleanPreferencesKey(Key.USE_ALARM)
    val useAlarm = dataStore.data.map { pref -> pref[useAlarmKey] ?: DEFAULT_USE_ALARM }

    suspend fun setAlarm(value: Boolean) {
        dataStore.edit { pref -> pref[useAlarmKey] = value }
    }

    private val alarmHourOfDayKey = intPreferencesKey(Key.ALARM_HOUR_OF_DAY)
    val alarmHourOfDay =
        dataStore.data.map { pref -> pref[alarmHourOfDayKey] ?: DEFAULT_ALARM_TIME }

    suspend fun setAlarmHourOfDay(value: Int) {
        dataStore.edit { pref -> pref[alarmHourOfDayKey] = value }
    }

    private val alarmMinuteKey = intPreferencesKey(Key.ALARM_MINUTE)
    val alarmMinute =
        dataStore.data.map { pref -> pref[alarmMinuteKey] ?: DEFAULT_ALARM_TIME }

    suspend fun setAlarmMinute(value: Int) {
        dataStore.edit { pref -> pref[alarmMinuteKey] = value }
    }

    private val solvedQuizKey = intPreferencesKey(Key.SOLVED_QUIZ)
    val solvedQuiz = dataStore.data.map { pref -> pref[solvedQuizKey] ?: DEFAULT_SOLVED_QUIZ }

    suspend fun increaseSolvedQuiz() {
        dataStore.edit { pref ->
            pref[solvedQuizKey] = (pref[solvedQuizKey] ?: DEFAULT_SOLVED_QUIZ) + 1
        }
    }

    val shouldRequestInAppReview = dataStore.data.map { pref ->
        val solvedQuiz = pref[solvedQuizKey] ?: DEFAULT_SOLVED_QUIZ

        if (solvedQuiz == DEFAULT_SOLVED_QUIZ) {
            return@map false
        }

        return@map solvedQuiz % DEFAULT_IN_APP_REVIEW_THRESHOLD == 0 && solvedQuiz % DEFAULT_SOLVED_QUIZ_THRESHOLD != 0
    }

}