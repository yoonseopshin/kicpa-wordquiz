package com.ysshin.cpaquiz.feature.sync.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.ysshin.cpaquiz.feature.sync.worker.SyncWorker

object Sync {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(SyncInitializer::class.java)
    }
}

private const val SyncWorkName = "SyncWorkName"

class SyncInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SyncWorkName,
                ExistingWorkPolicy.REPLACE,
                SyncWorker.startUpSyncWork()
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(WorkManagerInitializer::class.java)
}
