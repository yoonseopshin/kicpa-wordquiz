package com.ysshin.cpaquiz.feature.sync.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import com.ysshin.cpaquiz.shared.android.R
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
        // TODO: sync가 실패할 경우 어떻게 할지 적절한 처리를 해주어야 함.
        // Retry를 몇번할지
        // Fail의 경우 어떤 처리를 보여줄지

        problemUseCases.syncRemoteProblems()
        // TODO: Quiz dday도 로컬에 저장하고 업데이트 하는 형식으로 할까?
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
        "CPA 데이터 동기화",
        NotificationManager.IMPORTANCE_DEFAULT
    )

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    return NotificationCompat.Builder(
        this,
        SyncNotificationChannelID
    )
        .setSmallIcon(R.drawable.ic_done)
        .setContentTitle("CPA 문제를 업데이트했습니다.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()
}
