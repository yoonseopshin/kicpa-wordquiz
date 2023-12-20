package com.ysshin.cpaquiz.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ysshin.cpaquiz.core.android.framework.permission.PostNotification
import com.ysshin.cpaquiz.domain.model.AppConfig
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

class AppPreferenceDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {

    object Key {
        const val POST_NOTIFICATIONS = "post_notifications"
        const val HOME_NATIVE_MEDIUM_AD = "home_native_medium_ad"
        const val NOTE_NATIVE_SMALL_AD = "note_native_small_ad"
        const val SETTINGS_NATIVE_MEDIUM_AD = "settings_native_medium_ad"
        const val QUIZ_RESULT_INTERSTITIAL_AD = "quiz_result_interstitial_ad"
        const val QUIZ_RESULT_NATIVE_MEDIUM_AD = "quiz_result_native_medium_ad"
    }

    private val postNotificationStatusKey = intPreferencesKey(Key.POST_NOTIFICATIONS)
    val postNotification =
        dataStore.data.map { pref -> pref[postNotificationStatusKey] ?: PostNotification.NOT_REQUESTED }

    suspend fun grantPostNotification() {
        dataStore.edit { pref ->
            pref[postNotificationStatusKey] = PostNotification.GRANTED
        }
    }

    suspend fun denyPostNotification() {
        dataStore.edit { pref ->
            pref[postNotificationStatusKey] = PostNotification.DENIED
        }
    }

    private val homeNativeMediumAdKey = booleanPreferencesKey(Key.HOME_NATIVE_MEDIUM_AD)
    val isHomeNativeMediumAdEnabled =
        dataStore.data.map { pref -> pref[homeNativeMediumAdKey] ?: true }

    private val noteNativeSmallAdKey = booleanPreferencesKey(Key.NOTE_NATIVE_SMALL_AD)
    val isNoteNativeSmallAdEnabled =
        dataStore.data.map { pref -> pref[noteNativeSmallAdKey] ?: true }

    private val settingsNativeMediumAdKey = booleanPreferencesKey(Key.SETTINGS_NATIVE_MEDIUM_AD)
    val isSettingsNativeMediumAdEnabled =
        dataStore.data.map { pref -> pref[settingsNativeMediumAdKey] ?: true }

    private val quizResultInterstitialAdKey = booleanPreferencesKey(Key.QUIZ_RESULT_INTERSTITIAL_AD)
    val isQuizResultInterstitialAdEnabled =
        dataStore.data.map { pref -> pref[quizResultInterstitialAdKey] ?: true }

    private val quizResultNativeMediumAdKey = booleanPreferencesKey(Key.QUIZ_RESULT_NATIVE_MEDIUM_AD)
    val isQuizResultNativeMediumAdEnabled =
        dataStore.data.map { pref -> pref[quizResultNativeMediumAdKey] ?: true }

    suspend fun updateAdConfig(config: AppConfig) {
        dataStore.edit { pref ->
            pref[homeNativeMediumAdKey] = config.homeNativeMediumAd.on
            pref[noteNativeSmallAdKey] = config.noteNativeSmallAd.on
            pref[settingsNativeMediumAdKey] = config.homeNativeMediumAd.on
            pref[quizResultInterstitialAdKey] = config.quizResultInterstitialAd.on
            pref[quizResultNativeMediumAdKey] = config.quizResultNativeMediumAd.on
        }
    }

}
