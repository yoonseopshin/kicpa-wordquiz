package com.cpa.cpa_word_problem

import android.app.Application
import com.ysshin.cpaquiz.sync.initializer.Sync
import com.ysshin.cpaquiz.core.android.initializer.AdMob
import com.ysshin.cpaquiz.core.android.initializer.Timber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CpaQuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
        Timber.initialize(context = this)
        AdMob.initialize(context = this)
    }
}
