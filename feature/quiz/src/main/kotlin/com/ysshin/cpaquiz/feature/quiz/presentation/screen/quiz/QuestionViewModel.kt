package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.SavedStateHandle
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val handle: SavedStateHandle,
) : BaseViewModel() {

    val mode = handle.getStateFlow(QuizConstants.mode, ProblemDetailMode.Detail)

    private val _questions = mutableListOf<Problem>()
    val questions: List<Problem> = _questions

    private val _currentQuestion = MutableStateFlow(Problem.default())
    val currentQuestion = _currentQuestion.asStateFlow()

    private val _selected = mutableListOf<Int>()
    val selected: List<Int> = _selected

    init {
        handle.get<ProblemModel>(QuizConstants.problemModel)?.toDomain()?.let { question ->
            _currentQuestion.value = question
        }
    }
}
