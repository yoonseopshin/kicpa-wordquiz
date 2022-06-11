package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.android.ui.dialog.SelectableTextItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
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
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _problems: MutableStateFlow<List<Problem>> = MutableStateFlow(emptyList())
    val problems = _problems.asStateFlow()

    val wrongProblems = problemUseCases.getWrongProblems()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _filteredYears = MutableStateFlow(Problem.allYears())
    val filteredYears = _filteredYears.asStateFlow()
    private val _filteredTypes = MutableStateFlow(QuizType.all())
    val filteredTypes = _filteredTypes.asStateFlow()

    private val _isYearFiltering = MutableStateFlow(false)
    val isYearFiltering: StateFlow<Boolean> = _isYearFiltering

    private val _isQuizTypeFiltering = MutableStateFlow(false)
    val isQuizTypeFiltering: StateFlow<Boolean> = _isQuizTypeFiltering

    fun getSelectableFilteredYears() =
        Problem.allYears().map {
            SelectableTextItem(
                text = it.toString(),
                isSelected = _filteredYears.value.contains(it)
            )
        }

    fun getSelectableFilteredTypes() =
        QuizType.all().map {
            SelectableTextItem(
                text = it.toKorean(),
                isSelected = _filteredTypes.value.contains(it)
            )
        }

    fun setFilteredYears(years: List<Int>) {
        _filteredYears.update { years }
        _isYearFiltering.update { Problem.allYears().size != years.size }
    }

    fun setFilteredTypes(types: List<QuizType>) {
        _filteredTypes.update { types }
        _isQuizTypeFiltering.update { QuizType.all().size != types.size }
    }

    fun clearProblemFilter() {
        setFilteredYears(Problem.allYears())
        setFilteredTypes(QuizType.all())
    }

    fun applyProblemFilter() {
        problemUseCases.getLocalProblems(
            years = filteredYears.value,
            types = filteredTypes.value,
            scope = viewModelScope
        ) {
            _problems.value = it
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
