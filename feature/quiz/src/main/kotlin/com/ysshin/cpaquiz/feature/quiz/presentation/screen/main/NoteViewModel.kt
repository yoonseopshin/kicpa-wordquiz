package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.core.common.Result
import com.ysshin.cpaquiz.core.common.asResult
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
) : BaseViewModel() {

    val searchKeyword = MutableStateFlow("")
    private val isSearching get() = searchKeyword.value.isNotBlank()

    private val _noteFilter = MutableStateFlow(NoteFilter.default())

    val uiState: StateFlow<NoteUiState> =
        combine(
            problemUseCases.getTotalProblems().asResult(),
            problemUseCases.getWrongProblems().asResult(),
            searchKeyword,
            _noteFilter,
        ) { totalResult, wrongResult, keyword, noteFilter ->
            val userActionUiState = if (isSearching) {
                val searchedProblems = problemUseCases.searchProblems(keyword)
                val searchedProblemsUiState = SearchedProblemsUiState.Success(searchedProblems)
                UserActionUiState.Search(keyword, searchedProblemsUiState)
            } else {
                val totalProblemsUiState = if (totalResult is Result.Success) {
                    val filteredProblems = totalResult.data.filter { problem ->
                        problem.year in noteFilter.years && problem.type in noteFilter.types
                    }
                    TotalProblemsUiState.Success(filteredProblems)
                } else {
                    TotalProblemsUiState.Error
                }

                val wrongProblemsUiState = if (wrongResult is Result.Success) {
                    val filteredProblems = wrongResult.data.filter { problem ->
                        problem.year in noteFilter.years && problem.type in noteFilter.types
                    }
                    WrongProblemsUiState.Success(filteredProblems)
                } else {
                    WrongProblemsUiState.Error
                }
                UserActionUiState.View(noteFilter, totalProblemsUiState, wrongProblemsUiState)
            }

            NoteUiState(userActionUiState)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NoteUiState(
                    UserActionUiState.View(
                        NoteFilter.default(),
                        TotalProblemsUiState.Loading,
                        WrongProblemsUiState.Loading
                    )
                )
            )

    val selectableFilteredYears
        get() = Problem.allYears().map {
            SelectableTextItem(
                text = it.toString(),
                isSelected = _noteFilter.value.years.contains(it)
            )
        }

    val selectableFilteredTypes
        get() = QuizType.all().map {
            SelectableTextItem(
                text = it.toKorean(),
                isSelected = _noteFilter.value.types.contains(it)
            )
        }

    fun setFilter(
        years: List<Int> = _noteFilter.value.years,
        types: List<QuizType> = _noteFilter.value.types,
    ) {
        _noteFilter.update { NoteFilter(years, types) }
    }

    fun updateSearchKeyword(keyword: String) {
        searchKeyword.update { keyword }
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

    private val _isMenuOpened = mutableStateOf(false)
    val isMenuOpened: State<Boolean> get() = _isMenuOpened

    private val _noteMenuContentState: MutableState<NoteMenuContent> =
        mutableStateOf(NoteMenuContent.Search)
    val noteMenuContentState: State<NoteMenuContent>
        get() = _noteMenuContentState

    fun toggleMenu(newState: NoteMenuContent) {
        if (_isMenuOpened.value && _noteMenuContentState.value == newState) {
            hideMenu()
        } else {
            showMenu()
        }
        _noteMenuContentState.value = newState
    }

    fun hideMenu() {
        _isMenuOpened.value = false
    }

    private fun showMenu() {
        _isMenuOpened.value = true
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
    data class View(
        val filter: NoteFilter,
        val totalProblemsUiState: TotalProblemsUiState,
        val wrongProblemsUiState: WrongProblemsUiState,
    ) : UserActionUiState

    data class Search(
        val keyword: String,
        val searchedProblemsUiState: SearchedProblemsUiState,
    ) : UserActionUiState
}

data class NoteFilter(val years: List<Int>, val types: List<QuizType>) {
    companion object {
        fun default() = NoteFilter(years = Problem.allYears(), types = QuizType.all())
    }
}

internal fun NoteFilter.isYearFiltering() = years.size != Problem.allYears().size
internal fun NoteFilter.isQuizTypeFiltering() = types.size != QuizType.all().size
internal fun NoteFilter.isFiltering() = isYearFiltering() || isQuizTypeFiltering()

data class NoteUiState(
    val userActionUiState: UserActionUiState,
)

sealed interface NoteMenuContent {
    object Filter : NoteMenuContent
    object Search : NoteMenuContent
}
