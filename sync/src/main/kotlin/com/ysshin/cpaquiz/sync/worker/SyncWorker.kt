package com.ysshin.cpaquiz.sync.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import com.ysshin.cpaquiz.domain.usecase.config.ConfigUseCases
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.sync.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val SYNC_NOTIFICATION_ID = 1996
private const val SYNC_NOTIFICATION_CHANNEL = "SyncNotificationChannel"

private val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val problemUseCases: ProblemUseCases,
    private val configUseCases: ConfigUseCases,
) : CoroutineWorker(context, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return context.syncForegroundInfo()
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        problemUseCases.syncRemoteProblems()
        configUseCases.syncConfigs()
        Result.success()
    }

    companion object {
        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }
}

private fun Context.syncForegroundInfo() = ForegroundInfo(
    SYNC_NOTIFICATION_ID,
    syncWorkNotification(),
)

private fun Context.syncWorkNotification(): Notification {
    val channel = NotificationChannel(
        SYNC_NOTIFICATION_CHANNEL,
        getString(R.string.sync_data),
        NotificationManager.IMPORTANCE_DEFAULT,
    )

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    return NotificationCompat.Builder(
        this,
        SYNC_NOTIFICATION_CHANNEL,
    )
        .setSmallIcon(R.drawable.ic_done)
        .setContentTitle(getString(R.string.sync_data_successfully))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
