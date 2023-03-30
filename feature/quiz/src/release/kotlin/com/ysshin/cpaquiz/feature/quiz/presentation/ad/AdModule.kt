package com.ysshin.cpaquiz.feature.quiz.presentation.ad

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AdModule {
    @Binds
    abstract fun bindsInterstitialAdDelegator(
        interstitialAdDelegatorImpl: InterstitialAdDelegatorImpl,
    ): InterstitialAdDelegator
}
