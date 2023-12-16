package com.ysshin.cpaquiz.core.android.util

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.colorStateList(@ColorRes resId: Int) = ColorStateList.valueOf(ContextCompat.getColor(this, resId))

fun Context.color(@ColorRes resId: Int) = ContextCompat.getColor(this, resId)
