package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import android.view.inputmethod.EditorInfo
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.isValid
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.databinding.LayoutBottomSheetSearchBinding
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.TotalProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.WrongProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.shared.android.ui.bottomsheet.BottomSheetHandle
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.shared.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.shared.android.util.setOnThrottleClick
import com.ysshin.cpaquiz.shared.android.util.textChanges
import com.ysshin.cpaquiz.shared.base.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel = viewModel()) {
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
                NoteSearchBottomSheetContent(bottomSheetScaffoldState, viewModel, coroutineScope)
            },
            sheetBackgroundColor = MaterialTheme.colors.onSurface,
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
        ) {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    val userInput = viewModel.userInputText.collectAsState()

                    CompositionLocalProvider(LocalElevationOverlay provides null) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(id = R.string.note),
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            },
                            backgroundColor = colorResource(id = R.color.theme_color),
                            actions = {
                                NoteTopMenu(
                                    bottomSheetScaffoldState,
                                    userInput.value.isNotBlank(),
                                    coroutineScope
                                )
                            }
                        )
                    }
                }
            ) { padding ->
                val uiState = viewModel.uiState.collectAsState()

                val openDeleteWrongProblemDialog = viewModel.isDeleteWrongProblemDialogOpened.collectAsState()
                if (openDeleteWrongProblemDialog.value) {
                    AppInfoDialog(
                        icon = painterResource(id = R.drawable.ic_delete),
                        title = stringResource(id = R.string.delete_wrong_problem),
                        description = stringResource(id = R.string.question_delete_wrong_note),
                        onConfirm = {
                            viewModel.deleteTargetWrongProblem()
                            viewModel.updateDeleteWrongProblemDialogOpened(false)
                        },
                        onDismiss = {
                            viewModel.updateDeleteWrongProblemDialogOpened(false)
                        }
                    )
                }

                LazyColumn(modifier = Modifier.padding(padding)) {
                    val wrongProblemsUiState = uiState.value.wrongProblemsUiState

                    item {
                        WrongNoteHeaderContent(viewModel, wrongProblemsUiState)
                    }

                    if (wrongProblemsUiState is WrongProblemsUiState.Success) {
                        items(
                            items = wrongProblemsUiState.data,
                            key = { problem ->
                                problem.hashCode()
                            }
                        ) { problem ->
                            NoteSummaryContent(
                                problem = problem,
                                onProblemClick = {
                                    context.startActivity(
                                        ProblemDetailActivity.newIntent(
                                            context,
                                            ProblemDetailMode.Detail,
                                            problem.toModel()
                                        ),
                                    )
                                },
                                onProblemLongClick = {
                                    viewModel.updateDeleteWrongProblemDialogOpened(true, problem)
                                }
                            ).takeIf { problem.isValid() }
                        }
                    }

                    val totalProblemsUiState = uiState.value.totalProblemsUiState

                    item {
                        TotalNoteHeaderContent(totalProblemsUiState)
                    }

                    if (totalProblemsUiState is TotalProblemsUiState.Success) {
                        items(totalProblemsUiState.data) { problem ->
                            NoteSummaryContent(
                                problem = problem,
                                onProblemClick = {
                                    context.startActivity(
                                        ProblemDetailActivity.newIntent(
                                            context,
                                            ProblemDetailMode.Detail,
                                            problem.toModel()
                                        ),
                                    )
                                }
                            ).takeIf { problem.isValid() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WrongNoteHeaderContent(viewModel: NoteViewModel = viewModel(), state: WrongProblemsUiState) {
    when (state) {
        is WrongProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Wrong problems(${problems.size}) added.")

            val openDeleteAllWrongProblemsDialog =
                viewModel.isDeleteAllWrongProblemsDialogOpened.collectAsState()
            if (openDeleteAllWrongProblemsDialog.value) {
                AppInfoDialog(
                    icon = painterResource(id = R.drawable.ic_delete),
                    title = stringResource(id = R.string.delete_wrong_note),
                    description = stringResource(id = R.string.question_delete_all_wrong_note),
                    onConfirm = {
                        viewModel.deleteAllWrongProblems()
                        viewModel.updateDeleteAllWrongProblemsDialogOpened(false)
                    },
                    onDismiss = {
                        viewModel.updateDeleteAllWrongProblemsDialogOpened(false)
                    }
                )
            }

            NoteHeader(
                title = stringResource(id = R.string.wrong_note),
                numOfProblems = problems.size,
                onHeaderLongClick = {
                    viewModel.updateDeleteAllWrongProblemsDialogOpened(true)
                }
            )
        }
        is WrongProblemsUiState.Error -> {
            // TODO
        }
        is WrongProblemsUiState.Loading -> {
            // TODO
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteSummaryContent(problem: Problem, onProblemClick: Action = {}, onProblemLongClick: Action = {}) {
    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = onProblemClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onProblemLongClick()
                }
            )
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp)
            ) {
                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = colorResource(id = R.color.daynight_gray070s)
                    ),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = colorResource(id = R.color.daynight_gray300s)
                    ),
                    modifier = Modifier.padding(all = 4.dp)
                ) {
                    Text(text = "${problem.year}년 ${problem.pid}번")
                }
                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = colorResource(id = R.color.daynight_gray070s)
                    ),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = colorResource(id = R.color.daynight_gray300s)
                    ),
                    modifier = Modifier.padding(all = 4.dp)
                ) {
                    Text(text = problem.source.toString())
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp)
            ) {
                val backgroundColorResourceIdByType = when (problem.type) {
                    QuizType.Accounting -> R.color.accounting_highlight_color_0_20
                    QuizType.Business -> R.color.business_highlight_color_0_20
                    QuizType.CommercialLaw -> R.color.commercial_law_highlight_color_0_20
                    QuizType.TaxLaw -> R.color.tax_law_highlight_color_0_20
                }

                val borderColorResourceIdByType = when (problem.type) {
                    QuizType.Accounting -> R.color.accounting_highlight_color
                    QuizType.Business -> R.color.business_highlight_color
                    QuizType.CommercialLaw -> R.color.commercial_law_highlight_color
                    QuizType.TaxLaw -> R.color.tax_law_highlight_color
                }

                Chip(
                    onClick = {},
                    colors = ChipDefaults.chipColors(
                        backgroundColor = colorResource(id = backgroundColorResourceIdByType)
                    ),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = colorResource(id = borderColorResourceIdByType)
                    ),
                    modifier = Modifier.padding(all = 4.dp)
                ) {
                    Text(text = problem.type.toKorean())
                }
            }
        }

        Text(
            text = problem.description, maxLines = 2, overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp),
            fontSize = 15.sp,
            color = colorResource(id = R.color.daynight_gray700s)
        )
    }
}

