package com.ysshin.cpaquiz.feature.settings.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
        }
    }
}
