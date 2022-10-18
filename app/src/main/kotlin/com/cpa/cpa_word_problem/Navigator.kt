package com.cpa.cpa_word_problem

import android.content.Context
import android.content.Intent
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity

class Navigator(private val context: Context) : QuizNavigationActions {

    override fun onQuizStart(quizType: QuizType, quizNumbers: Int, useTimer: Boolean) {
        context.startActivity(
            ProblemDetailActivity.newIntent(
                context = context,
                quizType = quizType,
                quizNumbers = quizNumbers,
                useTimer = useTimer,
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}
