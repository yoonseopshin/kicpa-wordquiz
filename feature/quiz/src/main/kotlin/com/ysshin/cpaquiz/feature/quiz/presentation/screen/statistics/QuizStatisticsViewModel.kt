package com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.DEFAULT_SOLVED_QUIZ
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.WrongProblem
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class QuizStatisticsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    quizUseCases: QuizUseCases,
) : BaseViewModel() {

    private val wrongProblems = mutableListOf<WrongProblem>()

    val solvedQuiz = quizUseCases.getSolvedQuiz()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = DEFAULT_SOLVED_QUIZ
        )

    val shouldShowInAppReview = quizUseCases.getShouldRequestInAppReview()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    fun shouldShowAd(solvedQuiz: Int): Boolean {
        return solvedQuiz > 0 && solvedQuiz % SOLVED_QUIZ_THRESHOLD == 0
    }

    fun setWrongProblems(problems: List<Problem>, selected: List<Int>) {
        wrongProblems.clear()
        val size = problems.size
        for (i in 0 until size) {
            if (selected[i] != problems[i].answer) {
                wrongProblems.add(WrongProblem.from(problems[i]))
            }
        }

        problemUseCases.insertWrongProblems(wrongProblems, viewModelScope)
    }

    companion object {
        const val SOLVED_QUIZ_THRESHOLD = 5
    }
}
