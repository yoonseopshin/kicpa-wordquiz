package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteWrongProblemDialogFragment : AppInfoDialogFragment() {

    private val viewModel: DeleteProblemViewModel by viewModels()

    override fun onDialogConfirm() {
        viewModel.deleteWrongProblem()
    }

    override fun onDialogDismiss() = Unit
}
