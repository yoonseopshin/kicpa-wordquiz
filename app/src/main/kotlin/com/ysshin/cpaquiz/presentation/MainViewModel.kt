package com.ysshin.cpaquiz.presentation

import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
) : BaseViewModel() {

    val postNotification = userUseCases.getPostNotification()
        .map { PostNotificationUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = PostNotificationUiState.Loading,
        )

    fun grantPostNotification() {
        userUseCases.grantPostNotification(viewModelScope)
    }

    fun denyPostNotification() {
        userUseCases.denyPostNotification(viewModelScope)
    }
}

sealed interface PostNotificationUiState {
    object Loading : PostNotificationUiState
    data class Success(val data: Int) : PostNotificationUiState
}
