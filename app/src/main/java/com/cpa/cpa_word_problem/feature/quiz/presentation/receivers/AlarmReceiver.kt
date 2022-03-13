package com.cpa.cpa_word_problem.feature.quiz.presentation.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cpa.cpa_word_problem.feature.quiz.presentation.notifications.AlarmNotification

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification = AlarmNotification.createNotification(context)
        context.getSystemService(NotificationManager::class.java)?.notify(1, notification)
    }
}