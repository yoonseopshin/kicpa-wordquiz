package com.cpa.cpa_word_problem.feature.settings.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cpa.cpa_word_problem.databinding.FragmentSettingsBinding
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
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