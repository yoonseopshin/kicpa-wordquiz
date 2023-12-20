package com.ysshin.cpaquiz.domain.usecase.config

import com.ysshin.cpaquiz.domain.repository.ConfigRepository

class SyncConfigs(private val repository: ConfigRepository) {

    suspend operator fun invoke() {
        repository.syncConfigs()
    }
}