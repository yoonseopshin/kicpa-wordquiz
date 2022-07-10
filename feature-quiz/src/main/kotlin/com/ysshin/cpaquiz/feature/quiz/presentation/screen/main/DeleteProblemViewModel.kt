package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import com.ysshin.cpaquiz.shared.android.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DeleteProblemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    private val year = savedStateHandle.get<Int>(Constants.targetYear)
    private val pid = savedStateHandle.get<Int>(Constants.targetPid)
    private val type = savedStateHandle.get<QuizType>(Constants.targetType)

    fun deleteWrongProblem() {
        checkNotNull(year)
        checkNotNull(pid)
        checkNotNull(type)
        Timber.d("year: $year, pid: $pid, type: $type")

        viewModelScope.launch {
            problemUseCases.deleteWrongProblem(year, pid, type)
        }
    }

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
        }
    }
}
