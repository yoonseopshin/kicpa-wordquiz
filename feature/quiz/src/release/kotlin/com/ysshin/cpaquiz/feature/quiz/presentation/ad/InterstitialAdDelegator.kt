package com.ysshin.cpaquiz.feature.quiz.presentation.ad

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.core.android.util.AdConstants

class InterstitialAdDelegator(private val activity: BaseActivity): InterstitialAdDelegate {

    private var interstitialAd: InterstitialAd? = null

    override fun show() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            AdConstants.QUIZ_END_INTERSTITIAL_AD,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    ad.show(activity)
                }
            }
        )

        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitialAd = null
            }
        }
    }

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                super.onDestroy(owner)
                interstitialAd = null
            }
        })
    }
}
