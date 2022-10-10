package com.ysshin.cpaquiz.core.android.util

import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

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

inline fun <reified T : Fragment> newInstance(vararg params: Pair<String, Any>): T =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }

fun <T : ViewModel> Fragment.obtainSharedViewModel(
    owner: ViewModelStoreOwner,
    viewModelClass: Class<T>,
    viewModelFactory: ViewModelProvider.Factory = defaultViewModelProviderFactory
) = ViewModelProvider(owner, viewModelFactory)[viewModelClass]
