package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.core.base.Result
import com.ysshin.cpaquiz.core.base.asResult
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
) : BaseViewModel() {

    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword = _searchKeyword.asStateFlow()

    private val _noteFilter = MutableStateFlow(NoteFilter.default())
    val noteFilter = _noteFilter.asStateFlow()

    val selectedQuestion: MutableStateFlow<Problem?> = MutableStateFlow(null)

    val noteUiState: StateFlow<NoteUiState> =
        combine(
            problemUseCases.getTotalProblems().asResult(),
            problemUseCases.getWrongProblems().asResult(),
            _searchKeyword,
            _noteFilter,
        ) { totalResult, wrongResult, searchKeyword, noteFilter ->
            val totalProblemsUiState = if (totalResult is Result.Success) {
                if (noteFilter.years.isEmpty() && noteFilter.types.isEmpty()) {
                    // NoteFilter first init
                    Timber.d("NoteFilter init")
                    val data = totalResult.data
                    val years = data.map { it.year }.distinct().sorted()
                    val types = data.map { it.type }.distinct()
                    _noteFilter.value = NoteFilter(years = years, types = types, yearsOfQuestions = years)
                }

                TotalProblemsUiState.Success(totalResult.data.filter(noteFilter::contains))
            } else {
                TotalProblemsUiState.Error
            }

            val wrongProblemsUiState = if (wrongResult is Result.Success) {
                WrongProblemsUiState.Success(wrongResult.data.filter(noteFilter::contains))
            } else {
                WrongProblemsUiState.Error
            }

            val searchedProblems = problemUseCases.searchProblems(searchKeyword)
            val searchedProblemsUiState = SearchedProblemsUiState.Success(searchedProblems)

            NoteUiState(totalProblemsUiState, wrongProblemsUiState, searchedProblemsUiState)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NoteUiState(
                    TotalProblemsUiState.Loading,
                    WrongProblemsUiState.Loading,
                    SearchedProblemsUiState.Loading,
                ),
            )

    val selectableFilteredYears
        get() = _noteFilter.value.yearsOfQuestions.map {
            SelectableTextItem(
                text = it.toString(),
                isSelected = _noteFilter.value.years.contains(it),
            )
        }

    val selectableFilteredTypes
        get() = QuizType.all().map {
            SelectableTextItem(
                text = it.toKorean(),
                isSelected = _noteFilter.value.types.contains(it),
            )
        }

    fun setFilter(years: List<Int>, types: List<QuizType>) {
        _noteFilter.value = _noteFilter.value.copy(
            years = years.ifEmpty { _noteFilter.value.years },
            types = types.ifEmpty { _noteFilter.value.types },
        )
    }

    fun clearFilter() {
        val origin = _noteFilter.value
        _noteFilter.value = NoteFilter(
            years = origin.yearsOfQuestions,
            types = QuizType.all(),
            yearsOfQuestions = origin.yearsOfQuestions,
        )
    }

    fun updateSearchKeyword(keyword: String) {
        _searchKeyword.update { keyword }
    }

    fun deleteTargetWrongProblem(problem: Problem) {
        viewModelScope.launch {
            problemUseCases.deleteWrongProblem(problem)
        }
        setSelectedQuestion(null)
    }

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
        }
        setSelectedQuestion(null)
    }

    fun setSelectedQuestion(problem: Problem?) {
        selectedQuestion.value = problem
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

data class NoteUiState(
    val totalProblemsUiState: TotalProblemsUiState,
    val wrongProblemsUiState: WrongProblemsUiState,
    val searchedProblemsUiState: SearchedProblemsUiState,
)

data class NoteFilter(val years: List<Int>, val types: List<QuizType>, val yearsOfQuestions: List<Int>) {
    companion object {
        fun default() = NoteFilter(years = emptyList(), types = emptyList(), yearsOfQuestions = emptyList())
    }
}

internal fun NoteFilter.contains(problem: Problem) = years.contains(problem.year) && types.contains(problem.type)

internal fun NoteFilter.isYearFiltering() = years.size != yearsOfQuestions.size
internal fun NoteFilter.isQuizTypeFiltering() = types.size != QuizType.all().size
internal fun NoteFilter.isFiltering() = isYearFiltering() || isQuizTypeFiltering()

@Parcelize
sealed interface NoteMenuContent : Parcelable {

    @Parcelize
    object Filter : NoteMenuContent

    @Parcelize
    object Search : NoteMenuContent
}

@Parcelize
data class DeleteWrongProblemDialog(val isOpened: Boolean, val problem: ProblemModel) : Parcelable
