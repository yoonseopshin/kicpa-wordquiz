package com.ysshin.shared.util

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val activity = requireActivity()
    val imm =
        activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    activity.currentFocus?.let {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    } ?: run {
        imm.hideSoftInputFromWindow(activity.window.decorView.rootView.windowToken, 0)
    }
}