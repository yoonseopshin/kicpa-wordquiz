package com.cpa.cpa_word_problem.di

import android.content.Context
import com.cpa.cpa_word_problem.initializer.FlipperInitializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {

    fun inject(initializer: FlipperInitializer)

    companion object {
        fun resolve(context: Context): InitializerEntryPoint {
            return EntryPointAccessors.fromApplication(
                context.applicationContext,
                InitializerEntryPoint::class.java
            )
        }
    }

}