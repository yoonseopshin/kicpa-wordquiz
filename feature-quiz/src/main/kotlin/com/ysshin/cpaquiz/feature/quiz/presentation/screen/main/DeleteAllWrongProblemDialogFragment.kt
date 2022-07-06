package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllWrongProblemDialogFragment : AppInfoDialogFragment() {

    private val viewModel: DeleteProblemViewModel by viewModels()

    override fun onDialogConfirm() {
        viewModel.deleteAllWrongProblems()
    }

    override fun onDialogDismiss() = Unit
}
