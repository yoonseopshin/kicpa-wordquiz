package com.ysshin.cpaquiz.core.android.base

import androidx.appcompat.app.AppCompatActivity
import com.ysshin.cpaquiz.core.android.util.hideKeyboard

abstract class BaseActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }
}
