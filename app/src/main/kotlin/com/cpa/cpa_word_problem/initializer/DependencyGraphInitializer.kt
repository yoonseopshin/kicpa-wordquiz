package com.cpa.cpa_word_problem.initializer

import android.content.Context
import androidx.startup.Initializer
import com.cpa.cpa_word_problem.di.InitializerEntryPoint

class DependencyGraphInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
