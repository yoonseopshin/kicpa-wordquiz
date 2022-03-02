package com.cpa.cpa_word_problem.initializers

import android.content.Context
import androidx.startup.Initializer
import com.cpa.cpa_word_problem.BuildConfig
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()

}