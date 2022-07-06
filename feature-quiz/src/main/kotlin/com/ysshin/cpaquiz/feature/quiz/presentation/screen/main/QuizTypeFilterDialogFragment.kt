package com.ysshin.cpaquiz.feature.quiz.presentation.screen.main

import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppCheckboxDialogFragment
import com.ysshin.cpaquiz.shared.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.shared.android.util.obtainSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuizTypeFilterDialogFragment : AppCheckboxDialogFragment() {

    private val viewModel: NoteViewModel by lazy {
        val owner = try {
            requireParentFragment()
        } catch (e: IllegalStateException) {
            Timber.e(e)
            requireActivity()
        }
        obtainSharedViewModel(owner, NoteViewModel::class.java)
    }

    override fun onDialogConfirm(items: List<SelectableTextItem>) {
        Timber.d("Selected: $items")

        if (!items.any { it.isSelected }) {
            viewModel.showSnackbar(getString(R.string.msg_need_filtered_quiz_type))
            return
        }

        viewModel.setFilter(types = items.filter { it.isSelected }.map { QuizType.from(it.text) })
    }

    override fun onDialogDismiss() = Unit
}
