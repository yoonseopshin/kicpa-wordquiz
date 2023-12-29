package com.ysshin.cpaquiz.core.android.ui.network

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.theme.systemBarColor
import kotlinx.coroutines.delay

@Composable
fun NetworkConnectivityStatusBox(isOffline: Boolean, modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isOffline) {
        isVisible = if (isOffline) {
            true
        } else {
            delay(2000L)
            false
        }
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (isOffline) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        },
        label = "NetworkBackgroundColor",
    )
    val backgroundTint by animateColorAsState(
        targetValue = if (isOffline) {
            MaterialTheme.colorScheme.onError
        } else {
            MaterialTheme.colorScheme.onPrimary
        },
        label = "NetworkBackgroundTint",
    )
    val iconVector = if (isOffline) {
        Icons.Rounded.Warning
    } else {
        Icons.Rounded.CheckCircle
    }
    val messageResId = if (isOffline) {
        R.string.network_offline_message
    } else {
        R.string.network_online_message
    }

    StatusBarByNetworkConnectivity(isOffline = isOffline, isNetworkStatusVisible = isVisible)

    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        ),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        ),
    ) {
        Box(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CpaIcon(
                    icon = CpaIcon.ImageVectorIcon(iconVector),
                    contentDescription = "Network connectivity",
                    tint = backgroundTint,
                )
                Spacer(modifier = Modifier.size(8.dp))
                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                    Text(text = stringResource(id = messageResId), color = backgroundTint)
                }
            }
        }
    }
}

@Composable
private fun StatusBarByNetworkConnectivity(
    isOffline: Boolean,
    isNetworkStatusVisible: Boolean,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val statusBarBackgroundColor by animateColorAsState(
        targetValue = if (isOffline) {
            MaterialTheme.colorScheme.error
        } else {
            if (isNetworkStatusVisible) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.systemBarColor()
            }
        },
        label = "NetworkStatusBarBackgroundColor",
    )
    val isAppearanceLightStatusBars = if (isOffline) {
        isDarkTheme
    } else {
        isDarkTheme xor isNetworkStatusVisible.not()
    }

    val view = LocalView.current
    val activity = view.context as Activity
    val window = activity.window
    window.statusBarColor = statusBarBackgroundColor.toArgb()
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isAppearanceLightStatusBars
}
