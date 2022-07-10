package com.ysshin.cpaquiz.shared.android.bridge

import android.content.Context
import android.content.Intent
import com.ysshin.cpaquiz.domain.model.QuizType

interface ProblemDetailNavigator {
    fun quizIntent(context: Context, quizType: QuizType, quizNumbers: Int, useTimer: Boolean): Intent
}
