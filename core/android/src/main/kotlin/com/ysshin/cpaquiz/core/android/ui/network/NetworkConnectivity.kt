package com.ysshin.cpaquiz.core.android.ui.network

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.core.android.R
import kotlinx.coroutines.delay

@Composable
fun NetworkConnectivityStatusBox(isOffline: Boolean) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isOffline) {
        if (isOffline) {
            isVisible = true
        } else {
            delay(2000L)
            isVisible = false
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (isOffline) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        }
    )
    val backgroundTint by animateColorAsState(
        targetValue = if (isOffline) {
            MaterialTheme.colorScheme.onError
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
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
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = "Network connectivity",
                    tint = backgroundTint
                )
                Spacer(modifier = Modifier.size(8.dp))
                ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                    Text(text = stringResource(id = messageResId), color = backgroundTint)
                }
            }
        }
    }
}
