package com.ysshin.cpaquiz.feature.settings.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.feature.settings.R
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.core.android.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases
) : BaseViewModel() {

    private val _isDeleteWrongProblemDialogOpened = MutableStateFlow(false)
    val isDeleteWrongProblemDialogOpened = _isDeleteWrongProblemDialogOpened.asStateFlow()

    private val _isAppVersionDialogOpened = MutableStateFlow(false)
    val isAppVersionDialogOpened = _isAppVersionDialogOpened.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun updateDeleteWrongProblemDialogOpened(value: Boolean) {
        _isDeleteWrongProblemDialogOpened.update { value }
    }

    fun updateAppVersionDialogOpened(value: Boolean) {
        _isAppVersionDialogOpened.update { value }
    }

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            val isSuccess = problemUseCases.deleteAllWrongProblems.invoke()
            if (isSuccess) {
                _uiEvent.emit(
                    UiEvent.ShowSnackbar(
                        UiText.StringResource(resId = R.string.success_delete_all_wrong_note),
                        UiText.StringResource(resId = R.string.confirm)
                    )
                )
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: UiText, val actionLabel: UiText) : UiEvent()
    }
}
