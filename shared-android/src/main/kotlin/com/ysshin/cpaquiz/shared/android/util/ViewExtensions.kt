package com.ysshin.cpaquiz.shared.android.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlin.math.abs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

fun View.expand(duration: Long = 300L) {
    measure(
        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    slideView(measuredHeight, duration)
}

fun View.collapse(duration: Long = 300L, height: Int = 0) {
    slideView(height, duration)
}

fun View.slideView(newHeight: Int, duration: Long = 300L) {
    val slideAnimator = ValueAnimator
        .ofInt(height, newHeight)
        .setDuration(duration)

    slideAnimator.addUpdateListener {
        val currentHeight = it.animatedValue as Int
        layoutParams.height = currentHeight
        requestLayout()
    }

    with(slideAnimator) {
        interpolator = AccelerateDecelerateInterpolator()
        start()
    }
}

fun View.setOnThrottleClick(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    interval: Long = 500L,
    action: Consumer<View> = {}
) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnThrottleClickListener(dispatcher, listener, interval))
}

fun View.setOnDoubleClick(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    interval: Long = 500L,
    action: Consumer<View> = {}
) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnDoubleClickListener(dispatcher, listener, interval))
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visibleOrGone(isVisible: Boolean) {
    if (isVisible) visible() else gone()
}

fun View.visibleOrInvisible(isVisible: Boolean) {
    if (isVisible) visible() else invisible()
}

fun View.blink(backgroundColor: Int, animColor: Int, animDuration: Long = 500L) {
    ObjectAnimator.ofArgb(this, "backgroundColor", animColor, backgroundColor).apply {
        duration = animDuration
        start()
    }
}

private fun calculateRectOnScreen(view: View): Rect {
    val location = IntArray(2)
    view.getLocationOnScreen(location)
    return Rect(
        location[0],
        location[1],
        location[0] + view.measuredWidth,
        location[1] + view.measuredHeight
    )
}

private fun ScrollView.computeDistanceToView(view: View): Int {
    return abs(calculateRectOnScreen(this).top - (scrollY + calculateRectOnScreen(view).top))
}

fun ScrollView.scrollToView(view: View, maxDuration: Long = 500L) {
    if (height >= getChildAt(0).height) return

    val y = computeDistanceToView(view)
    val ratio = abs(y - scrollY) / (getChildAt(0).height - height).toFloat()

    ObjectAnimator.ofInt(this, "scrollY", y).apply {
        duration = (maxDuration * ratio).toLong()
    }.start()
}

fun EditText.showKeyboard() {
    requestFocus()
    (context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).also { imm ->
        imm.showSoftInput(
            this,
            0
        )
    }
}

fun Context.colorStateList(@ColorRes resId: Int) =
    ColorStateList.valueOf(ContextCompat.getColor(this, resId))

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)

inline fun <T> ViewGroup.inflate(inflater: (LayoutInflater, ViewGroup, Boolean) -> T) =
    inflater(LayoutInflater.from(context), this, false)

fun View.fadeOutAnimation(duration: Long = 300L, action: Action = {}) {
    animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            visibility = View.GONE
            action()
        }
}

fun View.fadeInAnimation(duration: Long = 300L, action: Action = {}) {
    alpha = 0f
    visibility = View.VISIBLE
    animate()
        .alpha(1f)
        .setDuration(duration)
        .withEndAction {
            action()
        }
}

fun TextView.setTextFadeAnimation(value: String, duration: Long = 300L, action: Action = {}) {
    val halfDuration = duration / 2
    fadeOutAnimation(halfDuration) {
        text = value
        fadeInAnimation(halfDuration) {
            action()
        }
    }
}
