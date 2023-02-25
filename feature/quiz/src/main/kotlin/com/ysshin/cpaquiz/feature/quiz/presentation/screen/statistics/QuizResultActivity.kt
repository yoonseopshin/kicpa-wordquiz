package com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.util.AdConstants
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.QuizResultRoute
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizResultActivity : BaseActivity() {

    private val reviewManager: ReviewManager by lazy { ReviewManagerFactory.create(this) }
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CpaQuizTheme {
                QuizResultRoute(
                    requestInAppReview = this::showInAppReview,
                    showInterstitialAd = this::showInterstitialAd
                )
            }
        }
    }

    private fun showInAppReview() {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener {
            if (it.isSuccessful) {
                val reviewInfo = it.result
                reviewManager.launchReviewFlow(this, reviewInfo)
            }
        }
    }

    private fun showInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            AdConstants.QUIZ_END_INTERSTITIAL_AD,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    ad.show(this@QuizResultActivity)
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

    override fun onDestroy() {
        super.onDestroy()
        interstitialAd = null
    }

    companion object {
        fun newIntent(
            context: Context,
            problems: List<ProblemModel> = listOf(),
            selected: List<Int> = listOf(),
            timesPerQuestion: List<Long> = listOf(),
        ) = Intent(context, QuizResultActivity::class.java).apply {
            putParcelableArrayListExtra(QuizConstants.problems, ArrayList(problems))
            putExtra(QuizConstants.selected, ArrayList(selected))
            putExtra(QuizConstants.timesPerQuestion, timesPerQuestion.toLongArray())
        }
    }
}
