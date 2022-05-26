package com.ysshin.cpaquiz.shared.android.base

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ysshin.cpaquiz.shared.android.util.color
import com.ysshin.cpaquiz.shared.android.util.colorStateList

abstract class BaseFragment : Fragment() {

    fun color(@ColorRes resId: Int) = requireContext().colorStateList(resId)

    fun colorAsInt(@ColorRes resId: Int) = requireContext().color(resId)

}