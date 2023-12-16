package com.ysshin.cpaquiz.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {

    override val isOnline: Flow<Boolean> = callbackFlow {
        val connectivityManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

        val callback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(connectivityManager.isCurrentConnected())
            }

            override fun onLost(network: Network) {
                trySend(connectivityManager.isCurrentConnected())
            }

            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                trySend(connectivityManager.isCurrentConnected())
            }
        }

        connectivityManager?.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            callback,
        )

        trySend(connectivityManager.isCurrentConnected())

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }

    private fun ConnectivityManager?.isCurrentConnected() = when (this) {
        null -> false
        else ->
            activeNetwork
                ?.let(::getNetworkCapabilities)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                ?: false
    }
}
