package com.ysshin.cpaquiz.feature.home.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.google.accompanist.flowlayout.SizeMode
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.ad.NativeMediumAd
import com.ysshin.cpaquiz.core.android.ui.dialog.AppNumberPickerDialog
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.designsystem.component.CpaBackground
import com.ysshin.cpaquiz.designsystem.component.CpaGradientBackground
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.designsystem.theme.LocalGradientColors
import com.ysshin.cpaquiz.designsystem.theme.LocalSnackbarHostState
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.feature.home.presentation.navigation.QuizStartNavigationActionsProvider
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeInfoUiState
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeQuizUiState
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.HomeViewModel
import com.ysshin.cpaquiz.feature.home.presentation.screen.main.SelectableSubtype
import kotlinx.coroutines.launch
import com.ysshin.cpaquiz.core.android.R as CR

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val homeQuizUiState by viewModel.homeQuizUiState.collectAsStateWithLifecycle()
    val homeInfoUiState by viewModel.homeInfoUiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context.findActivity()
    val appContext = context.applicationContext
    val scope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    HomeScreen(
        homeQuizUiState = homeQuizUiState,
        homeInfoUiState = homeInfoUiState,
        onSetQuizNumber = viewModel::setQuizNumber,
        onToggleTimer = viewModel::toggleTimer,
        onQuizCardClick = onQuizCardClick@{ type ->
            val selectedSubtypes = viewModel.getSelectedSubtypes(type)

            if (selectedSubtypes.isEmpty()) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.subtypes_not_selected_alert, type.toKorean()),
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
                return@onQuizCardClick
            }

            val quizStartNavActions = (appContext as QuizStartNavigationActionsProvider).quizStartNavActions
            quizStartNavActions.onQuizStart(
                activity = activity,
                quizType = type,
                quizNumbers = homeInfoUiState.quizNumber,
                useTimer = homeInfoUiState.useTimer,
                selectedSubtypes = viewModel.getSelectedSubtypes(type),
            )
        },
        onToggleSubtype = viewModel::toggleSubtype,
    )
}

