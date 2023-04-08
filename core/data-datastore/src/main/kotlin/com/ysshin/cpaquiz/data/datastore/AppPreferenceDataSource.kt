package com.ysshin.cpaquiz.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ysshin.cpaquiz.core.android.framework.permission.PostNotification
import javax.inject.Inject
import kotlinx.coroutines.flow.map

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_prefs")

class AppPreferenceDataSource @Inject constructor(private val dataStore: DataStore<Preferences>) {

    object Key {
        const val POST_NOTIFICATIONS = "post_notifications"
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
}
