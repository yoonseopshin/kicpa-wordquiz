package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.ysshin.cpaquiz.core.android.base.BaseActivity
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
            quizType: QuizType,
            quizNumbers: Int,
            useTimer: Boolean = false,
            selectedSubtypes: List<String> = emptyList(),
        ) = Intent(context, QuizActivity::class.java).apply {
            putExtra(QuizConstants.QUIZ_TYPE, quizType)
            putExtra(QuizConstants.QUIZ_NUMBERS, quizNumbers)
            putExtra(QuizConstants.USE_TIMER, useTimer)
            putStringArrayListExtra(QuizConstants.SELECTED_SUBTYPES, ArrayList(selectedSubtypes))
        }
    }
}
