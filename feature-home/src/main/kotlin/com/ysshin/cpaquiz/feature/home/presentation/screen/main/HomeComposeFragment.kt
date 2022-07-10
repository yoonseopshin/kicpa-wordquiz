package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.feature.home.presentation.ui.HomeScreen
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeComposeFragment : BaseFragment() {

    @Inject
    lateinit var problemDetailNavigator: ProblemDetailNavigator

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ComposeView(requireContext()).apply {
        setContent {
            HomeScreen(navigator = problemDetailNavigator, viewModel = viewModel)
        }
    }

}