package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import android.os.Parcelable
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.core.common.Result
import com.ysshin.cpaquiz.core.common.asResult
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

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
            if (isSearching) {
                val searchedProblems = problemUseCases.searchProblems(keyword)
                val searchedProblemsUiState = SearchedProblemsUiState.Success(searchedProblems)
                NoteUiState.Search(keyword, searchedProblemsUiState)
            } else {
                val totalProblemsUiState = if (totalResult is Result.Success) {
                    TotalProblemsUiState.Success(totalResult.data.filter(noteFilter::contains))
                } else {
                    TotalProblemsUiState.Error
                }

                val wrongProblemsUiState = if (wrongResult is Result.Success) {
                    WrongProblemsUiState.Success(wrongResult.data.filter(noteFilter::contains))
                } else {
                    WrongProblemsUiState.Error
                }
                NoteUiState.View(noteFilter, totalProblemsUiState, wrongProblemsUiState)
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = NoteUiState.View(
                    NoteFilter.default(),
                    TotalProblemsUiState.Loading,
                    WrongProblemsUiState.Loading
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

    fun deleteTargetWrongProblem(problem: Problem) {
        viewModelScope.launch {
            problemUseCases.deleteWrongProblem(problem)
        }
    }

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
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

sealed interface NoteUiState {
    data class View(
        val filter: NoteFilter,
        val totalProblemsUiState: TotalProblemsUiState,
        val wrongProblemsUiState: WrongProblemsUiState,
    ) : NoteUiState

    data class Search(
        val keyword: String,
        val searchedProblemsUiState: SearchedProblemsUiState,
    ) : NoteUiState
}

data class NoteFilter(val years: List<Int>, val types: List<QuizType>) {
    companion object {
        fun default() = NoteFilter(years = Problem.allYears(), types = QuizType.all())
    }
}

internal fun NoteFilter.contains(problem: Problem) =
    years.contains(problem.year) && types.contains(problem.type)

internal fun NoteFilter.isYearFiltering() = years.size != Problem.allYears().size
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
