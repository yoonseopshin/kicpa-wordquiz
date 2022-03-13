package com.cpa.cpa_word_problem.feature.quiz.presentation.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.MainActivity

object AlarmNotification {

    const val CHANNEL_ID = "cpa_word_quiz"
    const val REQUEST_CODE = 1996

    fun createNotification(context: Context): Notification {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(context.getString(R.string.app_name))
            setContentText("문제 풀 시간이에요.")
            setSmallIcon(R.mipmap.ic_launcher_foreground)
            setOngoing(true)
            setContentIntent(pendingIntent)
        }
            .build()
            .apply {
                flags = flags or Notification.FLAG_AUTO_CANCEL
            }

        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.app_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        context.getSystemService(NotificationManager::class.java)
            ?.createNotificationChannel(serviceChannel)

        return notification
    }

}