@Composable
fun HomeScreen(
    homeQuizUiState: HomeQuizUiState,
    homeInfoUiState: HomeInfoUiState,
    onSetQuizNumber: Consumer<Int>,
    onToggleTimer: Action,
    onQuizCardClick: Consumer<QuizType>,
    onToggleSubtype: (String) -> Unit,
) {
    CpaBackground {
        CpaGradientBackground(
            gradientColors = LocalGradientColors.current,
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HomeTopAppBar(
                    homeInfoUiState = homeInfoUiState,
                    onSetQuizTimer = onSetQuizNumber,
                    onToggleTimer = onToggleTimer,
                )

                val verticalScrollState = rememberScrollState()

                FlowRow(
                    modifier = Modifier.verticalScroll(verticalScrollState),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSize = SizeMode.Expand,
                    crossAxisSpacing = 20.dp,
                    mainAxisSpacing = 20.dp,
                ) {
                    when (homeQuizUiState) {
                        is HomeQuizUiState.Loading -> Unit
                        is HomeQuizUiState.Success -> {
                            QuizCard(
                                modifier = Modifier.resourceTestTag("quizCard${QuizType.Accounting.ordinal}"),
                                cardBackgroundColor = colorResource(id = CR.color.accounting_highlight_color).copy(
                                    alpha = 0.2f,
                                ),
                                iconBackgroundColor = colorResource(id = CR.color.accounting_highlight_color),
                                count = homeQuizUiState.accountingCount,
                                title = stringResource(id = CR.string.accounting),
                                onClick = { onQuizCardClick(QuizType.Accounting) },
                                subtypes = homeQuizUiState.accountingSubtypes,
                                toggleSubtype = onToggleSubtype,
                            )

                            QuizCard(
                                modifier = Modifier.resourceTestTag("quizCard${QuizType.Business.ordinal}"),
                                cardBackgroundColor = colorResource(id = CR.color.business_highlight_color).copy(
                                    alpha = 0.2f,
                                ),
                                iconBackgroundColor = colorResource(id = CR.color.business_highlight_color),
                                count = homeQuizUiState.businessCount,
                                title = stringResource(id = CR.string.business),
                                onClick = { onQuizCardClick(QuizType.Business) },
                                subtypes = homeQuizUiState.businessSubtypes,
                                toggleSubtype = onToggleSubtype,
                            )

                            QuizCard(
                                modifier = Modifier.resourceTestTag("quizCard${QuizType.CommercialLaw.ordinal}"),
                                cardBackgroundColor = colorResource(id = CR.color.commercial_law_highlight_color).copy(
                                    alpha = 0.2f,
                                ),
                                iconBackgroundColor = colorResource(id = CR.color.commercial_law_highlight_color),
                                count = homeQuizUiState.commercialLawCount,
                                title = stringResource(id = CR.string.commercial_law),
                                onClick = { onQuizCardClick(QuizType.CommercialLaw) },
                                subtypes = homeQuizUiState.commercialLawSubtypes,
                                toggleSubtype = onToggleSubtype,
                            )

                            QuizCard(
                                modifier = Modifier.resourceTestTag("quizCard${QuizType.TaxLaw.ordinal}"),
                                cardBackgroundColor = colorResource(id = CR.color.tax_law_highlight_color).copy(
                                    alpha = 0.2f,
                                ),
                                iconBackgroundColor = colorResource(id = CR.color.tax_law_highlight_color),
                                count = homeQuizUiState.taxLawCount,
                                title = stringResource(id = CR.string.tax_law),
                                onClick = { onQuizCardClick(QuizType.TaxLaw) },
                                subtypes = homeQuizUiState.taxLawSubtypes,
                                toggleSubtype = onToggleSubtype,
                            )

                            NativeMediumAd()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(homeInfoUiState: HomeInfoUiState, onSetQuizTimer: Consumer<Int>, onToggleTimer: Action) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        title = {
            Text(
                text = if (homeInfoUiState.dday.isBlank()) {
                    ""
                } else {
                    stringResource(id = R.string.dday, homeInfoUiState.dday)
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            HomeTopMenu(
                quizNumber = homeInfoUiState.quizNumber,
                onSetQuizTimer = onSetQuizTimer,
                useTimer = homeInfoUiState.useTimer,
                onToggleTimer = onToggleTimer,
            )
        },
    )
}

@Composable
private fun HomeTopMenu(quizNumber: Int, onSetQuizTimer: Consumer<Int>, useTimer: Boolean, onToggleTimer: Action) {
    var showDialog by remember { mutableStateOf(false) }

    IconButton(onClick = {
        showDialog = true
    }) {
        if (showDialog) {
            AlertDialog(onDismissRequest = {
                showDialog = false
            }, title = { Text(text = stringResource(id = CR.string.quiz_settings_title)) }, text = {
                LazyColumn {
                    item {
                        HomeQuizNumberBottomSheetListItem(
                            quizNumber = quizNumber,
                            onQuizNumberConfirm = onSetQuizTimer,
                        )
                    }

                    item {
                        HomeSettingsListItem(
                            icon = CpaIcons.Timer,
                            text = stringResource(id = CR.string.timer),
                            onBottomSheetItemClick = onToggleTimer,
                        ) {
                            Switch(checked = useTimer, onCheckedChange = {
                                onToggleTimer()
                            })
                        }
                    }
                }
            }, confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = stringResource(id = CR.string.confirm))
                }
            })
        }

        val transition = updateTransition(targetState = showDialog, label = "SettingsMenuIconTransition")

        val tint by transition.animateColor(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessLow)
                }
            },
            label = "SettingsMenuIconColor",
        ) { isExpanded ->
            if (isExpanded) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
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
            label = "SettingsMenuIconRotationDegree",
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

        CpaIcon(
            icon = if (showDialog) CpaIcons.SettingsFilled else CpaIcons.Settings,
            modifier = Modifier
                .rotate(rotationDegree)
                .size(size),
            contentDescription = stringResource(id = CR.string.settings),
            tint = tint,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizCard(
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color,
    iconBackgroundColor: Color,
    count: Int,
    title: String,
    onClick: Action,
    subtypes: List<SelectableSubtype>,
    toggleSubtype: (String) -> Unit,
) {
    val cornerShape = RoundedCornerShape(24.dp)
    val quizCardEnabled = count > 0
    val disabledBackgroundAlpha = 0.09f
    val disabledContentAlpha = 0.36f
    val elevation = 6.dp
    var cardWidth by remember { mutableStateOf(0) }

    Column {
        Surface(
            modifier = modifier
                .bounceClickable(
                    dampingRatio = 0.9f,
                    enabled = quizCardEnabled,
                    onClick = onClick,
                )
                .padding(elevation)
                .shadow(
                    elevation = elevation,
                    shape = cornerShape,
                )
                .onGloballyPositioned { coordinates ->
                    cardWidth = coordinates.size.width
                },
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
                        },
                    )
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp),
                    ) {
                        CpaIcon(
                            icon = CpaIcons.Play,
                            modifier = Modifier
                                .background(
                                    if (quizCardEnabled) {
                                        iconBackgroundColor.copy(0.88f)
                                    } else {
                                        iconBackgroundColor.copy(alpha = disabledContentAlpha)
                                    },
                                )
                                .padding(8.dp),
                            tint = if (quizCardEnabled) {
                                colorResource(id = CR.color.daynight_gray800s)
                            } else {
                                colorResource(id = CR.color.daynight_gray800s).copy(alpha = disabledContentAlpha)
                            },
                            contentDescription = null,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.defaultMinSize(minWidth = 64.dp)) {
                        Text(
                            text = stringResource(id = R.string.quiz_count, count),
                            style = Typography.labelSmall,
                            color = if (quizCardEnabled) {
                                colorResource(id = CR.color.daynight_gray500s)
                            } else {
                                colorResource(id = CR.color.daynight_gray500s).copy(alpha = disabledContentAlpha)
                            },
                        )
                        Text(
                            text = title,
                            style = Typography.titleMedium,
                            color = if (quizCardEnabled) {
                                colorResource(id = CR.color.daynight_gray800s)
                            } else {
                                colorResource(id = CR.color.daynight_gray800s).copy(alpha = disabledContentAlpha)
                            },
                        )
                    }
                }
            }
        }

        if (subtypes.isNotEmpty()) {
            Row(
                modifier = Modifier.width(
                    width = with(LocalDensity.current) {
                        cardWidth.toDp()
                    },
                ),
            ) {
                LazyRow(state = rememberLazyListState()) {
                    itemsIndexed(subtypes) { _, subtype ->
                        FilterChip(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            onClick = {
                                toggleSubtype(subtype.text)
                            },
                            selected = subtype.isSelected,
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(text = subtype.text)
                                }
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = cardBackgroundColor,
                                selectedLabelColor = colorResource(id = CR.color.daynight_gray800s),
                                disabledLabelColor = colorResource(id = CR.color.daynight_gray800s)
                                    .copy(alpha = disabledContentAlpha),
                            ),
                        )
                    }
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
            icon = CpaIcons.NoteOutlined,
            title = stringResource(id = CR.string.quiz_number_picker_title),
            description = stringResource(id = CR.string.quiz_number_picker_description),
        )
    }

    HomeSettingsListItem(
        icon = CpaIcons.NoteOutlined,
        text = stringResource(id = CR.string.quiz_amount),
        onBottomSheetItemClick = {
            openDialog.value = true
        },
    ) {
        Crossfade(targetState = quizNumber) {
            Text(
                text = it.toString(),
                style = Typography.headlineSmall,
                color = colorResource(id = CR.color.daynight_gray900s),
            )
        }
    }
}

