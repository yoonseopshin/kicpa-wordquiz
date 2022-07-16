package com.ysshin.cpaquiz.feature.home.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeViewModel
import com.ysshin.cpaquiz.shared.android.bridge.ProblemDetailNavigator
import com.ysshin.cpaquiz.shared.android.ui.ad.NativeMediumAd
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppNumberPickerDialog
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.android.ui.theme.Typography
import com.ysshin.cpaquiz.shared.base.Action
import com.ysshin.cpaquiz.shared.base.Consumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navigator: ProblemDetailNavigator, viewModel: HomeViewModel = viewModel()) {
    CpaQuizTheme {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        // NOTE: After migrating to Jetpack Compose, it will work fine, but not now.
        BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }

        BottomSheetScaffold(
            sheetContent = {
                HomeSettingsBottomSheetContent(viewModel)
            },
            sheetBackgroundColor = MaterialTheme.colors.onSurface,
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp
        ) {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    val dday by viewModel.dday.collectAsState()
                    HomeTopAppBar(
                        dday = dday,
                        scope = coroutineScope,
                        bottomSheetScaffoldState = bottomSheetScaffoldState
                    )
                }
            ) { padding ->
                val verticalScrollState = rememberScrollState()

                FlowRow(
                    modifier = Modifier
                        .padding(padding)
                        .padding(vertical = 28.dp)
                        .verticalScroll(verticalScrollState),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSize = SizeMode.Expand,
                    crossAxisSpacing = 20.dp,
                    mainAxisSpacing = 20.dp
                ) {
                    val accountingCount by viewModel.accountingCount.collectAsState()
                    QuizCard(
                        cardBackgroundColor = colorResource(id = R.color.accounting_highlight_color_0_20),
                        iconBackgroundColor = colorResource(id = R.color.accounting_highlight_color),
                        count = accountingCount,
                        title = stringResource(id = R.string.accounting),
                        onClick = {
                            context.startActivity(
                                navigator.quizIntent(
                                    context = context,
                                    quizType = QuizType.Accounting,
                                    quizNumbers = viewModel.quizNumber.value,
                                    useTimer = viewModel.useTimer.value,
                                )
                            )
                        }
                    )

                    val businessCount by viewModel.businessCount.collectAsState()
                    QuizCard(
                        cardBackgroundColor = colorResource(id = R.color.business_highlight_color_0_20),
                        iconBackgroundColor = colorResource(id = R.color.business_highlight_color),
                        count = businessCount,
                        title = stringResource(id = R.string.business),
                        onClick = {
                            context.startActivity(
                                navigator.quizIntent(
                                    context = context,
                                    quizType = QuizType.Business,
                                    quizNumbers = viewModel.quizNumber.value,
                                    useTimer = viewModel.useTimer.value,
                                )
                            )
                        }
                    )

                    val commercialLawCount by viewModel.commercialLawCount.collectAsState()
                    QuizCard(
                        cardBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color_0_20),
                        iconBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color),
                        count = commercialLawCount,
                        title = stringResource(id = R.string.commercial_law),
                        onClick = {
                            context.startActivity(
                                navigator.quizIntent(
                                    context = context,
                                    quizType = QuizType.CommercialLaw,
                                    quizNumbers = viewModel.quizNumber.value,
                                    useTimer = viewModel.useTimer.value,
                                )
                            )
                        }
                    )

                    val taxLawCount by viewModel.taxLawCount.collectAsState()
                    QuizCard(
                        cardBackgroundColor = colorResource(id = R.color.tax_law_highlight_color_0_20),
                        iconBackgroundColor = colorResource(id = R.color.tax_law_highlight_color),
                        count = taxLawCount,
                        title = stringResource(id = R.string.tax_law),
                        onClick = {
                            context.startActivity(
                                navigator.quizIntent(
                                    context = context,
                                    quizType = QuizType.TaxLaw,
                                    quizNumbers = viewModel.quizNumber.value,
                                    useTimer = viewModel.useTimer.value,
                                )
                            )
                        }
                    )

                    NativeMediumAd()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeTopAppBar(
    dday: String,
    scope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
) {
    CompositionLocalProvider(LocalElevationOverlay provides null) {
        TopAppBar(
            title = {
                Text(
                    text = if (dday.isBlank()) "" else stringResource(id = R.string.dday, dday),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            backgroundColor = colorResource(id = R.color.theme_color),
            actions = {
                IconButton(onClick = {
                    Timber.d("HomeScreen settings UI expanded")
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.settings),
                        tint = if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                            colorResource(id = R.color.daynight_pastel_blue)
                        } else {
                            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun QuizCard(
    cardBackgroundColor: Color,
    iconBackgroundColor: Color,
    count: Int,
    title: String,
    onClick: Action = {},
) {
    val cornerShape = RoundedCornerShape(24.dp)

    Card(
        modifier = Modifier
            .clip(cornerShape)
            .clickable(onClick = onClick),
        shape = cornerShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(cardBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 32.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(40.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .background(iconBackgroundColor)
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.defaultMinSize(minWidth = 64.dp)) {
                    Text(
                        text = stringResource(id = R.string.quiz_count, count),
                        style = Typography.caption,
                        color = colorResource(id = R.color.daynight_gray500s)
                    )
                    Text(
                        text = title,
                        style = Typography.subtitle1
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSettingsBottomSheetContent(viewModel: HomeViewModel = viewModel()) {
    LazyColumn {
        item {
            HomeBottomSheetHandle()
        }

        item {
            val quizNumber by viewModel.quizNumber.collectAsState()
            HomeQuizNumberBottomSheetListItem(
                quizNumber = quizNumber,
                onQuizNumberConfirm = {
                    viewModel.setQuizNumber(it)
                }
            )
        }

        item {
            val useTimer by viewModel.useTimer.collectAsState()

            HomeSettingsBottomSheetListItem(
                icon = painterResource(id = R.drawable.ic_timer),
                text = stringResource(id = R.string.timer),
                onBottomSheetItemClick = {
                    viewModel.toggleTimer()
                }
            ) {
                Switch(
                    checked = useTimer,
                    onCheckedChange = {
                        viewModel.setTimer(it)
                    }
                )
            }
        }
    }
}

@Composable
fun HomeQuizNumberBottomSheetListItem(quizNumber: Int, onQuizNumberConfirm: Consumer<Int>) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AppNumberPickerDialog(
            onConfirm = {
                onQuizNumberConfirm(it)
                openDialog.value = false
            },
            onDismiss = { openDialog.value = false },
            minNumber = 5,
            maxNumber = 25,
            defaultNumber = quizNumber,
            icon = painterResource(id = R.drawable.ic_note_outlined),
            title = stringResource(id = R.string.quiz_number_picker_title),
            description = stringResource(id = R.string.quiz_number_picker_description)
        )
    }

    HomeSettingsBottomSheetListItem(
        icon = painterResource(id = R.drawable.ic_note_outlined),
        text = stringResource(id = R.string.quiz_amount),
        onBottomSheetItemClick = {
            openDialog.value = true
        }
    ) {
        Crossfade(targetState = quizNumber) {
            Text(
                text = it.toString(),
                style = Typography.h6,
                color = colorResource(id = R.color.daynight_gray900s)
            )
        }
    }
}

@Composable
fun HomeBottomSheetHandle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.daynight_gray050s))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.size(width = 80.dp, height = 4.dp),
            backgroundColor = colorResource(id = R.color.daynight_gray800s)
        ) {}
    }
}

@Composable
fun HomeSettingsBottomSheetListItem(
    icon: Painter,
    text: String,
    onBottomSheetItemClick: Action = {},
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = colorResource(id = R.color.daynight_gray050s))
            .clickable(onClick = onBottomSheetItemClick)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.item_highlight_color)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = text, style = Typography.body1, color = colorResource(id = R.color.daynight_gray700s))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.width(48.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun HomeTopAppBarPreview() {
    HomeTopAppBar(dday = "123")
}

@Preview(showBackground = true)
@Composable
private fun QuizCardPreview() {
    QuizCard(
        cardBackgroundColor = colorResource(id = R.color.business_highlight_color_0_20),
        iconBackgroundColor = colorResource(id = R.color.business_highlight_color),
        count = 131,
        title = stringResource(id = R.string.business)
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeQuizNumberBottomSheetListItemPreview() {
    HomeQuizNumberBottomSheetListItem(quizNumber = 5, onQuizNumberConfirm = {})
}

@Preview(showBackground = true)
@Composable
private fun HomeDoubleDigitQuizNumberBottomSheetListItemPreview() {
    HomeQuizNumberBottomSheetListItem(quizNumber = 15, onQuizNumberConfirm = {})
}
