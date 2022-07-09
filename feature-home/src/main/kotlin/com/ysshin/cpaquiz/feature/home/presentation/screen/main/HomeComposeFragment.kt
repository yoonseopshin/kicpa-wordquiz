package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeScreen
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeComposeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setContent {
            HomeScreen(viewModel = viewModel)
        }
    }

}