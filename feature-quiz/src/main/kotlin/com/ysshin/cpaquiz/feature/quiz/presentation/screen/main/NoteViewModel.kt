package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.shared.base.Result
import com.ysshin.cpaquiz.shared.base.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
) : BaseViewModel() {

    private val totalProblems = problemUseCases.getTotalProblems().asResult()

    private val wrongProblems = problemUseCases.getWrongProblems().asResult()

    private val userInputText = MutableStateFlow("")
    private val isSearching get() = userInputText.value.isNotBlank()

    private val noteFilter = MutableStateFlow(NoteFilter())

    private val _showWrongNoteHeader = MutableStateFlow(false)
    val showWrongNoteHeader = _showWrongNoteHeader.asStateFlow()

    private val _showScrollToTop = MutableStateFlow(false)
    val showScrollToTop = _showScrollToTop.asStateFlow()

    private val _isYearFiltering = MutableStateFlow(false)
    val isYearFiltering = _isYearFiltering.asStateFlow()

    private val _isQuizTypeFiltering = MutableStateFlow(false)
    val isQuizTypeFiltering = _isQuizTypeFiltering.asStateFlow()

    private val _uiEvent = MutableSharedFlow<NoteUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    val uiState: StateFlow<NoteUiState> =
        combine(
            totalProblems,
            wrongProblems,
            userInputText,
            noteFilter
        ) { totalResult, wrongResult, userInput, filter ->
            var totalAndWrongProblemSize = 0

            val totalProblemsUiState = if (totalResult is Result.Success) {
                val filtered = totalResult.data.filter { problem ->
                    problem.year in filter.years && problem.type in filter.types
                }
                totalAndWrongProblemSize += filtered.size
                TotalProblemsUiState.Success(filtered)
            } else {
                TotalProblemsUiState.Error
            }

            val wrongProblemsUiState = if (wrongResult is Result.Success) {
                val filtered = wrongResult.data.filter { problem ->
                    problem.year in filter.years && problem.type in filter.types
                }
                totalAndWrongProblemSize += filtered.size
                WrongProblemsUiState.Success(filtered)
            } else {
                WrongProblemsUiState.Error
            }

            val searched = problemUseCases.searchProblems(userInput)
            val searchedProblemsUiState = SearchedProblemsUiState.Success(searched)

            val userActionUiState = if (isSearching) {
                _showScrollToTop.update { searched.size > 15 }
                _showWrongNoteHeader.update { false }
                UserActionUiState.OnSearching
            } else {
                _showScrollToTop.update { totalAndWrongProblemSize > 15 }
                _showWrongNoteHeader.update {
                    (wrongProblemsUiState is WrongProblemsUiState.Success && wrongProblemsUiState.data.isNotEmpty())
                }
                UserActionUiState.OnViewing
            }

            NoteUiState(
                totalProblemsUiState,
                wrongProblemsUiState,
                searchedProblemsUiState,
                userActionUiState
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NoteUiState(
                    TotalProblemsUiState.Loading,
                    WrongProblemsUiState.Loading,
                    SearchedProblemsUiState.Loading,
                    UserActionUiState.OnViewing
                )
            )

    val selectableFilteredYears
        get() = Problem.allYears().map {
            SelectableTextItem(
                text = it.toString(),
                isSelected = noteFilter.value.years.contains(it)
            )
        }

    val selectableFilteredTypes
        get() = QuizType.all().map {
            SelectableTextItem(
                text = it.toKorean(),
                isSelected = noteFilter.value.types.contains(it)
            )
        }

    fun setFilter(years: List<Int> = noteFilter.value.years, types: List<QuizType> = noteFilter.value.types) {
        noteFilter.update { NoteFilter(years, types) }
        _isYearFiltering.update { Problem.allYears().size != years.size }
        _isQuizTypeFiltering.update { QuizType.all().size != types.size }
    }

    fun updateUserInput(text: String) {
        userInputText.update { text }
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _uiEvent.emit(NoteUiEvent.ShowSnackbar(message))
        }
    }
}

sealed interface TotalProblemsUiState {
    data class Success(val data: List<Problem>) : TotalProblemsUiState
    object Error : TotalProblemsUiState
    object Loading : TotalProblemsUiState
}

sealed interface WrongProblemsUiState {
    data class Success(val data: List<Problem>) : WrongProblemsUiState
    object Error : WrongProblemsUiState
    object Loading : WrongProblemsUiState
}

sealed interface SearchedProblemsUiState {
    data class Success(val data: List<Problem>) : SearchedProblemsUiState
    object Error : SearchedProblemsUiState
    object Loading : SearchedProblemsUiState
}

sealed interface UserActionUiState {
    object OnViewing : UserActionUiState
    object OnSearching : UserActionUiState
}

data class NoteFilter(val years: List<Int> = Problem.allYears(), val types: List<QuizType> = QuizType.all())

data class NoteUiState(
    val totalProblemsUiState: TotalProblemsUiState,
    val wrongProblemsUiState: WrongProblemsUiState,
    val searchedProblemsUiState: SearchedProblemsUiState,
    val userActionUiState: UserActionUiState,
)

sealed interface NoteUiEvent {
    data class ShowSnackbar(val message: String) : NoteUiEvent
}
