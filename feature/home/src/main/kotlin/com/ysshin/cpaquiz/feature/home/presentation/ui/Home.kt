package com.ysshin.cpaquiz.feature.home.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.ysshin.cpaquiz.core.android.ui.ad.NativeMediumAd
import com.ysshin.cpaquiz.core.android.ui.dialog.AppNumberPickerDialog
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.core.common.Action
import com.ysshin.cpaquiz.core.common.Consumer
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActionsProvider
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val dday by viewModel.dday.collectAsStateWithLifecycle()
    val accountingCount by viewModel.accountingCount.collectAsStateWithLifecycle()
    val businessCount by viewModel.businessCount.collectAsStateWithLifecycle()
    val commercialLawCount by viewModel.commercialLawCount.collectAsStateWithLifecycle()
    val taxLawCount by viewModel.taxLawCount.collectAsStateWithLifecycle()
    val quizNumber by viewModel.quizNumber.collectAsStateWithLifecycle()
    val useTimer by viewModel.useTimer.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context.findActivity()
    val appContext = context.applicationContext

    HomeScreen(
        windowSizeClass = windowSizeClass,
        modifier = modifier,
        dday = dday,
        accountingCount = accountingCount,
        businessCount = businessCount,
        commercialLawCount = commercialLawCount,
        taxLawCount = taxLawCount,
        quizNumber = quizNumber,
        onSetQuizNumber = viewModel::setQuizNumber,
        useTimer = useTimer,
        onToggleTimer = viewModel::toggleTimer,
        onQuizCardClick = { type ->
            val quizStartNavActions = (appContext as QuizStartNavigationActionsProvider).quizStartNavActions
            quizStartNavActions.onQuizStart(
                activity = activity,
                quizType = type,
                quizNumbers = quizNumber,
                useTimer = useTimer,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    dday: String,
    accountingCount: Int,
    businessCount: Int,
    commercialLawCount: Int,
    taxLawCount: Int,
    quizNumber: Int,
    onSetQuizNumber: Consumer<Int>,
    useTimer: Boolean,
    onToggleTimer: Action,
    onQuizCardClick: Consumer<QuizType>,
) {
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState(),
        flingAnimationSpec = decayAnimationSpec,
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(
                windowSizeClass = windowSizeClass,
                scrollBehavior = scrollBehavior,
                dday = dday,
                quizNumber = quizNumber,
                onSetQuizTimer = onSetQuizNumber,
                useTimer = useTimer,
                onToggleTimer = onToggleTimer
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
            QuizCard(
                cardBackgroundColor = colorResource(id = R.color.accounting_highlight_color_0_20),
                iconBackgroundColor = colorResource(id = R.color.accounting_highlight_color),
                count = accountingCount,
                title = stringResource(id = R.string.accounting),
                onClick = { onQuizCardClick(QuizType.Accounting) }
            )

            QuizCard(
                cardBackgroundColor = colorResource(id = R.color.business_highlight_color_0_20),
                iconBackgroundColor = colorResource(id = R.color.business_highlight_color),
                count = businessCount,
                title = stringResource(id = R.string.business),
                onClick = { onQuizCardClick(QuizType.Business) }
            )

            QuizCard(
                cardBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color_0_20),
                iconBackgroundColor = colorResource(id = R.color.commercial_law_highlight_color),
                count = commercialLawCount,
                title = stringResource(id = R.string.commercial_law),
                onClick = { onQuizCardClick(QuizType.CommercialLaw) }
            )

            QuizCard(
                cardBackgroundColor = colorResource(id = R.color.tax_law_highlight_color_0_20),
                iconBackgroundColor = colorResource(id = R.color.tax_law_highlight_color),
                count = taxLawCount,
                title = stringResource(id = R.string.tax_law),
                onClick = { onQuizCardClick(QuizType.TaxLaw) }
            )

            NativeMediumAd()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    windowSizeClass: WindowSizeClass,
    scrollBehavior: TopAppBarScrollBehavior,
    dday: String,
    quizNumber: Int,
    onSetQuizTimer: Consumer<Int>,
    useTimer: Boolean,
    onToggleTimer: Action,
) {
    val shouldShowLargeTopAppBar = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
    if (shouldShowLargeTopAppBar) {
        LargeTopAppBar(
            title = {
                Text(
                    text = if (dday.isBlank()) "" else stringResource(id = R.string.dday, dday),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            actions = {
                HomeTopMenu(
                    quizNumber = quizNumber,
                    onSetQuizTimer = onSetQuizTimer,
                    useTimer = useTimer,
                    onToggleTimer = onToggleTimer
                )
            },
            scrollBehavior = scrollBehavior
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = if (dday.isBlank()) "" else stringResource(id = R.string.dday, dday),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            actions = {
                HomeTopMenu(
                    quizNumber = quizNumber,
                    onSetQuizTimer = onSetQuizTimer,
                    useTimer = useTimer,
                    onToggleTimer = onToggleTimer
                )
            }
        )
    }
}

@Composable
private fun HomeTopMenu(
    quizNumber: Int,
    onSetQuizTimer: Consumer<Int>,
    useTimer: Boolean,
    onToggleTimer: Action,
) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(onClick = {
        openDialog.value = true
    }) {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = { Text(text = stringResource(id = R.string.quiz_settings_title)) },
                text = {
                    LazyColumn {
                        item {
                            HomeQuizNumberBottomSheetListItem(
                                quizNumber = quizNumber,
                                onQuizNumberConfirm = onSetQuizTimer
                            )
                        }

                        item {
                            HomeSettingsListItem(
                                icon = painterResource(id = R.drawable.ic_timer),
                                text = stringResource(id = R.string.timer),
                                onBottomSheetItemClick = onToggleTimer
                            ) {
                                Switch(
                                    checked = useTimer,
                                    onCheckedChange = {
                                        onToggleTimer()
                                    }
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            )
        }

        val transition =
            updateTransition(targetState = openDialog.value, label = "SettingsMenuIconTransition")

        val tint by transition.animateColor(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessLow)
                }
            },
            label = "SettingsMenuIconColor"
        ) { isExpanded ->
            if (isExpanded) {
                MaterialTheme.colorScheme.primary
            } else {
                LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            }
        }

        val rotationDegree by transition.animateFloat(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessLow)
                }
            },
            label = "SettingsMenuIconRotationDegree"
        ) { isExpanded ->
            if (isExpanded) 240f else 0f
        }

        val size by transition.animateDp(transitionSpec = {
            if (false isTransitioningTo true) {
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessLow)
            }
        }, label = "SettingsMenuIconSize") { isExpanded ->
            if (isExpanded) 32.dp else 24.dp
        }

        Icon(
            imageVector = if (openDialog.value) Icons.Filled.Settings else Icons.Outlined.Settings,
            contentDescription = stringResource(id = R.string.settings),
            tint = tint,
            modifier = Modifier
                .rotate(rotationDegree)
                .size(size)
        )
    }
}

