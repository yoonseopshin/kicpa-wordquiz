package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.feature.quiz.presentation.ad.InterstitialAdDelegator
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.QuizResultRoute
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizResultActivity : BaseActivity() {

    private val reviewManager: ReviewManager by lazy { ReviewManagerFactory.create(this) }

    @Inject
    lateinit var interstitialAdDelegator: InterstitialAdDelegator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CpaQuizTheme {
                QuizResultRoute(
                    requestInAppReview = this::showInAppReview,
                    showInterstitialAd = { interstitialAdDelegator.show(this) },
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
