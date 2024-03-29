package com.ysshin.cpaquiz.core.android.ui.dialog

import android.os.Parcelable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.domain.model.QuizType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableTextItem(val text: String, val isSelected: Boolean) : Parcelable

@Immutable
data class SelectableTextItems(val items: List<SelectableTextItem>)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCheckboxDialog(
    onConfirm: Consumer<SelectableTextItems>,
    onDismiss: Action,
    icon: CpaIcon,
    title: String,
    description: String,
    selectableItems: SelectableTextItems,
    modifier: Modifier = Modifier,
    confirmText: String = stringResource(id = R.string.confirm),
    dismissText: String = stringResource(id = R.string.cancel),
    dialogType: AppDialogType = AppDialogType.ConfirmDismiss,
) {
    Dialog(onDismissRequest = onDismiss) {
        val verticalScrollState = rememberScrollState()

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp)
                .verticalScroll(state = verticalScrollState),
        ) {
            Column(
                Modifier.background(MaterialTheme.colorScheme.surface),
            ) {
                CpaIcon(
                    icon = icon,
                    tint = MaterialTheme.colorScheme.primary,
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
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    Text(
                        text = description,
                        modifier = Modifier
                            .padding(top = 12.dp, start = 24.dp, end = 24.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                var items by remember { mutableStateOf(selectableItems.items) }

                FlowRow(
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSize = SizeMode.Expand,
                ) {
                    for (item in items) {
                        FilterChip(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            onClick = {
                                items = items.map { selectedItem ->
                                    if (item == selectedItem) {
                                        item.copy(isSelected = item.isSelected.not())
                                    } else {
                                        selectedItem
                                    }
                                }
                            },
                            selected = item.isSelected,
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(text = item.text)
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(),
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    if (dialogType == AppDialogType.ConfirmDismiss) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = dismissText,
                                style = Typography.labelLarge,
                                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                            )
                        }
                    }
                    TextButton(onClick = { onConfirm(SelectableTextItems(items)) }) {
                        Text(
                            text = confirmText,
                            style = Typography.labelLarge,
                            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun YearFilterDialogPreview() {
    AppCheckboxDialog(
        icon = CpaIcons.Filter,
        title = stringResource(id = R.string.year),
        description = stringResource(id = R.string.choose_filtered_years),
        selectableItems = SelectableTextItems(
            (2016..2023).map {
                SelectableTextItem("$it", false)
            },
        ),
        onConfirm = {},
        onDismiss = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun QuizTypeFilterDialogPreview() {
    AppCheckboxDialog(
        icon = CpaIcons.Filter,
        title = stringResource(id = R.string.quiz),
        description = stringResource(id = R.string.choose_filtered_types),
        selectableItems = SelectableTextItems(
            QuizType.all().map { SelectableTextItem(it.toKorean(), false) },
        ),
        onConfirm = {},
        onDismiss = {},
    )
}
