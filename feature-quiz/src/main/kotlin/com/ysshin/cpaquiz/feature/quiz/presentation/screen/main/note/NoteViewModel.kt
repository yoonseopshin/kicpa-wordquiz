package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
