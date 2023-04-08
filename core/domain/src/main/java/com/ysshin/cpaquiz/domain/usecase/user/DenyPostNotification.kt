package com.ysshin.cpaquiz.domain.usecase.user

import com.ysshin.cpaquiz.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DenyPostNotification(private val repository: UserRepository) {

    operator fun invoke(scope: CoroutineScope) = scope.launch { repository.denyPostNotification() }
}
