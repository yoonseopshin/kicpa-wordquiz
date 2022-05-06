package com.ysshin.shared.util

import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnThrottleClickListener(
    private val dispatcher: CoroutineDispatcher,
    private val onClickListener: View.OnClickListener,
    private val interval: Long,
) : View.OnClickListener {

    private var isClickable = true

    override fun onClick(view: View) {
        if (isClickable) {
            isClickable = false
            view.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
                lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
                    onClickListener.onClick(view)
                    delay(interval)
                    isClickable = true
                }
            }
        }
    }
}