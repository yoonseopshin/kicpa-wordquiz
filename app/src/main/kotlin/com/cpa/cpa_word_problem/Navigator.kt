package com.cpa.cpa_word_problem

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics.QuizStatisticsActivity

class Navigator : QuizStartNavigationActions, QuizEndNavigationActions {

    override fun onQuizStart(
        activity: BaseActivity,
        quizType: QuizType,
        quizNumbers: Int,
        useTimer: Boolean,
    ) {
        // FIXME: Migrate to Jetpack Compose, overall to be reworked
//        context.startActivity(
//            ProblemDetailActivity.newIntent(
//                context = context,
//                quizType = quizType,
//                quizNumbers = quizNumbers,
//                useTimer = useTimer,
//            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        )

        activity.startActivity(
            QuestionActivity.newIntent(
                context = activity,
                quizType = quizType,
                quizNumbers = quizNumbers,
                useTimer = useTimer,
            )
        )
    }

    override fun onQuizEnd(
        activity: BaseActivity,
        problems: List<Problem>,
        selected: List<Int>,
        timesPerProblem: List<Long>,
    ) {
        activity.startActivity(
            QuizStatisticsActivity.newIntent(
                context = activity,
                problems = problems.toModel(),
                selected = selected,
                timesPerProblem = timesPerProblem,
            )
        )
        activity.finish()
    }
}
