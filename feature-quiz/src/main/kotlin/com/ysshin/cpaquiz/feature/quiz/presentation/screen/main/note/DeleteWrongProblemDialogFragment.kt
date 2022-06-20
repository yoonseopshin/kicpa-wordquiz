package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialogFragment
import com.ysshin.cpaquiz.shared.android.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteWrongProblemDialogFragment : AppInfoDialogFragment() {

    private val viewModel: DeleteProblemViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = arguments?.getInt(Constants.targetYear) ?: return
        val pid = arguments?.getInt(Constants.targetPid) ?: return
        val type = arguments?.getSerializable(Constants.targetType) as? QuizType ?: return
        viewModel.targetYear = year
        viewModel.targetPid = pid
        viewModel.targetType = type
    }

    override fun onDialogConfirm() {
        viewModel.deleteWrongProblem()
    }

    override fun onDialogDismiss() = Unit
}
