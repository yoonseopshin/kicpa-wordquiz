package com.cpa.cpa_word_problem.base

import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.cpa.cpa_word_problem.util.colorStateList
import com.cpa.cpa_word_problem.util.color

abstract class BaseFragment : Fragment() {

    fun color(@ColorRes resId: Int) = requireContext().colorStateList(resId)

    fun colorAsInt(@ColorRes resId: Int) = requireContext().color(resId)

}