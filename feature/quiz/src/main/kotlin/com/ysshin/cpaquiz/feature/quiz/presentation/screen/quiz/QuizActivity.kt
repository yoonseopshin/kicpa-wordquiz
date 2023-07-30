package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.QuizScreen
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuizScreen()
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            mode: ProblemDetailMode = ProblemDetailMode.Quiz,
            quizType: QuizType,
            quizNumbers: Int,
            useTimer: Boolean = false,
            selectedSubtypes: List<String> = emptyList(),
        ) = Intent(context, QuizActivity::class.java).apply {
            putExtra(QuizConstants.mode, mode)
            putExtra(QuizConstants.quizType, quizType)
            putExtra(QuizConstants.quizNumbers, quizNumbers)
            putExtra(QuizConstants.useTimer, useTimer)
            putStringArrayListExtra(QuizConstants.selectedSubtypes, ArrayList(selectedSubtypes))
        }
    }
}
