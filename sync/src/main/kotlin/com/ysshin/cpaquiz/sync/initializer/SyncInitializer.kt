package com.ysshin.cpaquiz.sync.initializer

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.ysshin.cpaquiz.sync.worker.SyncWorker

object Sync {
    fun initialize(context: Context) {
        AppInitializer.getInstance(context)
            .initializeComponent(SyncInitializer::class.java)
    }
}

private const val SYNC_WORK_NAME = "SyncWorkName"

class SyncInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        WorkManager.getInstance(context).apply {
            enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                SyncWorker.startUpSyncWork(),
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(WorkManagerInitializer::class.java)
}
