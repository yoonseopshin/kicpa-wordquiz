package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Badge
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme as M3MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.isValid
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toWrongProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteBottomSheetContentState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteUiEvent
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.SearchedProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.TotalProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.UserActionUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.WrongProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil
import com.ysshin.cpaquiz.shared.android.ui.ad.NativeSmallAd
import com.ysshin.cpaquiz.shared.android.ui.bottomsheet.BottomSheetHandle
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppCheckboxDialog
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.shared.base.Action
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun NoteScreen(windowSizeClass: WindowSizeClass, viewModel: NoteViewModel = hiltViewModel()) {
    CpaQuizLegacyTheme {
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
        )
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(bottomSheetScaffoldState) {
            viewModel.uiEvent.collectLatest { event ->
                when (event) {
                    is NoteUiEvent.ShowSnackbar -> {
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                            message = event.message.asString(context),
                            actionLabel = event.actionLabel.asString(context)
                        )
                    }
                }
            }
        }

        // NOTE: After migrating to Jetpack Compose, it will work fine, but not now.
        BackHandler(enabled = bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }

        val bottomSheetContentState by viewModel.bottomSheetContentState

        BottomSheetScaffold(
            sheetContent = {
                // FIXME: Google issue tracker https://issuetracker.google.com/issues/236160476
                when (bottomSheetContentState) {
                    is NoteBottomSheetContentState.Filter -> {
                        NoteFilterBottomSheetContent()
                    }
                    is NoteBottomSheetContentState.Search -> {
                        NoteSearchBottomSheetContent(bottomSheetScaffoldState, coroutineScope)
                    }
                    is NoteBottomSheetContentState.None -> {
                        // Note: If sheetContent is empty, the following exception occurs:
                        // java.lang.IllegalArgumentException: The initial value must have an associated anchor.
                        // Therefore, added small-sized box.
                        // https://stackoverflow.com/questions/66511309/jetpack-compose-bottom-sheet-initialization-error
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(colorResource(id = R.color.daynight_gray050s))
                        )
                    }
                }
            },
            sheetBackgroundColor = MaterialTheme.colors.onSurface,
            scaffoldState = bottomSheetScaffoldState,
            sheetPeekHeight = 0.dp,
        ) {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    NoteTopAppBar(
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        scope = coroutineScope
                    )
                }
            ) { padding ->
                val uiState = viewModel.uiState.collectAsStateWithLifecycle()

                LazyColumn(modifier = Modifier.padding(padding)) {
                    item {
                        // FIXME: Prevent recomposition when scrolling or new item added.
                        NativeSmallAd()
                    }

                    when (uiState.value.userActionUiState) {
                        UserActionUiState.OnViewing ->
                            onViewingContent(viewModel, uiState.value, windowSizeClass)
                        UserActionUiState.OnSearching ->
                            onSearchingContent(uiState.value.searchedProblemsUiState, windowSizeClass)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLifecycleComposeApi::class)
@Composable
private fun NoteTopAppBar(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val userInput = viewModel.userInputText.collectAsStateWithLifecycle()
    val isYearFiltering = viewModel.isYearFiltering.collectAsStateWithLifecycle()
    val isQuizTypeFiltering = viewModel.isQuizTypeFiltering.collectAsStateWithLifecycle()

    TopAppBar(
        elevation = 0.dp,
        title = {
            Text(
                text = stringResource(id = R.string.note),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            NoteTopMenu(
                bottomSheetScaffoldState,
                userInput.value.isNotBlank(),
                isYearFiltering.value || isQuizTypeFiltering.value,
                scope
            )
        },
    )
}

private fun LazyListScope.onSearchingContent(
    uiState: SearchedProblemsUiState,
    windowSizeClass: WindowSizeClass,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is SearchedProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            SearchedNoteHeaderContent(uiState)
        }

        items(
            items = uiState.data,
            key = { problem ->
                problem.hashCode()
            }
        ) { problem ->
            NoteSummaryContent(problem = problem).takeIf { problem.isValid() }
        }
    }
}

private fun LazyListScope.onViewingContent(
    viewModel: NoteViewModel,
    uiState: NoteUiState,
    windowSizeClass: WindowSizeClass,
) {
    wrongProblemsContent(viewModel, uiState.wrongProblemsUiState, windowSizeClass)
    totalProblemsContent(uiState.totalProblemsUiState, windowSizeClass)
}

@OptIn(ExperimentalLifecycleComposeApi::class)
private fun LazyListScope.wrongProblemsContent(
    viewModel: NoteViewModel,
    uiState: WrongProblemsUiState,
    windowSizeClass: WindowSizeClass,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is WrongProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            val openDeleteAllWrongProblemsDialog =
                viewModel.isDeleteAllWrongProblemsDialogOpened.collectAsStateWithLifecycle()
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

            WrongNoteHeaderContent(
                state = uiState,
                onHeaderLongClick = {
                    viewModel.updateDeleteAllWrongProblemsDialogOpened(true)
                }
            )
        }

        items(
            items = uiState.data.map { it.toWrongProblemModel() },
            // FIXME: Key collision occurs but I don't know how to deal with.
            // key = { wrongProblemModel ->
            //     wrongProblemModel.hashCode()
            // }
        ) { wrongProblemModel ->
            val problem = wrongProblemModel.problem
            NoteSummaryContent(
                problem = problem,
                onProblemLongClick = {
                    Timber.d("Target problem: $problem")
                    viewModel.updateDeleteWrongProblemDialogOpened(true, problem)
                }
            ).takeIf { problem.isValid() }
        }
    }
}

