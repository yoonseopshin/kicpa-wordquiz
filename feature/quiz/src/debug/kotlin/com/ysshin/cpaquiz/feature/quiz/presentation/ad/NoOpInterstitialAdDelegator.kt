package com.ysshin.cpaquiz.feature.quiz.presentation.ad

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoOpInterstitialAdDelegator @Inject constructor() : InterstitialAdDelegator {

    override fun show(activity: BaseActivity) {
        Timber.d("show interstitial ad")
    }
}
