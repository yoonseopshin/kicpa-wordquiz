package com.ysshin.cpaquiz.core.android.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer

object AdMob {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(AdMobInitializer::class.java)
    }
}

class AdMobInitializer : Initializer<Unit> {

    // Do nothing
    override fun create(context: Context) {}
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
