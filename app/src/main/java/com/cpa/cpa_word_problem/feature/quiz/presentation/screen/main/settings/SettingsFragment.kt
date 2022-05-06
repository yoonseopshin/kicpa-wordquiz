package com.cpa.cpa_word_problem.feature.quiz.presentation.screen.main.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ysshin.shared.base.BaseFragment
import com.cpa.cpa_word_problem.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(layoutInflater).also {
        _binding = it
        // TODO: Set ViewModel
    }.root

}