package com.ysshin.cpaquiz.shared.android.ui.dialog

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.shared.android.R
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectableTextItem(val text: String, val isSelected: Boolean) : Parcelable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppCheckboxDialog(
    modifier: Modifier = Modifier,
    onConfirm: Consumer<List<SelectableTextItem>> = {},
    onDismiss: Action = {},
    icon: Painter,
    title: String,
    description: String,
    selectableItems: List<SelectableTextItem>,
    confirmText: String = stringResource(id = R.string.confirm),
    dismissText: String = stringResource(id = R.string.cancel),
    dialogType: AppDialogType = AppDialogType.ConfirmDismiss,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp),
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

                var items by remember { mutableStateOf(selectableItems) }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                ) {
                    items(items) { item ->
                        Chip(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 3.dp),
                            onClick = {
                                items = items.map { selectedItem ->
                                    if (item == selectedItem) {
                                        item.copy(isSelected = item.isSelected.not())
                                    } else selectedItem
                                }
                            },
                            colors = ChipDefaults.chipColors(
                                backgroundColor = if (item.isSelected) {
                                    colorResource(id = R.color.secondaryColor_0_15)
                                } else {
                                    colorResource(id = R.color.daynight_gray070s)
                                },
                                contentColor = if (item.isSelected) Color.Magenta else Color.Black
                            ),
                            border = BorderStroke(
                                width = 0.5.dp,
                                color = if (item.isSelected) {
                                    colorResource(id = R.color.secondaryColor)
                                } else {
                                    colorResource(id = R.color.daynight_gray300s)
                                }
                            ),
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = item.text,
                                    color = MaterialTheme.colors.onSurface,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                )
                            }
                        }
                    }
                }

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
                    TextButton(onClick = { onConfirm(items) }) {
                        Text(
                            text = confirmText,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.onSurface,
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
fun YearFilterDialogPreview() {
    AppCheckboxDialog(
        icon = painterResource(id = R.drawable.ic_filter),
        title = stringResource(id = R.string.year),
        description = stringResource(id = R.string.choose_filtered_years),
        selectableItems = Problem.allYears().map {
            SelectableTextItem("$it", false)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun QuizTypeFilterDialogPreview() {
    AppCheckboxDialog(
        icon = painterResource(id = R.drawable.ic_filter),
        title = stringResource(id = R.string.quiz),
        description = stringResource(id = R.string.choose_filtered_types),
        selectableItems = QuizType.all().map { SelectableTextItem(it.toKorean(), false) }
    )
}
