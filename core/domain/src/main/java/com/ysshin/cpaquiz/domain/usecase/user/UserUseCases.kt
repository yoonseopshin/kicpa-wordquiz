package com.ysshin.cpaquiz.domain.usecase.user

data class UserUseCases(
    val getPostNotification: GetPostNotification,
    val grantPostNotification: GrantPostNotification,
    val denyPostNotification: DenyPostNotification,
)
