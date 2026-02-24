package com.aryan.reader.feedback

import android.content.Context
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.aryan.reader.BuildConfig
import com.aryan.reader.data.FeedbackRepository
import com.aryan.reader.data.FeedbackTextPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.core.net.toUri
import com.aryan.reader.AuthRepository

class FeedbackWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val feedbackRepository = FeedbackRepository(applicationContext)

    private val authRepository = AuthRepository(applicationContext)

    override suspend fun doWork(): Result {
        val message = inputData.getString(KEY_MESSAGE) ?: return Result.failure()
        val category = inputData.getString(KEY_CATEGORY) ?: return Result.failure()
        val imageUris = inputData.getStringArray(KEY_IMAGE_URIS)?.map { it.toUri() } ?: emptyList()

        val userId = authRepository.getSignedInUser()?.uid ?: "Not signed in"

        val contextMap = mapOf(
            "appVersion" to BuildConfig.VERSION_NAME,
            "deviceModel" to "${Build.MANUFACTURER} ${Build.MODEL}",
            "androidVersion" to Build.VERSION.SDK_INT.toString(),
            "userId" to userId
        )

        val payload = FeedbackTextPayload(message, category, contextMap)

        return withContext(Dispatchers.IO) {
            val textResult = feedbackRepository.sendFeedbackText(payload)
            val response = textResult.getOrNull()

            if (textResult.isFailure || response?.status != "success" || response.thread_ts == null) {
                return@withContext if (runAttemptCount < 3) Result.retry() else Result.failure()
            }

            val threadTs = response.thread_ts

            imageUris.forEach { uri ->
                val imageResult = feedbackRepository.sendFeedbackImage(uri, threadTs)
                if (imageResult.isFailure) {
                    return@withContext Result.failure(workDataOf("error" to "Image upload failed"))
                }
            }

            Result.success()
        }
    }

    companion object {
        const val KEY_MESSAGE = "KEY_MESSAGE"
        const val KEY_CATEGORY = "KEY_CATEGORY"
        const val KEY_IMAGE_URIS = "KEY_IMAGE_URIS"
    }
}