package com.cpa.cpa_word_problem.initializers

import android.content.Context
import androidx.startup.Initializer
import com.google.android.gms.ads.MobileAds

class AdMobInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        MobileAds.initialize(context) { }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}