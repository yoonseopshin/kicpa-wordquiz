@file:Suppress("ktlint:standard:property-naming")

package com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class QuestionViewerViewModel @Inject constructor(
    handle: SavedStateHandle,
) : BaseViewModel() {

    private val _totalQuestions = MutableStateFlow<List<Problem>>(emptyList())
    private val _currentQuestion = MutableStateFlow(Problem.default())

    val questionPagerUiState: StateFlow<QuestionPagerUiState> =
        _totalQuestions.zip(_currentQuestion) { totalQuestions, currentQuestion ->
            QuestionPagerUiState.Success(totalQuestions, totalQuestions.indexOf(currentQuestion))
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = QuestionPagerUiState.Loading,
            )

    init {
        handle.get<ProblemModel>(QuizConstants.PROBLEM_MODEL)?.toDomain()?.let { question ->
            _currentQuestion.value = question
        }

        handle.get<ArrayList<ProblemModel>>(QuizConstants.TOTAL_PROBLEM_MODELS)
            ?.map(ProblemModel::toDomain)
            ?.let { questions ->
                _totalQuestions.value = questions
            }
    }
}

sealed interface QuestionPagerUiState {
    object Loading : QuestionPagerUiState
    data class Success(val totalQuestions: List<Problem>, val currentPage: Int) :
        QuestionPagerUiState {
        fun getQuestion(page: Int) = totalQuestions[page]
        val pageCount: Int get() = totalQuestions.size
    }
}
