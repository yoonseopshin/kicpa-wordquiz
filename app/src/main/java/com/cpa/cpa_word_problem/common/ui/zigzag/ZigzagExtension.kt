package com.cpa.cpa_word_problem.common.ui.zigzag

import android.animation.ObjectAnimator
import androidx.annotation.Keep

@Keep
fun ZigzagView.blinkZigzag(backgroundColor: Int, animColor: Int, animDuration: Long = 500L) {
    ObjectAnimator.ofArgb(this, "zigzagBackgroundColor", animColor, backgroundColor).apply {
        duration = animDuration
        start()
    }
}