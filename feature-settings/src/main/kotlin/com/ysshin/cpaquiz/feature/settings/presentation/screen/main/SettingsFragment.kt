package com.ysshin.cpaquiz.feature.settings.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.ysshin.cpaquiz.feature.settings.R
import com.ysshin.cpaquiz.shared.android.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            setContent {
                // TODO : CPATheme을 만들고 다크모드를 적용해야 함
                MaterialTheme {
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            TopAppBar(title = {
                                Text(
                                    getString(R.string.settings),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            })
                        },
                        content = {
                            Text("Hello SettingsFragment Jetpack Compose!")
                            it.calculateTopPadding()
                        }
                    )
                }


            }
        }
}