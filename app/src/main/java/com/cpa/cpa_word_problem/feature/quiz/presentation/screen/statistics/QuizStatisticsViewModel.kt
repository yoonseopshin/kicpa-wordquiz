package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.data.datasource.local.QuizDatastoreManager
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class QuizStatisticsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    quizDatastoreManager: QuizDatastoreManager,
) : BaseViewModel() {

    private val wrongProblems = mutableListOf<WrongProblem>()

    val solvedQuiz = quizDatastoreManager.solvedQuiz
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = QuizDatastoreManager.DEFAULT_SOLVED_QUIZ
        )

    val shouldShowInAppReview = quizDatastoreManager.shouldRequestInAppReview
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