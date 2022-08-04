package com.cpa.cpa_word_problem

import android.app.Application
import com.ysshin.cpaquiz.feature.sync.initializer.Sync
import com.ysshin.cpaquiz.shared.android.initializer.AdMob
import com.ysshin.cpaquiz.shared.android.initializer.Timber
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