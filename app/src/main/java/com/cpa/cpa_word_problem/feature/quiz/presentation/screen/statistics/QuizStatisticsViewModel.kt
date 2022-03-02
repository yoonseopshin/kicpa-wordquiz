package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.statistics

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.WrongProblem
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizStatisticsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    private val wrongProblems = mutableListOf<WrongProblem>()

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

}