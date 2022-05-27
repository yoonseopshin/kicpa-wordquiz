package com.ysshin.cpaquiz.feature.settings.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.ysshin.cpaquiz.feature.settings.presentation.ui.SettingsScreen
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            ComposeView(requireContext()).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    SettingsScreen()
                }
            }
}