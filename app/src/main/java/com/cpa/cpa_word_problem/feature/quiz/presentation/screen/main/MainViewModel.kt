package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.cpa.cpa_word_problem.base.BaseViewModel
import com.cpa.cpa_word_problem.feature.quiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    fun syncRemoteProblems() {
        viewModelScope.launch {
            problemUseCases.syncRemoteProblems()
        }
    }

}