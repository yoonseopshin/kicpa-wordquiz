package com.ysshin.cpaquiz.shared.android.bridge

import android.content.Context
import android.content.Intent

interface MainTabNavigator {
    fun homeTabIntent(context: Context, flags: Int? = null): Intent
    fun noteTabIntent(context: Context, flags: Int? = null): Intent
    fun settingsTabIntent(context: Context, flags: Int? = null): Intent
}