@Composable
private fun QuizCard(
    cardBackgroundColor: Color,
    iconBackgroundColor: Color,
    count: Int,
    title: String,
    onClick: Action,
) {
    val cornerShape = RoundedCornerShape(24.dp)
    val quizCardEnabled = count > 0
    val disabledBackgroundAlpha = 0.09f
    val disabledContentAlpha = 0.36f

    Surface(
        modifier = Modifier
            .bounceClickable(
                dampingRatio = 0.9f,
                enabled = quizCardEnabled,
                onClick = onClick,
            ),
        shape = cornerShape,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    if (quizCardEnabled) {
                        cardBackgroundColor
                    } else {
                        cardBackgroundColor.copy(alpha = disabledBackgroundAlpha)
                    }
                )
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
                            .background(
                                if (quizCardEnabled) {
                                    iconBackgroundColor.copy(0.88f)
                                } else {
                                    iconBackgroundColor.copy(alpha = disabledContentAlpha)
                                }
                            )
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_play),
                        tint = if (quizCardEnabled) {
                            colorResource(id = R.color.daynight_gray800s)
                        } else {
                            colorResource(id = R.color.daynight_gray800s).copy(alpha = disabledContentAlpha)
                        },
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.defaultMinSize(minWidth = 64.dp)) {
                    Text(
                        text = stringResource(id = R.string.quiz_count, count),
                        style = Typography.labelSmall,
                        color = if (quizCardEnabled) {
                            colorResource(id = R.color.daynight_gray500s)
                        } else {
                            colorResource(id = R.color.daynight_gray500s).copy(alpha = disabledContentAlpha)
                        }
                    )
                    Text(
                        text = title,
                        style = Typography.titleMedium,
                        color = if (quizCardEnabled) {
                            colorResource(id = R.color.daynight_gray800s)
                        } else {
                            colorResource(id = R.color.daynight_gray800s).copy(alpha = disabledContentAlpha)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeQuizNumberBottomSheetListItem(quizNumber: Int, onQuizNumberConfirm: Consumer<Int>) {
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

    HomeSettingsListItem(
        icon = painterResource(id = R.drawable.ic_note_outlined),
        text = stringResource(id = R.string.quiz_amount),
        onBottomSheetItemClick = {
            openDialog.value = true
        }
    ) {
        Crossfade(targetState = quizNumber) {
            Text(
                text = it.toString(),
                style = Typography.headlineSmall,
                color = colorResource(id = R.color.daynight_gray900s)
            )
        }
    }
}

@Composable
private fun HomeSettingsListItem(
    icon: Painter,
    text: String,
    onBottomSheetItemClick: Action = {},
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onBottomSheetItemClick)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(36.dp),
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = text, style = Typography.bodyLarge)

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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
private fun HomeScreenPreview() {
    CpaQuizTheme {
        BoxWithConstraints {
            HomeScreen(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight)),
                dday = "123",
                accountingCount = 100,
                businessCount = 50,
                commercialLawCount = 25,
                taxLawCount = 125,
                quizNumber = 20,
                onSetQuizNumber = {},
                useTimer = true,
                onToggleTimer = {},
                onQuizCardClick = {}
            )
        }
    }
}
