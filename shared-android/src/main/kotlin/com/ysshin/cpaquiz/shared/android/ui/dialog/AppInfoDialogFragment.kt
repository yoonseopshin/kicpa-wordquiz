package com.ysshin.cpaquiz.shared.android.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.DialogFragment
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.android.util.Constants

abstract class AppInfoDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ComposeView(requireContext()).apply {
            val iconResId = arguments?.getInt(Constants.icon) ?: return@apply
            val title = arguments?.getString(Constants.title) ?: return@apply
            val description = arguments?.getString(Constants.description) ?: return@apply

            setContent {
                CpaQuizTheme {
                    val openDialog = remember { mutableStateOf(true) }

                    if (openDialog.value) {
                        AppInfoDialog(
                            icon = painterResource(id = iconResId),
                            title = title,
                            description = description,
                            onConfirm = {
                                onDialogConfirm()
                                dismiss()
                            },
                            onDismiss = {
                                onDialogDismiss()
                                dismiss()
                            }
                        )
                    }
                }
            }
        }

    abstract fun onDialogConfirm()
    abstract fun onDialogDismiss()
}
