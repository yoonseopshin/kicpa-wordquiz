package com.ysshin.cpaquiz.core.android.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> Flow<T>.collectAsEffect(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend (T) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEach(block).flowOn(context).launchIn(this)
    }
}
