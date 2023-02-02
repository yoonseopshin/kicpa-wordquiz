package com.ysshin.cpaquiz.core.android.ui.dialog

import android.content.Context.VIBRATOR_MANAGER_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.VibratorManager
import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer

@Composable
fun AppNumberPickerDialog(
    modifier: Modifier = Modifier,
    onConfirm: Consumer<Int>,
    onDismiss: Action = {},
    minNumber: Int,
    maxNumber: Int,
    defaultNumber: Int,
    icon: Painter,
    title: String,
    description: String,
    confirmText: String = stringResource(id = R.string.confirm),
    dismissText: String = stringResource(id = R.string.cancel),
    dialogType: AppDialogType = AppDialogType.ConfirmDismiss,
) {
    Dialog(onDismissRequest = onDismiss) {
        val scrollState = rememberScrollState()

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp)
                .verticalScroll(scrollState),
        ) {
            Column(
                modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = description,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                val context = LocalContext.current

                val numberPicker = NumberPicker(context).apply {
                    minValue = minNumber
                    maxValue = maxNumber
                    value = defaultNumber

                    setOnValueChangedListener { _, _, _ ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val vibrator =
                                context.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                            vibrator.defaultVibrator.vibrate(
                                VibrationEffect.createOneShot(
                                    50L,
                                    VibrationEffect.EFFECT_HEAVY_CLICK
                                )
                            )
                        }
                    }
                }

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { numberPicker }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (dialogType == AppDialogType.ConfirmDismiss) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = dismissText,
                                style = Typography.labelLarge,
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                            )
                        }
                    }
                    TextButton(onClick = { onConfirm(numberPicker.value) }) {
                        Text(
                            text = confirmText,
                            style = Typography.labelLarge,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppNumberPickerDialogPreview() {
    AppNumberPickerDialog(
        minNumber = 5,
        maxNumber = 25,
        defaultNumber = 5,
        icon = painterResource(id = R.drawable.ic_note_outlined),
        title = stringResource(id = R.string.quiz_number_picker_title),
        description = stringResource(id = R.string.quiz_number_picker_description),
        onConfirm = {},
    )
}
