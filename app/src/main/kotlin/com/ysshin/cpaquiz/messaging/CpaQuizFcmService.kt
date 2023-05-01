package com.ysshin.cpaquiz.messaging

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class CpaQuizFcmService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d("token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        // Handle received message
        Timber.d("message notification title: ${message.notification?.title}")
        Timber.d("message notification body: ${message.notification?.body}")
        Timber.d("message data: ${message.data}")
    }
}
