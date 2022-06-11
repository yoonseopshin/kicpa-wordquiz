package com.ysshin.cpaquiz.shared.android.ui.dialog

import android.widget.NumberPicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.ysshin.cpaquiz.shared.android.R
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Consumer

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
            elevation = 4.dp
        ) {
            Column(
                modifier.background(MaterialTheme.colors.surface)
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
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
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = description,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body2
                    )
                }

                val numberPicker = NumberPicker(LocalContext.current).apply {
                    minValue = minNumber
                    maxValue = maxNumber
                    value = defaultNumber
                }

                AndroidView(
                    modifier = Modifier.fillMaxWidth(),
                    factory = { numberPicker }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .background(MaterialTheme.colors.primary.copy(alpha = 0.15f)),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (dialogType == AppDialogType.ConfirmDismiss) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = dismissText,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                            )
                        }
                    }
                    TextButton(onClick = { onConfirm(numberPicker.value) }) {
                        Text(
                            text = confirmText,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                    }
                }
            }
        }
    }
}
