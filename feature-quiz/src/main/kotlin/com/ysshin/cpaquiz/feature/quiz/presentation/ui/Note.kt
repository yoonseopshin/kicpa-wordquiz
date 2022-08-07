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
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppCheckboxDialog
import com.ysshin.cpaquiz.shared.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.shared.base.Action
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(windowSizeClass: WindowSizeClass, viewModel: NoteViewModel = hiltViewModel()) {
    val context = LocalContext.current

    BackHandler(enabled = viewModel.isMenuOpened.value) {
        viewModel.isMenuOpened.value = false
    }

    val bottomSheetContentState by viewModel.bottomSheetContentState

    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(listState, snackbarHostState) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is NoteUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.actionLabel.asString(context)
                    )
                }
                is NoteUiEvent.ScrollToTop -> {
                    listState.animateScrollToItem(0)
                }
            }
        }
    }

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarState()
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NoteTopAppBar(windowSizeClass = windowSizeClass, scrollBehavior = scrollBehavior)
        }
    ) { padding ->
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val shouldShowMenu = viewModel.isMenuOpened.value
        val shouldShowNativeAd =
            shouldShowMenu.not() && windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

        LazyColumn(
            state = listState,
            modifier = Modifier.padding(padding)
        ) {
            if (shouldShowMenu) {
                item {
                    Surface {
                        when (bottomSheetContentState) {
                            is NoteBottomSheetContentState.Filter -> NoteFilterMenuContent()
                            is NoteBottomSheetContentState.Search -> NoteSearchMenuContent()
                            is NoteBottomSheetContentState.None -> Unit
                        }
                    }
                }
            }

            if (shouldShowNativeAd) {
                item {
                    // FIXME: Prevent recomposition when scrolling or new item added.
                    NativeSmallAd()
                }
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

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun NoteTopAppBar(
    windowSizeClass: WindowSizeClass,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val shouldShowLargeTopAppBar = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact
    val viewModel = hiltViewModel<NoteViewModel>()
    val userInput = viewModel.userInputText.collectAsStateWithLifecycle()
    val isYearFiltering = viewModel.isYearFiltering.collectAsStateWithLifecycle()
    val isQuizTypeFiltering = viewModel.isQuizTypeFiltering.collectAsStateWithLifecycle()

    if (shouldShowLargeTopAppBar) {
        LargeTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.note),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            actions = {
                NoteTopMenu(
                    userInput.value.isNotBlank(),
                    isYearFiltering.value || isQuizTypeFiltering.value,
                )
            },
            scrollBehavior = scrollBehavior
        )
    } else {
        SmallTopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.note),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            actions = {
                NoteTopMenu(
                    userInput.value.isNotBlank(),
                    isYearFiltering.value || isQuizTypeFiltering.value,
                )
            },
        )
    }
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

        items(items = uiState.data.map { it.toWrongProblemModel() }) { wrongProblemModel ->
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

        items(items = uiState.data) { problem ->
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
    ExperimentalFoundationApi::class,
    ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class
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
                val assistChipContainerColor =
                    colorResource(id = R.color.daynight_gray070s)
                val assistChipBorderColor =
                    colorResource(id = R.color.daynight_gray300s)

                Box {
                    AssistChip(
                        modifier = Modifier.padding(all = 4.dp),
                        onClick = {},
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = "${problem.year}년 ${problem.pid}번")
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(containerColor = assistChipContainerColor),
                        border = AssistChipDefaults.assistChipBorder(
                            borderColor = assistChipBorderColor,
                            borderWidth = 0.5.dp
                        )
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0f)
                            .clickable(onClick = {})
                    )
                }

                Box {
                    AssistChip(
                        modifier = Modifier.padding(all = 4.dp),
                        onClick = {},
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = problem.source.toString())
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(containerColor = assistChipContainerColor),
                        border = AssistChipDefaults.assistChipBorder(
                            borderColor = assistChipBorderColor,
                            borderWidth = 0.5.dp
                        )
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0f)
                            .clickable(onClick = {})
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp)
            ) {
                val containerColorResourceIdByType = when (problem.type) {
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

                Box {
                    AssistChip(
                        modifier = Modifier.padding(all = 4.dp),
                        onClick = {},
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = problem.type.toKorean())
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = colorResource(id = containerColorResourceIdByType)
                        ),
                        border = AssistChipDefaults.assistChipBorder(
                            borderColor = colorResource(id = borderColorResourceIdByType),
                            borderWidth = 0.5.dp,
                        )
                    )

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .alpha(0f)
                            .clickable(onClick = {})
                    )
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
fun NoteFilterMenuContent() {
    val viewModel = hiltViewModel<NoteViewModel>()

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetFilterContent(
    isYearFiltering: Boolean,
    isQuizTypeFiltering: Boolean,
    onYearFilter: Action,
    onTypeFilter: Action,
) {
    val yearAssistChipContainerColor =
        colorResource(id = filterChipBackgroundColorResourceIdByFiltering(isYearFiltering))
    val yearAssistChipBorderColor =
        colorResource(id = filterChipStrokeColorResourceIdByFiltering(isYearFiltering))

    val quizTypeAssistChipContainerColor =
        colorResource(id = filterChipBackgroundColorResourceIdByFiltering(isQuizTypeFiltering))
    val quizTypeAssistChipBorderColor =
        colorResource(id = filterChipStrokeColorResourceIdByFiltering(isQuizTypeFiltering))

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.daynight_gray050s))
    ) {
        AssistChip(
            onClick = onYearFilter,
            modifier = Modifier.padding(all = 4.dp),
            label = { Text(text = stringResource(id = R.string.year)) },
            colors = AssistChipDefaults.assistChipColors(containerColor = yearAssistChipContainerColor),
            border = AssistChipDefaults.assistChipBorder(
                borderColor = yearAssistChipBorderColor,
                borderWidth = 0.5.dp
            )
        )

        AssistChip(
            onClick = onTypeFilter,
            modifier = Modifier.padding(all = 4.dp),
            label = { Text(text = stringResource(id = R.string.quiz_type)) },
            colors = AssistChipDefaults.assistChipColors(containerColor = quizTypeAssistChipContainerColor),
            border = AssistChipDefaults.assistChipBorder(
                borderColor = quizTypeAssistChipBorderColor,
                borderWidth = 0.5.dp
            )
        )
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

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class
)
@Composable
private fun NoteSearchMenuContent() {
    val viewModel = hiltViewModel<NoteViewModel>()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    SideEffect {
        if (viewModel.isMenuOpened.value) {
            keyboardController?.show()
            focusRequester.requestFocus()
        } else {
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
            onValueChange = { text: String -> viewModel.updateUserInput(text) },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            maxLines = 1,
            placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.isMenuOpened.value = false
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

                Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                    Text(
                        text = numOfProblems.toString(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun NoteTopMenu(
    isSearching: Boolean,
    isFiltering: Boolean,
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val isMenuExpanded = viewModel.isMenuOpened.value
    val bottomSheetContentState = viewModel.bottomSheetContentState.value

    AnimatedVisibility(
        visible = isSearching,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        AssistChip(
            onClick = { viewModel.updateUserInput("") },
            modifier = Modifier.padding(all = 4.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_off),
                    contentDescription = stringResource(id = R.string.clear_note_searching),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.clear_note_searching))
            }
        )
    }

    AnimatedVisibility(
        visible = isFiltering,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        AssistChip(
            onClick = { viewModel.setFilter(years = Problem.allYears(), types = QuizType.all()) },
            modifier = Modifier.padding(all = 4.dp),
            label = {
                Text(text = stringResource(id = R.string.clear_filter))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = stringResource(id = R.string.clear_filter),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        )
    }

    IconButton(
        onClick = {
            viewModel.updateBottomSheetContentState(NoteBottomSheetContentState.Search)
            viewModel.isMenuOpened.value = true
            viewModel.scrollToTop()
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
                MaterialTheme.colorScheme.primary
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
            viewModel.updateBottomSheetContentState(NoteBottomSheetContentState.Filter)
            // TODO: toggle로 처리하고 이벤트는 내부에서 처리.
            viewModel.isMenuOpened.value = true
            viewModel.scrollToTop()
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
                MaterialTheme.colorScheme.primary
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
