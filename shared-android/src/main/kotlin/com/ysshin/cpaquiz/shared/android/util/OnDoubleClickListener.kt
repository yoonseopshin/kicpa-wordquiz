package com.ysshin.cpaquiz.shared.android.util

import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnDoubleClickListener(
    private val dispatcher: CoroutineDispatcher,
    private val onClickListener: View.OnClickListener,
    private val interval: Long,
) : View.OnClickListener {

    private var isBusy = false
    private var count = 0

    override fun onClick(view: View) {
        if (isBusy) {
            if (count == 1) {
                onClickListener.onClick(view)
            }
        }

        count++

        if (isBusy.not()) {
            isBusy = true

            view.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
                lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
                    delay(interval)
                    isBusy = false
                    count = 0
                }
            }
        }
    }
}