@Composable
private fun HomeSettingsListItem(
    icon: CpaIcon,
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CpaIcon(
            icon = icon,
            modifier = Modifier.size(36.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = text, style = Typography.bodyLarge)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Row(
                modifier = Modifier.width(48.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    CpaQuizTheme {
        BoxWithConstraints {
            HomeScreen(
                homeQuizUiState = HomeQuizUiState.Success(
                    accountingCount = 10,
                    accountingSubtypes = emptyList(),
                    businessCount = 20,
                    businessSubtypes = emptyList(),
                    commercialLawCount = 30,
                    commercialLawSubtypes = listOf(
                        SelectableSubtype("상행위", true),
                        SelectableSubtype("회사법", true),
                        SelectableSubtype("어음수표법", false),
                    ),
                    taxLawCount = 40,
                    taxLawSubtypes = listOf(
                        SelectableSubtype("기타세법", true),
                        SelectableSubtype("부가가치세", true),
                        SelectableSubtype("소득세", false),
                        SelectableSubtype("법인세", false),
                    ),
                ),
                homeInfoUiState = HomeInfoUiState(
                    dday = "365",
                    quizNumber = 20,
                    useTimer = true,
                ),
                onSetQuizNumber = {},
                onToggleTimer = {},
                onQuizCardClick = {},
                onToggleSubtype = {},
            )
        }
    }
}
