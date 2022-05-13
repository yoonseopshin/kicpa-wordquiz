package com.ysshin.cpaquiz.shared.android.util

import android.content.res.Resources

fun Int.toDp() = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx() = (this * Resources.getSystem().displayMetrics.density).toInt()