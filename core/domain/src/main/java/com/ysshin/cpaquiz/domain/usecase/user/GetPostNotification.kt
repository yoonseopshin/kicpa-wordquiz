package com.ysshin.cpaquiz.domain.usecase.user

import com.ysshin.cpaquiz.domain.repository.UserRepository

class GetPostNotification(private val repository: UserRepository) {

    operator fun invoke() = repository.getPostNotification()
}
