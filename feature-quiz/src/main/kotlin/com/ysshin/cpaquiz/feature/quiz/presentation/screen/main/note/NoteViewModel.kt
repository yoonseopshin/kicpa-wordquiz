package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.android.ui.dialog.SelectableTextItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    init {
        problemUseCases.getLocalProblems(scope = viewModelScope) {
            _problems.value = it
        }

        problemUseCases.getWrongProblems(scope = viewModelScope) {
            _wrongProblems.value = it
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _problems: MutableStateFlow<List<Problem>> = MutableStateFlow(emptyList())
    val problems = _problems.asStateFlow()

    private val _wrongProblems: MutableStateFlow<List<Problem>> = MutableStateFlow(emptyList())
    val wrongProblems = _wrongProblems.asStateFlow()

    private val filteredYears = Problem.allYears().toMutableList()
    private val filteredTypes = QuizType.all().toMutableList()

    fun getSelectableFilteredYears() =
        Problem.allYears().map {
            SelectableTextItem(
                text = it.toString(),
                isSelected = filteredYears.contains(it)
            )
        }

    fun getSelectableFilteredTypes() =
        QuizType.all().map {
            SelectableTextItem(
                text = it.toKorean(),
                isSelected = filteredTypes.contains(it)
            )
        }

    fun setFilteredYears(years: List<Int>) {
        filteredYears.clear()
        filteredYears.addAll(years)
    }

    fun setFilteredTypes(types: List<QuizType>) {
        filteredTypes.clear()
        filteredTypes.addAll(types)
    }

    fun clearProblemFilter() {
        setFilteredYears(Problem.allYears())
        setFilteredTypes(QuizType.all())
    }

    fun applyProblemFilter() {
        problemUseCases.getLocalProblems(years = filteredYears, types = filteredTypes, scope = viewModelScope) {
            _problems.value = it
        }
        problemUseCases.getWrongProblems(years = filteredYears, types = filteredTypes, scope = viewModelScope) {
            _wrongProblems.value = it
        }
    }

    val isWrongNoteOpened = MutableStateFlow(true)
    val isTotalNoteOpened = MutableStateFlow(true)

    fun toggleWrongNote() {
        isWrongNoteOpened.update { isOpened -> isOpened.not() }
    }

    fun toggleTotalNote() {
        isTotalNoteOpened.update { isOpened -> isOpened.not() }
    }

    private val _searchedProblems = MutableStateFlow(emptyList<Problem>())
    val searchedProblems = _searchedProblems.asStateFlow()

    val userInputText = MutableStateFlow("")
    val isSearching get() = userInputText.value.isNotBlank()

    fun search(text: String) {
        viewModelScope.launch {
            _searchedProblems.value = problemUseCases.searchProblems(text)
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.ShowSnackbar(message))
        }
    }
}
