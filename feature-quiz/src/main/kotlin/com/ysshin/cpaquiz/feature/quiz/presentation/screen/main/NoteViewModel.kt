package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.shared.android.util.UiText
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

    val userInputText = MutableStateFlow("")
    private val isSearching get() = userInputText.value.isNotBlank()

    private val noteFilter = MutableStateFlow(NoteFilter())

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
                UserActionUiState.OnSearching
            } else {
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

    fun showSnackbar(messageResId: Int, @StringRes actionLabelResId: Int = R.string.confirm) {
        viewModelScope.launch {
            _uiEvent.emit(
                NoteUiEvent.ShowSnackbar(
                    UiText.StringResource(resId = messageResId),
                    UiText.StringResource(resId = actionLabelResId)
                )
            )
        }
    }

    private val _isDeleteWrongProblemDialogOpened = MutableStateFlow(false)
    val isDeleteWrongProblemDialogOpened = _isDeleteWrongProblemDialogOpened.asStateFlow()
    private lateinit var targetProblem: Problem

    private val _isDeleteAllWrongProblemsDialogOpened = MutableStateFlow(false)
    val isDeleteAllWrongProblemsDialogOpened = _isDeleteAllWrongProblemsDialogOpened.asStateFlow()

    fun updateDeleteWrongProblemDialogOpened(value: Boolean, problem: Problem? = null) {
        _isDeleteWrongProblemDialogOpened.update { value }
        if (value && problem != null) {
            targetProblem = problem
        }
    }

    fun deleteTargetWrongProblem() {
        viewModelScope.launch {
            problemUseCases.deleteWrongProblem(targetProblem)
        }
    }

    fun updateDeleteAllWrongProblemsDialogOpened(value: Boolean) {
        _isDeleteAllWrongProblemsDialogOpened.update { value }
    }

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
        }
    }

    private val _isYearFilterDialogOpened = MutableStateFlow(false)
    val isYearFilterDialogOpened = _isYearFilterDialogOpened.asStateFlow()

    fun updateYearFilterDialogOpened(value: Boolean) {
        _isYearFilterDialogOpened.update { value }
    }

    private val _isQuizTypeFilterDialogOpened = MutableStateFlow(false)
    val isQuizTypeFilterDialogOpened = _isQuizTypeFilterDialogOpened.asStateFlow()

    fun updateQuizTypeFilterDialogOpened(value: Boolean) {
        _isQuizTypeFilterDialogOpened.update { value }
    }

    private val _bottomSheetContentState: MutableState<NoteBottomSheetContentState> =
        mutableStateOf(NoteBottomSheetContentState.None)
    val bottomSheetContentState: State<NoteBottomSheetContentState>
        get() = _bottomSheetContentState

    fun updateBottomSheetContentState(state: NoteBottomSheetContentState) {
        _bottomSheetContentState.value = state
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
    data class ShowSnackbar(val message: UiText, val actionLabel: UiText) : NoteUiEvent
}

sealed interface NoteBottomSheetContentState {
    object Filter : NoteBottomSheetContentState
    object Search : NoteBottomSheetContentState
    object None : NoteBottomSheetContentState
}
