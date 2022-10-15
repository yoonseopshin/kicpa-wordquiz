package com.ysshin.cpaquiz.core.android.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import com.ysshin.cpaquiz.core.common.Consumer

fun animateColorChange(startColor: Int, endColor: Int, onColorChange: Consumer<Int>) {
    ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
        duration = 200L
        addUpdateListener {
            onColorChange(it.animatedValue as Int)
        }
    }.start()
}
