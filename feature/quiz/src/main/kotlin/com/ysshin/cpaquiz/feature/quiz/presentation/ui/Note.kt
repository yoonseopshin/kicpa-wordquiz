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
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
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
import com.ysshin.cpaquiz.core.android.ui.ad.NativeSmallAd
import com.ysshin.cpaquiz.core.android.ui.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.core.android.ui.dialog.AppCheckboxDialog
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.chipBorderColorResIdByType
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.core.common.Action
import com.ysshin.cpaquiz.core.common.Consumer
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.isValid
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toWrongProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.UserSolvedProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.DeleteWrongProblemDialog
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteMenuContent
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.SearchedProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.TotalProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.WrongProblemsUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.isFiltering
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.isQuizTypeFiltering
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.isYearFiltering
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun NoteScreen(windowSizeClass: WindowSizeClass, viewModel: NoteViewModel = hiltViewModel()) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val noteUiState = viewModel.uiState.collectAsStateWithLifecycle()
    var isMenuOpened by rememberSaveable { mutableStateOf(false) }
    var noteMenuContent by rememberSaveable { mutableStateOf<NoteMenuContent>(NoteMenuContent.Search) }

    BackHandler(enabled = isMenuOpened) {
        isMenuOpened = false
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NoteTopAppBar(
                noteUiState = noteUiState.value,
                isMenuOpened = isMenuOpened,
                toggleMenu = { newNoteMenuContent ->
                    isMenuOpened = (isMenuOpened && noteMenuContent == newNoteMenuContent).not()
                    noteMenuContent = newNoteMenuContent
                },
                noteMenuContent = noteMenuContent
            )
        }
    ) { padding ->
        val shouldShowNativeAd = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

        Column(modifier = Modifier.padding(padding)) {
            AnimatedVisibility(
                visible = isMenuOpened,
                enter = expandVertically() + fadeIn(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface {
                    when (noteMenuContent) {
                        is NoteMenuContent.Filter -> NoteFilterMenuContent(
                            hideMenu = { isMenuOpened = false },
                            snackbarHostState = snackbarHostState
                        )
                        is NoteMenuContent.Search -> NoteSearchMenuContent(
                            isMenuOpened = isMenuOpened,
                            hideMenu = { isMenuOpened = false }
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = shouldShowNativeAd,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                NativeSmallAd()
            }

            var isDeleteAllWrongProblemsDialogOpened by rememberSaveable { mutableStateOf(false) }
            var isDeleteWrongProblemDialogOpened by rememberSaveable {
                mutableStateOf(
                    DeleteWrongProblemDialog(
                        isOpened = false,
                        problem = Problem().toModel()
                    )
                )
            }

            LazyColumn(state = listState) {
                when (val uiState = noteUiState.value) {
                    is NoteUiState.View ->
                        onViewingContent(
                            viewModel = viewModel,
                            totalProblemsUiState = uiState.totalProblemsUiState,
                            wrongProblemsUiState = uiState.wrongProblemsUiState,
                            isDeleteAllWrongProblemsDialogOpened = isDeleteAllWrongProblemsDialogOpened,
                            updateDeletingAllWrongProblemsDialog = { isOpened ->
                                isDeleteAllWrongProblemsDialogOpened = isOpened
                            },
                            isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened,
                            updateDeletingWrongProblemDialogOpened = { dialog ->
                                isDeleteWrongProblemDialogOpened =
                                    isDeleteWrongProblemDialogOpened.copy(
                                        isOpened = dialog.isOpened,
                                        problem = dialog.problem
                                    )
                                Timber.d("dialog info: $dialog")
                            },
                            windowSizeClass = windowSizeClass
                        )
                    is NoteUiState.Search ->
                        onSearchingContent(uiState.searchedProblemsUiState, windowSizeClass)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteTopAppBar(
    noteUiState: NoteUiState,
    isMenuOpened: Boolean,
    toggleMenu: Consumer<NoteMenuContent>,
    noteMenuContent: NoteMenuContent,
) {
    val isSearching: Boolean
    val isFiltering: Boolean

    when (noteUiState) {
        is NoteUiState.View -> {
            isSearching = false
            isFiltering = noteUiState.filter.isFiltering()
        }
        is NoteUiState.Search -> {
            isSearching = noteUiState.keyword.isNotBlank()
            isFiltering = false
        }
    }

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.note),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            NoteTopMenu(
                isMenuOpened = isMenuOpened,
                toggleMenu = toggleMenu,
                noteMenuContent = noteMenuContent,
                isSearching = isSearching,
                isFiltering = isFiltering,
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

        val items = uiState.data
        itemsIndexed(items = items) { index, problem ->
            NoteSummaryContent(problem = problem).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider()
            }
        }
    }
}

private fun LazyListScope.onViewingContent(
    viewModel: NoteViewModel,
    totalProblemsUiState: TotalProblemsUiState,
    wrongProblemsUiState: WrongProblemsUiState,
    isDeleteAllWrongProblemsDialogOpened: Boolean,
    updateDeletingAllWrongProblemsDialog: Consumer<Boolean>,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>,
    windowSizeClass: WindowSizeClass,
) {
    wrongProblemsContent(
        viewModel,
        wrongProblemsUiState,
        isDeleteAllWrongProblemsDialogOpened,
        updateDeletingAllWrongProblemsDialog,
        isDeleteWrongProblemDialogOpened,
        updateDeletingWrongProblemDialogOpened,
        windowSizeClass
    )
    totalProblemsContent(totalProblemsUiState, windowSizeClass)
}

private fun LazyListScope.wrongProblemsContent(
    viewModel: NoteViewModel,
    uiState: WrongProblemsUiState,
    isDeleteAllWrongProblemsDialogOpened: Boolean,
    updateDeletingAllWrongProblemsDialog: Consumer<Boolean>,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>,
    windowSizeClass: WindowSizeClass,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is WrongProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            if (isDeleteAllWrongProblemsDialogOpened) {
                AppInfoDialog(
                    icon = painterResource(id = R.drawable.ic_delete),
                    title = stringResource(id = R.string.delete_wrong_note),
                    description = stringResource(id = R.string.question_delete_all_wrong_note),
                    onConfirm = {
                        viewModel.deleteAllWrongProblems()
                        updateDeletingAllWrongProblemsDialog(false)
                    },
                    onDismiss = {
                        updateDeletingAllWrongProblemsDialog(false)
                    }
                )
            }

            WrongNoteHeaderContent(
                state = uiState,
                onHeaderLongClick = {
                    updateDeletingAllWrongProblemsDialog(true)
                }
            )
        }

        val items = uiState.data.map { it.toWrongProblemModel() }
        itemsIndexed(items = items) { index, wrongProblemModel ->
            val problem = wrongProblemModel.problem
            NoteSummaryContent(
                problem = problem,
                onProblemLongClick = {
                    Timber.d("Target problem: $problem")
                    updateDeletingWrongProblemDialogOpened(
                        isDeleteWrongProblemDialogOpened.copy(
                            isOpened = true,
                            problem = problem.toModel(),
                        )
                    )
                },
                isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened,
                updateDeletingWrongProblemDialogOpened = updateDeletingWrongProblemDialogOpened
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider()
            }
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

        val items = uiState.data
        itemsIndexed(items = items) { index, problem ->
            NoteSummaryContent(problem = problem).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider()
            }
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
    ExperimentalMaterial3Api::class
)
@Composable
private fun LazyItemScope.NoteSummaryContent(
    problem: Problem,
    onProblemLongClick: Action? = null,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog? = null,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>? = null,
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val context = LocalContext.current

    isDeleteWrongProblemDialogOpened?.let { dialog ->
        if (dialog.isOpened) {
            AppInfoDialog(
                icon = painterResource(id = R.drawable.ic_delete),
                title = stringResource(id = R.string.delete_wrong_problem),
                description = stringResource(id = R.string.question_delete_wrong_note),
                onConfirm = {
                    viewModel.deleteTargetWrongProblem(dialog.problem.toDomain())
                    updateDeletingWrongProblemDialogOpened?.invoke(
                        isDeleteWrongProblemDialogOpened.copy(isOpened = false)
                    )
                },
                onDismiss = {
                    updateDeletingWrongProblemDialogOpened?.invoke(
                        isDeleteWrongProblemDialogOpened.copy(isOpened = false)
                    )
                }
            )
        }
    }

    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = {
                    context.startActivity(
                        QuestionActivity.newIntent(
                            context = context,
                            mode = ProblemDetailMode.Detail,
                            problemModel = problem.toModel()
                        )
                    )
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onProblemLongClick?.invoke()
                }
            )
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .animateItemPlacement()
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

                NotClickableAssistedChip(
                    modifier = Modifier.padding(all = 4.dp),
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

                NotClickableAssistedChip(
                    modifier = Modifier.padding(all = 4.dp),
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
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp)
            ) {
                val containerColorResourceIdByType = chipContainerColorResIdByType(problem.type)
                val borderColorResourceIdByType = chipBorderColorResIdByType(problem.type)

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
            style = Typography.bodyMedium
        )
    }
}

@Composable
private fun NoteSummaryDivider() {
    Divider(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    )
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
private fun SearchedNoteHeaderContent(state: SearchedProblemsUiState) {
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
private fun NoteFilterMenuContent(hideMenu: Action, snackbarHostState: SnackbarHostState) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isYearFilterDialogOpened by rememberSaveable { mutableStateOf(false) }

    if (isYearFilterDialogOpened) {
        AppCheckboxDialog(
            icon = painterResource(id = R.drawable.ic_filter),
            title = stringResource(id = R.string.year),
            description = stringResource(id = R.string.choose_filtered_years),
            selectableItems = viewModel.selectableFilteredYears,
            onConfirm = { items ->
                Timber.d("Selected: $items")

                if (!items.any { it.isSelected }) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_need_filtered_year),
                            actionLabel = context.getString(R.string.confirm)
                        )
                    }
                    isYearFilterDialogOpened = false
                    return@AppCheckboxDialog
                }

                viewModel.setFilter(years = items.filter { it.isSelected }.map { it.text.toInt() })
                isYearFilterDialogOpened = false
            },
            onDismiss = {
                isYearFilterDialogOpened = false
            }
        )
    }

    var isQuizTypeFilterDialogOpened by rememberSaveable { mutableStateOf(false) }

    if (isQuizTypeFilterDialogOpened) {
        AppCheckboxDialog(
            icon = painterResource(id = R.drawable.ic_filter),
            title = stringResource(id = R.string.quiz_type),
            description = stringResource(id = R.string.choose_filtered_types),
            selectableItems = viewModel.selectableFilteredTypes,
            onConfirm = { items ->
                Timber.d("Selected: $items")

                if (!items.any { it.isSelected }) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.msg_need_filtered_quiz_type),
                            actionLabel = context.getString(R.string.confirm)
                        )
                    }
                    isQuizTypeFilterDialogOpened = false
                    return@AppCheckboxDialog
                }

                viewModel.setFilter(
                    types = items.filter { it.isSelected }.map { QuizType.from(it.text) }
                )
                isQuizTypeFilterDialogOpened = false
            },
            onDismiss = {
                isQuizTypeFilterDialogOpened = false
            }
        )
    }

    val noteUiState = viewModel.uiState.collectAsStateWithLifecycle()

    val isYearFiltering: Boolean
    val isQuizTypeFiltering: Boolean

    when (val uiState = noteUiState.value) {
        is NoteUiState.View -> {
            isYearFiltering = uiState.filter.isYearFiltering()
            isQuizTypeFiltering = uiState.filter.isQuizTypeFiltering()
        }
        is NoteUiState.Search -> {
            isYearFiltering = false
            isQuizTypeFiltering = false
        }
    }

    NoteFilterMenuContentDetail(
        isYearFiltering = isYearFiltering,
        isQuizTypeFiltering = isQuizTypeFiltering,
        onYearFilter = {
            isYearFilterDialogOpened = true
        },
        onTypeFilter = {
            isQuizTypeFilterDialogOpened = true
        },
        hideMenu = hideMenu
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteFilterMenuContentDetail(
    isYearFiltering: Boolean,
    isQuizTypeFiltering: Boolean,
    onYearFilter: Action,
    onTypeFilter: Action,
    hideMenu: Action,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp))
    ) {
        Spacer(modifier = Modifier.width(12.dp))

        FilterChip(
            selected = isYearFiltering,
            onClick = onYearFilter,
            modifier = Modifier.padding(horizontal = 4.dp),
            label = {
                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                    Text(text = stringResource(id = R.string.year))
                }
            },
            colors = FilterChipDefaults.filterChipColors()
        )

        FilterChip(
            selected = isQuizTypeFiltering,
            onClick = onTypeFilter,
            modifier = Modifier.padding(horizontal = 4.dp),
            label = {
                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                    Text(text = stringResource(id = R.string.quiz_type))
                }
            },
            colors = FilterChipDefaults.filterChipColors()
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = { hideMenu() },
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = stringResource(id = R.string.hide_menu),
                tint = colorResource(id = R.color.daynight_gray600s)
            )
        }
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
private fun NoteSearchMenuContent(isMenuOpened: Boolean, hideMenu: Action) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    SideEffect {
        if (isMenuOpened) {
            scope.launch {
                delay(100L)
                keyboardController?.show()
                focusRequester.requestFocus()
            }
        } else {
            keyboardController?.hide()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp))
            .bringIntoViewRequester(bringIntoViewRequester),
    ) {
        val userInput = viewModel.searchKeyword.collectAsStateWithLifecycle()

        OutlinedTextField(
            value = userInput.value,
            onValueChange = viewModel::updateSearchKeyword,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .focusRequester(focusRequester)
                .onFocusEvent { focusState ->
                    if (focusState.hasFocus || focusState.isFocused) {
                        scope.launch {
                            bringIntoViewRequester.bringIntoView()
                        }
                    }
                },
            maxLines = 1,
            placeholder = { Text(text = stringResource(id = R.string.search_hint)) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    hideMenu()
                }
            )
        )

        IconButton(
            onClick = hideMenu,
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = stringResource(id = R.string.hide_menu),
                tint = colorResource(id = R.color.daynight_gray600s)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp))
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
    isMenuOpened: Boolean,
    toggleMenu: Consumer<NoteMenuContent>,
    noteMenuContent: NoteMenuContent,
    isSearching: Boolean,
    isFiltering: Boolean,
) {
    val viewModel = hiltViewModel<NoteViewModel>()
    val isMenuExpanded = isMenuOpened

    AnimatedVisibility(
        visible = isSearching,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        AssistChip(
            onClick = { viewModel.updateSearchKeyword("") },
            modifier = Modifier.padding(all = 4.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_off),
                    contentDescription = stringResource(id = R.string.clear_search),
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(start = 4.dp)
                )
            },
            label = {
                Text(text = stringResource(id = R.string.clear_search))
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
            toggleMenu(NoteMenuContent.Search)
        },
        enabled = isFiltering.not(),
    ) {
        val isEnabled = isFiltering.not()

        val transition =
            updateTransition(
                targetState = isMenuExpanded && noteMenuContent is NoteMenuContent.Search,
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
                if (isEnabled) {
                    LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                } else {
                    LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                }
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
            toggleMenu(NoteMenuContent.Filter)
        },
        enabled = isSearching.not()
    ) {
        val isEnabled = isSearching.not()

        val transition =
            updateTransition(
                targetState = isMenuExpanded && noteMenuContent is NoteMenuContent.Filter,
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
                if (isEnabled) {
                    LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                } else {
                    LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                }
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
            modifier = Modifier.size(size),
        )
    }
}
