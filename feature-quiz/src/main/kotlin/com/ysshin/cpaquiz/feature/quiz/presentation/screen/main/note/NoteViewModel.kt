package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
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

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _problems: MutableStateFlow<List<Problem>> = MutableStateFlow(emptyList())
    val problems = _problems.asStateFlow()

    private val _wrongProblems = problemUseCases.getWrongProblems()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _filteredYears = MutableStateFlow(Problem.allYears())
    private val _filteredTypes = MutableStateFlow(QuizType.all())

    val filteredWrongProblems =
        combine(_wrongProblems, _filteredYears, _filteredTypes) { wrongProblems, filteredYears, filteredTypes ->
            wrongProblems.filter { problem ->
                problem.year in filteredYears && problem.type in filteredTypes
            }.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _isYearFiltering = MutableStateFlow(false)
    val isYearFiltering: StateFlow<Boolean> = _isYearFiltering

    private val _isQuizTypeFiltering = MutableStateFlow(false)
    val isQuizTypeFiltering: StateFlow<Boolean> = _isQuizTypeFiltering

    fun initProblems() {
        if (_problems.value.isEmpty()) {
            problemUseCases.getLocalProblems(scope = viewModelScope) {
                _problems.value = it
            }
        }
    }

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
            years = _filteredYears.value,
            types = _filteredTypes.value,
            scope = viewModelScope
        ) {
            _problems.value = it
        }
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
