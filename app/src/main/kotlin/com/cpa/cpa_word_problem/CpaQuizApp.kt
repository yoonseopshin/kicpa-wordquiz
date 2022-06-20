package com.cpa.cpa_word_problem

import android.app.Application
import com.ysshin.cpaquiz.data.network.initializer.Flipper
import com.ysshin.cpaquiz.feature.sync.initializer.Sync
import com.ysshin.cpaquiz.shared.android.initializer.Timber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CpaQuizApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(context = this)
        Flipper.initialize(context = this)
        Timber.initialize(context = this)
    }
}
