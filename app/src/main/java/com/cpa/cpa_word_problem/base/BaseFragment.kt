package com.cpa.cpa_word_problem.base

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.utils.color
import com.cpa.cpa_word_problem.utils.colorAsInt

abstract class BaseFragment : Fragment() {

    fun color(@ColorRes resId: Int) = requireContext().color(resId)

    fun colorAsInt(@ColorRes resId: Int) = requireContext().colorAsInt(resId)

}