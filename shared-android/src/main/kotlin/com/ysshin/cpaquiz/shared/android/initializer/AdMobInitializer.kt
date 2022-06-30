package com.ysshin.cpaquiz.shared.android.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds

object AdMob {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(AdMobInitializer::class.java)
    }
}

class AdMobInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        MobileAds.initialize(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
