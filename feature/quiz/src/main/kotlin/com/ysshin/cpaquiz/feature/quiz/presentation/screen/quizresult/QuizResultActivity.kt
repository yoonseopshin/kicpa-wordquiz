package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.feature.quiz.presentation.ad.InterstitialAdDelegator
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.QuizResultRoute
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizResultActivity : BaseActivity() {

    @Inject
    lateinit var interstitialAdDelegator: InterstitialAdDelegator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CpaQuizTheme {
                QuizResultRoute(
                    showInterstitialAd = { interstitialAdDelegator.show(this) },
                )
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
