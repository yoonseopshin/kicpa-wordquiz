package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.shared.android.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteProblemViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    var targetYear = -1
    var targetPid = -1
    var targetType = QuizType.None

    fun deleteWrongProblem() {
        problemUseCases.deleteWrongProblem(targetYear, targetPid, targetType, viewModelScope)
    }

    fun deleteAllWrongProblems() {
        problemUseCases.deleteAllWrongProblems(viewModelScope)
    }
}
