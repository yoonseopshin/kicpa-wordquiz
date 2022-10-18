package com.ysshin.cpaquiz.sync.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val SyncNotificationId = 1996
private const val SyncNotificationChannelID = "SyncNotificationChannel"

private val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
) : CoroutineWorker(context, workerParams) {

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return context.syncForegroundInfo()
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        problemUseCases.syncRemoteProblems()
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
    SyncNotificationId,
    syncWorkNotification()
)

private fun Context.syncWorkNotification(): Notification {
    val channel = NotificationChannel(
        SyncNotificationChannelID,
        getString(R.string.sync_data),
        NotificationManager.IMPORTANCE_DEFAULT
    )

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    return NotificationCompat.Builder(
        this,
        SyncNotificationChannelID
    )
        .setSmallIcon(R.drawable.ic_done)
        .setContentTitle(getString(R.string.sync_data_successfully))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