private fun LazyListScope.totalProblemsContent(
    uiState: TotalProblemsUiState,
    windowSizeClass: WindowSizeClass,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is TotalProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            TotalNoteHeaderContent(uiState)
        }

        items(
            items = uiState.data,
            key = { problem ->
                problem.hashCode()
            }
        ) { problem ->
            NoteSummaryContent(problem = problem).takeIf { problem.isValid() }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.itemHeader(
    shouldShowListHeaderAsSticky: Boolean,
    content: @Composable LazyItemScope.() -> Unit,
) {
    if (shouldShowListHeaderAsSticky) {
        stickyHeader {
            content()
        }
    } else {
        item {
            content()
        }
    }
}

@Composable
private fun WrongNoteHeaderContent(
    state: WrongProblemsUiState,
    onHeaderLongClick: Action,
) {
    when (state) {
        is WrongProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Wrong problems(${problems.size}) added.")

            NoteHeader(
                title = stringResource(id = R.string.wrong_note),
                numOfProblems = problems.size,
                onHeaderLongClick = onHeaderLongClick
            )
        }
        is WrongProblemsUiState.Error -> Unit
        is WrongProblemsUiState.Loading -> Unit
    }
}

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
private fun NoteSummaryContent(
    problem: Problem,
    onProblemLongClick: Action? = null,
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val context = LocalContext.current

    val openDeleteWrongProblemDialog =
        viewModel.isDeleteWrongProblemDialogOpened.collectAsStateWithLifecycle()
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

    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    context.startActivity(
                        ProblemDetailActivity.newIntent(
                            context,
                            ProblemDetailMode.Detail,
                            problem.toModel()
                        ),
                    )
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onProblemLongClick?.invoke()
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
                // TODO: Move to designsystem module
                Chip(
                    onClick = {},
                    enabled = false,
                    colors = ChipDefaults.chipColors(
                        contentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        backgroundColor = colorResource(id = R.color.daynight_gray070s),
                        disabledContentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        disabledBackgroundColor = colorResource(id = R.color.daynight_gray070s)
                    ),
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = colorResource(id = R.color.daynight_gray300s)
                    ),
                    modifier = Modifier
                        .padding(all = 4.dp)
                ) {
                    Text(text = "${problem.year}년 ${problem.pid}번")
                }
                Chip(
                    onClick = {},
                    enabled = false,
                    colors = ChipDefaults.chipColors(
                        contentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        backgroundColor = colorResource(id = R.color.daynight_gray070s),
                        disabledContentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        disabledBackgroundColor = colorResource(id = R.color.daynight_gray070s)
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
                    enabled = false,
                    colors = ChipDefaults.chipColors(
                        contentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        backgroundColor = colorResource(id = backgroundColorResourceIdByType),
                        disabledContentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                        disabledBackgroundColor = colorResource(id = backgroundColorResourceIdByType),
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
            text = buildAnnotatedString {
                for (keyword in QuizUtil.highlightKeywords) {
                    if (problem.description.contains(keyword)) {
                        val start = problem.description.indexOf(keyword)
                        val end = start + keyword.length

                        append(problem.description.substring(0, start))
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.daynight_gray900s),
                                textDecoration = TextDecoration.Underline,
                            )
                        ) {
                            append(problem.description.substring(start, end))
                        }
                        append(problem.description.substring(end))
                        return@buildAnnotatedString
                    }
                }

                append(problem.description)
            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
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
private fun TotalNoteHeaderContent(state: TotalProblemsUiState) {
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
        is TotalProblemsUiState.Error -> Unit
        is TotalProblemsUiState.Loading -> Unit
    }
}

