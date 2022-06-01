package com.ysshin.cpaquiz.feature.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.cpa.cpa_word_problem.worker.DelegatingWorker
import com.cpa.cpa_word_problem.worker.delegatedData
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val problemUseCases: ProblemUseCases
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        // TODO: sync가 실패할 경우 어떻게 할지 적절한 처리를 해주어야 함.
        // Retry를 몇번할지
        // Fail의 경우 어떤 처리를 보여줄지
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