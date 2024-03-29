package com.ysshin.cpaquiz.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ysshin.cpaquiz.domain.model.DEFAULT_QUIZ_NUMBER
import com.ysshin.cpaquiz.domain.model.DEFAULT_SOLVED_QUIZ
import com.ysshin.cpaquiz.domain.model.DEFAULT_USE_TIMER
import com.ysshin.cpaquiz.domain.model.IN_APP_REVIEW_THRESHOLD
import com.ysshin.cpaquiz.domain.model.SOLVED_QUIZ_THRESHOLD
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.quizDataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_prefs")

class QuizDatastoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    object Key {
        const val QUIZ_NUMBER = "quiz_number"
        const val USE_TIMER = "use_timer"
        const val SOLVED_QUIZ = "solved_quiz"
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

    private val solvedQuizKey = intPreferencesKey(Key.SOLVED_QUIZ)
    val solvedQuiz = dataStore.data.map { pref -> pref[solvedQuizKey] ?: DEFAULT_SOLVED_QUIZ }

    suspend fun increaseSolvedQuiz() {
        dataStore.edit { pref ->
            pref[solvedQuizKey] = (pref[solvedQuizKey] ?: DEFAULT_SOLVED_QUIZ) + 1
        }
    }

    val shouldRequestInAppReview = dataStore.data.map { pref ->
        val solvedQuiz = pref[solvedQuizKey] ?: return@map false
        solvedQuiz % IN_APP_REVIEW_THRESHOLD == 0
    }

    val shouldShowInterstitialAd = dataStore.data.map { pref ->
        val solvedQuiz = pref[solvedQuizKey] ?: return@map false
        solvedQuiz % SOLVED_QUIZ_THRESHOLD == 0
    }
}
