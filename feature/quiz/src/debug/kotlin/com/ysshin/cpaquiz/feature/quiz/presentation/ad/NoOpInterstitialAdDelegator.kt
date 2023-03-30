package com.ysshin.cpaquiz.feature.quiz.presentation.ad

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber

@Singleton
class NoOpInterstitialAdDelegator @Inject constructor() : InterstitialAdDelegator {

    override fun show(activity: BaseActivity) {
        Timber.d("show interstitial ad")
    }
}
