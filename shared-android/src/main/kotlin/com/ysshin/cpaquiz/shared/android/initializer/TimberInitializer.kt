package com.ysshin.cpaquiz.shared.android.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import com.ysshin.cpaquiz.shared.android.BuildConfig
import timber.log.Timber

object Timber {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(TimberInitializer::class.java)
    }
}

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
