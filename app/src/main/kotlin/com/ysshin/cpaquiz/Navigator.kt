package com.ysshin.cpaquiz

import com.ysshin.cpaquiz.core.android.base.BaseActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActions
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuizActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult.QuizResultActivity

class Navigator : QuizStartNavigationActions, QuizEndNavigationActions {

    override fun onQuizStart(
        activity: BaseActivity,
        quizType: QuizType,
        quizNumbers: Int,
        useTimer: Boolean,
        selectedSubtypes: List<String>,
    ) {
        activity.startActivity(
            QuizActivity.newIntent(
                context = activity,
                quizType = quizType,
                quizNumbers = quizNumbers,
                useTimer = useTimer,
                selectedSubtypes = selectedSubtypes,
            )
        )
    }

    override fun onQuizEnd(
        activity: BaseActivity,
        problems: List<Problem>,
        selected: List<Int>,
        timesPerQuestion: List<Long>,
    ) {
        activity.startActivity(
            QuizResultActivity.newIntent(
                context = activity,
                problems = problems.toModel(),
                selected = selected,
                timesPerQuestion = timesPerQuestion,
            )
        )
        activity.finish()
    }
}
