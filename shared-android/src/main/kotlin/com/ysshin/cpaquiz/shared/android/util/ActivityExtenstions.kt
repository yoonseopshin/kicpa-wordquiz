package com.ysshin.cpaquiz.shared.android.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.internal.managers.ViewComponentManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    } ?: run {
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }
}

fun Context.findActivity(): Activity? {
    return when (this) {
        is ViewComponentManager.FragmentContextWrapper -> baseContext.findActivity()
        is ContextWrapper -> baseContext.findActivity()
        is Activity -> this
        else -> null
    }
}
