package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    val problems: StateFlow<List<Problem>> = problemUseCases.getLocalProblems()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val wrongProblems: StateFlow<List<Problem>> = problemUseCases.getWrongProblems()
        .flowOn(Dispatchers.IO)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

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

    fun deleteWrongProblem(year: Int, pid: Int, type: QuizType) {
        problemUseCases.deleteWrongProblem(year, pid, type, viewModelScope)
    }

    fun deleteAllWrongProblems() {
        problemUseCases.deleteAllWrongProblems(viewModelScope)
    }

    fun search(text: String) {
        viewModelScope.launch {
            _searchedProblems.value = problemUseCases.searchProblems(text)
        }
    }

    fun isSearching() = userInputText.value.isNotBlank()

}