package com.ysshin.shared.base

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.ysshin.shared.util.color
import com.ysshin.shared.util.colorStateList

abstract class BaseFragment : Fragment() {

    fun color(@ColorRes resId: Int) = requireContext().colorStateList(resId)

    fun colorAsInt(@ColorRes resId: Int) = requireContext().color(resId)

}