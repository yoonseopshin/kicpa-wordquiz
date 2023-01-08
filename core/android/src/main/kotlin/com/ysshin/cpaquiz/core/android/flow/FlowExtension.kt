package com.ysshin.cpaquiz.core.android.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectAsEffectWithLifecycle(
    vararg keys: Any?,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: suspend CoroutineScope.(T) -> Unit
) {
    val currentCollector by rememberUpdatedState(newValue = collector)
    LaunchedEffect(this, lifecycle, minActiveState, *keys) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            collect { currentCollector(it) }
        }
    }
}
