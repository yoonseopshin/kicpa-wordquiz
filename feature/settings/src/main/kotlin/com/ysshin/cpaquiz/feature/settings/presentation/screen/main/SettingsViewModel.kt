package com.ysshin.cpaquiz.feature.settings.presentation.screen.main

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.AdType
import com.ysshin.cpaquiz.domain.usecase.config.ConfigUseCases
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    configUseCases: ConfigUseCases,
) : BaseViewModel() {

    val isSettingsNativeMediumAdEnabled = configUseCases.getAdConfig(AdType.SettingsNativeMediumAd)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = true,
        )

    fun deleteAllWrongProblems() {
        viewModelScope.launch {
            problemUseCases.deleteAllWrongProblems.invoke()
        }
    }
}
