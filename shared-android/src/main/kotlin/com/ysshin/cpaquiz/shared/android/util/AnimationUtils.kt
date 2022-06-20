package com.ysshin.cpaquiz.shared.android.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import com.ysshin.cpaquiz.shared.base.Consumer

fun animateColorChange(startColor: Int, endColor: Int, onColorChange: Consumer<Int>) {
    ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
        duration = 200L
        addUpdateListener {
            onColorChange(it.animatedValue as Int)
        }
    }.start()
}
