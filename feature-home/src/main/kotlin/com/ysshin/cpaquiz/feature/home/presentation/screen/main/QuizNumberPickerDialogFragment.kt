package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppNumberPickerDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizNumberPickerDialogFragment : AppNumberPickerDialogFragment() {

    private val viewModel: QuizNumberPickerViewModel by viewModels()

    override fun onDialogConfirm(value: Int) {
        viewModel.setQuizNumber(value)
    }

    override fun onDialogDismiss() = Unit
}