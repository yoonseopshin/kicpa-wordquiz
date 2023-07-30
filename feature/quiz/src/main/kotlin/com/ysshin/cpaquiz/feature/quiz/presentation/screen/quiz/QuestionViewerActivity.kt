package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.QuestionViewerScreen
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionViewerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuestionViewerScreen()
        }
    }

    companion object {
        fun newIntent(
            context: Context,
            mode: ProblemDetailMode = ProblemDetailMode.Detail,
            problemModel: ProblemModel? = null,
        ) = Intent(context, QuestionViewerActivity::class.java).apply {
            putExtra(QuizConstants.mode, mode)
            problemModel?.let { problemModel -> putExtra(QuizConstants.problemModel, problemModel) }
        }
    }
}
