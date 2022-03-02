package com.cpa.cpa_word_problem.feature.quiz.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.quizDataStore: DataStore<Preferences> by preferencesDataStore(name = "quiz_prefs")

class QuizDatastoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        const val DEFAULT_QUIZ_NUMBER = 3
        const val USE_TIMER = true
    }

    object Key {
        const val QUIZ_NUMBER = "quiz_number"
        const val USE_TIMER = "use_timer"
    }

    private val quizNumberKey = intPreferencesKey(Key.QUIZ_NUMBER)
    val quizNumber = dataStore.data.map { pref -> pref[quizNumberKey] ?: DEFAULT_QUIZ_NUMBER }

    suspend fun setQuizNumber(value: Int) {
        dataStore.edit { pref -> pref[quizNumberKey] = value }
    }

    private val useTimerKey = booleanPreferencesKey(Key.USE_TIMER)
    val useTimer = dataStore.data.map { pref -> pref[useTimerKey] ?: USE_TIMER }

    suspend fun setTimer(value: Boolean) {
        dataStore.edit { pref -> pref[useTimerKey] = value }
    }

}