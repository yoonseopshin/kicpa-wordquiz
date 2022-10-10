package com.ysshin.cpaquiz.core.android.bridge

import android.content.Context
import android.content.Intent

interface MainScreenNavigator {
    fun homeScreenIntent(context: Context, flags: Int? = null): Intent
    fun noteScreenIntent(context: Context, flags: Int? = null): Intent
    fun settingsScreenIntent(context: Context, flags: Int? = null): Intent
}