@Composable
fun TotalNoteHeaderContent(state: TotalProblemsUiState) {
    when (state) {
        is TotalProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Total problems(${problems.size}) added.")

            NoteHeader(
                title = stringResource(id = R.string.total_note),
                numOfProblems = problems.size
            )
        }
        is TotalProblemsUiState.Error -> {
            // TODO
        }
        is TotalProblemsUiState.Loading -> {
            // TODO
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteSearchBottomSheetContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: NoteViewModel = viewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    LazyColumn {
        item {
            BottomSheetHandle()
        }

        item {
            BottomSheetSearchContent(bottomSheetScaffoldState, viewModel, scope)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetSearchContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    viewModel: NoteViewModel = viewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    AndroidViewBinding(factory = LayoutBottomSheetSearchBinding::inflate) {
        etSearch.textChanges()
            .map { it?.toString() ?: "" }
            .debounce(300L)
            .onEach { viewModel.updateUserInput(it) }
            .launchIn(scope)

        etSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                    true
                }
                else -> false
            }
        }

        ivClear.setOnThrottleClick {
            etSearch.text.clear()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteHeader(
    title: String = "",
    numOfProblems: Int = 0,
    onHeaderClick: Action = {},
    onHeaderLongClick: Action = {},
) {
    if (numOfProblems > 0) {
        val haptic = LocalHapticFeedback.current
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .combinedClickable(
                    onClick = onHeaderClick,
                    onLongClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onHeaderLongClick()
                    }
                )
                .fillMaxWidth()
                .defaultMinSize(minHeight = 52.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    append(title)
                    append("  ")

                    withStyle(
                        style = SpanStyle(
                            fontSize = 16.sp,
                            color = colorResource(id = R.color.daynight_pastel_blue)
                        ),
                    ) {
                        append(numOfProblems.toString())
                    }
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoteTopMenu(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isSearching: Boolean,
    scope: CoroutineScope,
) {
    IconButton(onClick = {
        scope.launch {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }) {
        val isMenuExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded

        val transition =
            updateTransition(targetState = isSearching, label = "SearchingMenuIconTransition")

        val tint by transition.animateColor(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessLow)
                }
            },
            label = "SearchingMenuIconColor"
        ) { isExpanded ->
            if (isExpanded) {
                colorResource(id = R.color.daynight_pastel_blue)
            } else {
                LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
            }
        }

        val size by transition.animateDp(transitionSpec = {
            if (false isTransitioningTo true) {
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
            } else {
                spring(stiffness = Spring.StiffnessLow)
            }
        }, label = "SearchingMenuIconSize") { isExpanded ->
            if (isExpanded) 32.dp else 24.dp
        }

        Icon(
            imageVector = if (isMenuExpanded) Icons.Filled.Search else Icons.Outlined.Search,
            contentDescription = stringResource(id = R.string.search),
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}
