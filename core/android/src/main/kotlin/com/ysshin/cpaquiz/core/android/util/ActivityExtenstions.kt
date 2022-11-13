package com.ysshin.cpaquiz.core.android.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ysshin.cpaquiz.core.android.base.BaseActivity

fun Activity.hideKeyboard() {
    val imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    } ?: run {
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
    }
}

fun Context.findActivity(): BaseActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is BaseActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Fail to find Activity from Context")
}