@Composable
fun SearchedNoteHeaderContent(state: SearchedProblemsUiState) {
    when (state) {
        is SearchedProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Searched problems(${problems.size}) added.")

            NoteHeader(
                title = stringResource(id = R.string.searched_problem),
                numOfProblems = problems.size
            )
        }
        is SearchedProblemsUiState.Error -> Unit
        is SearchedProblemsUiState.Loading -> Unit
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NoteFilterBottomSheetContent() {
    val viewModel = hiltViewModel<NoteViewModel>()

    LazyColumn {
        item {
            BottomSheetHandle()
        }

        item {
            val openYearFilterDialog = viewModel.isYearFilterDialogOpened.collectAsStateWithLifecycle()
            if (openYearFilterDialog.value) {
                AppCheckboxDialog(
                    icon = painterResource(id = R.drawable.ic_filter),
                    title = stringResource(id = R.string.year),
                    description = stringResource(id = R.string.choose_filtered_years),
                    selectableItems = viewModel.selectableFilteredYears,
                    onConfirm = { items ->
                        Timber.d("Selected: $items")

                        if (!items.any { it.isSelected }) {
                            viewModel.showSnackbar(R.string.msg_need_filtered_year)
                            viewModel.updateYearFilterDialogOpened(false)
                            return@AppCheckboxDialog
                        }

                        viewModel.setFilter(years = items.filter { it.isSelected }.map { it.text.toInt() })
                        viewModel.updateYearFilterDialogOpened(false)
                    },
                    onDismiss = {
                        viewModel.updateYearFilterDialogOpened(false)
                    }
                )
            }

            val openQuizTypeFilterDialog =
                viewModel.isQuizTypeFilterDialogOpened.collectAsStateWithLifecycle()
            if (openQuizTypeFilterDialog.value) {
                AppCheckboxDialog(
                    icon = painterResource(id = R.drawable.ic_filter),
                    title = stringResource(id = R.string.quiz_type),
                    description = stringResource(id = R.string.choose_filtered_types),
                    selectableItems = viewModel.selectableFilteredTypes,
                    onConfirm = { items ->
                        Timber.d("Selected: $items")

                        if (!items.any { it.isSelected }) {
                            viewModel.showSnackbar(R.string.msg_need_filtered_quiz_type)
                            viewModel.updateQuizTypeFilterDialogOpened(false)
                            return@AppCheckboxDialog
                        }

                        viewModel.setFilter(
                            types = items.filter { it.isSelected }.map { QuizType.from(it.text) }
                        )
                        viewModel.updateQuizTypeFilterDialogOpened(false)
                    },
                    onDismiss = {
                        viewModel.updateQuizTypeFilterDialogOpened(false)
                    }
                )
            }

            val isYearFiltering = viewModel.isYearFiltering.collectAsStateWithLifecycle()
            val isQuizTypeFiltering = viewModel.isQuizTypeFiltering.collectAsStateWithLifecycle()

            BottomSheetFilterContent(
                isYearFiltering = isYearFiltering.value,
                isQuizTypeFiltering = isQuizTypeFiltering.value,
                onYearFilter = {
                    viewModel.updateYearFilterDialogOpened(true)
                },
                onTypeFilter = {
                    viewModel.updateQuizTypeFilterDialogOpened(true)
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomSheetFilterContent(
    isYearFiltering: Boolean,
    isQuizTypeFiltering: Boolean,
    onYearFilter: Action,
    onTypeFilter: Action,
) {
    val yearFilterChipBackgroundColor =
        colorResource(id = filterChipBackgroundColorResourceIdByFiltering(isYearFiltering))
    val yearFilterChipStrokeColor =
        colorResource(id = filterChipStrokeColorResourceIdByFiltering(isYearFiltering))

    val quizTypeFilterChipBackgroundColor =
        colorResource(id = filterChipBackgroundColorResourceIdByFiltering(isQuizTypeFiltering))
    val quizTypeFilterChipStrokeColor =
        colorResource(id = filterChipStrokeColorResourceIdByFiltering(isQuizTypeFiltering))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.daynight_gray050s))
    ) {
        Chip(
            onClick = onYearFilter,
            colors = ChipDefaults.chipColors(
                backgroundColor = yearFilterChipBackgroundColor
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = yearFilterChipStrokeColor
            ),
            modifier = Modifier.padding(all = 4.dp)
        ) {
            Text(text = stringResource(id = R.string.year))
        }

        Chip(
            onClick = onTypeFilter,
            colors = ChipDefaults.chipColors(
                backgroundColor = quizTypeFilterChipBackgroundColor
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = quizTypeFilterChipStrokeColor
            ),
            modifier = Modifier.padding(all = 4.dp)
        ) {
            Text(text = stringResource(id = R.string.quiz_type))
        }
    }
}

// TODO: Move to designsystem module
private fun filterChipBackgroundColorResourceIdByFiltering(isFiltering: Boolean) = if (isFiltering) {
    R.color.primaryColor_0_15
} else {
    R.color.daynight_gray070s
}

private fun filterChipStrokeColorResourceIdByFiltering(isFiltering: Boolean) = if (isFiltering) {
    R.color.primaryColor
} else {
    R.color.daynight_gray300s
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun NoteSearchBottomSheetContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    LazyColumn {
        item {
            BottomSheetHandle()
        }

        item {
            BottomSheetSearchContent(bottomSheetScaffoldState, scope)
        }
    }
}

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalLifecycleComposeApi::class
)
@Composable
private fun BottomSheetSearchContent(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    SideEffect {
        if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
            keyboardController?.show()
            focusRequester.requestFocus()
        } else if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
            keyboardController?.hide()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.daynight_gray050s)),
    ) {
        val userInput = viewModel.userInputText.collectAsStateWithLifecycle()

        OutlinedTextField(
            value = userInput.value,
            onValueChange = { text -> viewModel.updateUserInput(text) },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            maxLines = 1,
            placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.daynight_gray900s),
                backgroundColor = colorResource(id = R.color.daynight_gray050a),
                cursorColor = MaterialTheme.colors.secondary,
                disabledTextColor = colorResource(id = R.color.daynight_gray500s),
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            )
        )

        IconButton(
            onClick = { viewModel.updateUserInput("") },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = stringResource(id = R.string.clear_note_searching),
                tint = colorResource(id = R.color.daynight_gray500s)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun NoteHeader(
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
                .background(colorResource(id = R.color.daynight_gray050s))
                .defaultMinSize(minHeight = 52.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Badge(backgroundColor = M3MaterialTheme.colorScheme.primaryContainer) {
                    Text(
                        text = numOfProblems.toString(),
                        color = M3MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
private fun NoteTopMenu(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isSearching: Boolean,
    isFiltering: Boolean,
    scope: CoroutineScope,
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val isMenuExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded
    val bottomSheetContentState = viewModel.bottomSheetContentState.value

    AnimatedVisibility(
        visible = isSearching,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Chip(
            onClick = { viewModel.updateUserInput("") },
            colors = ChipDefaults.chipColors(
                contentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                backgroundColor = colorResource(id = R.color.daynight_gray070s),
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = colorResource(id = R.color.daynight_gray300s)
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_off),
                    contentDescription = stringResource(id = R.string.clear_note_searching),
                    tint = colorResource(id = R.color.daynight_gray300s),
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
        ) {
            Text(text = stringResource(id = R.string.clear_note_searching))
        }
    }

    AnimatedVisibility(
        visible = isFiltering,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Chip(
            onClick = { viewModel.setFilter(years = Problem.allYears(), types = QuizType.all()) },
            colors = ChipDefaults.chipColors(
                contentColor = MaterialTheme.colors.onSurface.copy(alpha = ChipDefaults.ContentOpacity),
                backgroundColor = colorResource(id = R.color.daynight_gray070s),
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = colorResource(id = R.color.daynight_gray300s)
            ),
            modifier = Modifier.padding(all = 4.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(id = R.string.clear_filter),
                    tint = colorResource(id = R.color.daynight_gray300s),
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
        ) {
            Text(text = stringResource(id = R.string.clear_filter))
        }
    }

    IconButton(
        onClick = {
            scope.launch {
                viewModel.updateBottomSheetContentState(NoteBottomSheetContentState.Search)
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }, enabled = isFiltering.not()
    ) {
        val transition =
            updateTransition(
                targetState = isMenuExpanded && bottomSheetContentState is NoteBottomSheetContentState.Search,
                label = "SearchingMenuIconTransition"
            )

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
                if (MaterialTheme.colors.isLight) {
                    // FIXME: After migrate to material3, set to primary color
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.primary
                }
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

    IconButton(
        onClick = {
            scope.launch {
                viewModel.updateBottomSheetContentState(NoteBottomSheetContentState.Filter)
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }, enabled = isSearching.not()
    ) {
        val transition =
            updateTransition(
                targetState = isMenuExpanded && bottomSheetContentState is NoteBottomSheetContentState.Filter,
                label = "FilteringMenuIconTransition"
            )

        val tint by transition.animateColor(
            transitionSpec = {
                if (false isTransitioningTo true) {
                    spring(stiffness = Spring.StiffnessMedium)
                } else {
                    spring(stiffness = Spring.StiffnessLow)
                }
            },
            label = "FilteringMenuIconColor"
        ) { isExpanded ->
            if (isExpanded) {
                if (MaterialTheme.colors.isLight) {
                    // FIXME: After migrate to material3, set to primary color
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.primary
                }
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
        }, label = "FilteringMenuIconSize") { isExpanded ->
            if (isExpanded) 32.dp else 24.dp
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_filter),
            contentDescription = stringResource(id = R.string.filter),
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}
