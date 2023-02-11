package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
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
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.modifier.modifyIf
import com.ysshin.cpaquiz.core.android.ui.ad.NativeSmallAd
import com.ysshin.cpaquiz.core.android.ui.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.core.android.ui.dialog.AppCheckboxDialog
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.chipBorderColorResIdByType
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
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
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.NoteFilter
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

@Composable
fun NoteRoute(
    windowSizeClass: WindowSizeClass,
    viewModel: NoteViewModel = hiltViewModel(),
) {
    val noteUiState = viewModel.noteUiState.collectAsStateWithLifecycle()
    val noteFilter = viewModel.noteFilter.collectAsStateWithLifecycle()
    val searchKeyword = viewModel.searchKeyword.collectAsStateWithLifecycle()
    val selectedQuestionInSplitScreen = viewModel.selectedQuestion.collectAsStateWithLifecycle()

    NoteScreen(
        windowSizeClass = windowSizeClass,
        noteUiState = noteUiState.value,
        noteFilter = noteFilter.value,
        searchKeyword = searchKeyword.value,
        selectedQuestionInSplitScreen = selectedQuestionInSplitScreen.value,
        updateSearchKeyword = viewModel::updateSearchKeyword,
        setFilter = viewModel::setFilter,
        selectableFilteredYears = viewModel.selectableFilteredYears,
        selectableFilteredTypes = viewModel.selectableFilteredTypes,
        deleteAllWrongProblems = viewModel::deleteAllWrongProblems,
        deleteTargetWrongProblem = viewModel::deleteTargetWrongProblem,
        setSelectedQuestion = viewModel::setSelectedQuestion
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    windowSizeClass: WindowSizeClass,
    noteUiState: NoteUiState,
    noteFilter: NoteFilter,
    searchKeyword: String,
    selectedQuestionInSplitScreen: Problem?,
    updateSearchKeyword: Consumer<String>,
    setFilter: (List<Int>, List<QuizType>) -> Unit,
    selectableFilteredYears: List<SelectableTextItem>,
    selectableFilteredTypes: List<SelectableTextItem>,
    deleteAllWrongProblems: Action,
    deleteTargetWrongProblem: Consumer<Problem>,
    setSelectedQuestion: (Problem?) -> Unit,
) {
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val useSplitScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    var isMenuOpened by rememberSaveable { mutableStateOf(false) }
    var noteMenuContent by rememberSaveable { mutableStateOf<NoteMenuContent>(NoteMenuContent.Search) }

    BackHandler(enabled = useSplitScreen && selectedQuestionInSplitScreen != null) {
        setSelectedQuestion(null)
    }

    BackHandler(enabled = isMenuOpened) {
        isMenuOpened = false
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NoteTopAppBar(
                isMenuOpened = isMenuOpened,
                toggleMenu = { newNoteMenuContent ->
                    isMenuOpened = (isMenuOpened && noteMenuContent == newNoteMenuContent).not()
                    noteMenuContent = newNoteMenuContent
                },
                noteMenuContent = noteMenuContent,
                noteFilter = noteFilter,
                searchKeyword = searchKeyword,
                updateSearchKeyword = updateSearchKeyword,
                setFilter = setFilter,
            )
        }
    ) { padding ->
        val shouldShowAd = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

        Column(modifier = Modifier.padding(padding)) {
            AnimatedVisibility(
                visible = isMenuOpened,
                enter = expandVertically() + fadeIn(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface {
                    when (noteMenuContent) {
                        is NoteMenuContent.Filter -> NoteFilterMenuContent(
                            noteMenuContent = noteMenuContent,
                            noteFilter = noteFilter,
                            hideMenu = { isMenuOpened = false },
                            selectableFilteredYears = selectableFilteredYears,
                            selectableFilteredTypes = selectableFilteredTypes,
                            setFilter = setFilter,
                            snackbarHostState = snackbarHostState
                        )
                        is NoteMenuContent.Search -> NoteSearchMenuContent(
                            isMenuOpened = isMenuOpened,
                            hideMenu = { isMenuOpened = false },
                            searchKeyword = searchKeyword,
                            updateSearchKeyword = updateSearchKeyword
                        )
                    }
                }
            }

            if (shouldShowAd) {
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

            val context = LocalContext.current

            val onProblemClick: (Problem) -> Unit = { problem ->
                if (useSplitScreen) {
                    setSelectedQuestion(problem)
                } else {
                    setSelectedQuestion(problem)
                    context.startActivity(
                        QuestionActivity.newIntent(
                            context = context,
                            mode = ProblemDetailMode.Detail,
                            problemModel = problem.toModel()
                        )
                    )
                }
            }

            when (getNoteScreenType(useSplitScreen)) {
                NoteScreenType.Question -> {
                    LazyColumn(state = listState) {
                        if (searchKeyword.isBlank()) {
                            onViewingContent(
                                totalProblemsUiState = noteUiState.totalProblemsUiState,
                                wrongProblemsUiState = noteUiState.wrongProblemsUiState,
                                isDeleteAllWrongProblemsDialogOpened = isDeleteAllWrongProblemsDialogOpened,
                                updateDeletingAllWrongProblemsDialog = { isOpened ->
                                    isDeleteAllWrongProblemsDialogOpened = isOpened
                                },
                                deleteAllWrongProblems = deleteAllWrongProblems,
                                isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened,
                                updateDeletingWrongProblemDialogOpened = { dialog ->
                                    isDeleteWrongProblemDialogOpened =
                                        isDeleteWrongProblemDialogOpened.copy(
                                            isOpened = dialog.isOpened,
                                            problem = dialog.problem
                                        )
                                    Timber.d("dialog info: $dialog")
                                },
                                deleteTargetWrongProblem = deleteTargetWrongProblem,
                                onProblemClick = onProblemClick,
                                windowSizeClass = windowSizeClass,
                                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen,
                            )
                        } else {
                            onSearchingContent(
                                noteUiState.searchedProblemsUiState,
                                windowSizeClass,
                                onProblemClick,
                                selectedQuestionInSplitScreen
                            )
                        }
                    }
                }
                NoteScreenType.QuestionWithDetails -> {
                    Row {
                        LazyColumn(state = listState) {
                            if (searchKeyword.isBlank()) {
                                onViewingContent(
                                    totalProblemsUiState = noteUiState.totalProblemsUiState,
                                    wrongProblemsUiState = noteUiState.wrongProblemsUiState,
                                    isDeleteAllWrongProblemsDialogOpened = isDeleteAllWrongProblemsDialogOpened,
                                    updateDeletingAllWrongProblemsDialog = { isOpened ->
                                        isDeleteAllWrongProblemsDialogOpened = isOpened
                                    },
                                    deleteAllWrongProblems = deleteAllWrongProblems,
                                    isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened,
                                    updateDeletingWrongProblemDialogOpened = { dialog ->
                                        isDeleteWrongProblemDialogOpened =
                                            isDeleteWrongProblemDialogOpened.copy(
                                                isOpened = dialog.isOpened,
                                                problem = dialog.problem
                                            )
                                        Timber.d("dialog info: $dialog")
                                    },
                                    deleteTargetWrongProblem = deleteTargetWrongProblem,
                                    onProblemClick = onProblemClick,
                                    windowSizeClass = windowSizeClass,
                                    selectedQuestionInSplitScreen = selectedQuestionInSplitScreen,
                                )
                            } else {
                                onSearchingContent(
                                    noteUiState.searchedProblemsUiState,
                                    windowSizeClass,
                                    onProblemClick,
                                    selectedQuestionInSplitScreen
                                )
                            }
                        }

                        val scrollState = rememberScrollState()

                        LaunchedEffect(selectedQuestionInSplitScreen) {
                            scrollState.animateScrollTo(0)
                        }

                        Crossfade(targetState = selectedQuestionInSplitScreen) { selectedQuestion ->
                            if (selectedQuestion == null) {
                                NoSelectedQuestionScreen()
                            } else {
                                Surface(
                                    modifier = Modifier
                                        .verticalScroll(scrollState)
                                        .padding(bottom = 8.dp)
                                ) {
                                    QuestionDetail(
                                        mode = ProblemDetailMode.Detail,
                                        currentQuestion = selectedQuestion,
                                        selectedQuestionIndex = -1,
                                        onQuestionClick = {},
                                        onSelectAnswer = {},
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoSelectedQuestionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val minSize = 100.dp
        val maxSize = 140.dp
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .sizeIn(
                    minWidth = minSize,
                    minHeight = minSize,
                    maxWidth = maxSize,
                    maxHeight = maxSize,
                ),
            painter = painterResource(id = R.drawable.quiz),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteTopAppBar(
    isMenuOpened: Boolean,
    toggleMenu: Consumer<NoteMenuContent>,
    noteMenuContent: NoteMenuContent,
    noteFilter: NoteFilter,
    searchKeyword: String,
    updateSearchKeyword: Consumer<String>,
    setFilter: (List<Int>, List<QuizType>) -> Unit,
) {
    val isSearching: Boolean
    val isFiltering: Boolean

    when (noteMenuContent) {
        is NoteMenuContent.Filter -> {
            isSearching = false
            isFiltering = noteFilter.isFiltering()
        }
        is NoteMenuContent.Search -> {
            isSearching = searchKeyword.isNotBlank()
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
                updateSearchKeyword = updateSearchKeyword,
                setFilter = setFilter,
            )
        },
    )
}

private fun LazyListScope.onSearchingContent(
    uiState: SearchedProblemsUiState,
    windowSizeClass: WindowSizeClass,
    onProblemClick: ((Problem) -> Unit)? = null,
    selectedQuestionInSplitScreen: Problem? = null,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is SearchedProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            SearchedNoteHeaderContent(uiState, windowSizeClass)
        }

        val items = uiState.data
        itemsIndexed(items = items) { index, problem ->
            NoteSummaryContent(
                problem = problem,
                windowSizeClass = windowSizeClass,
                onProblemClick = onProblemClick,
                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider(windowSizeClass)
            }
        }
    }
}

private fun LazyListScope.onViewingContent(
    totalProblemsUiState: TotalProblemsUiState,
    wrongProblemsUiState: WrongProblemsUiState,
    isDeleteAllWrongProblemsDialogOpened: Boolean,
    updateDeletingAllWrongProblemsDialog: Consumer<Boolean>,
    deleteAllWrongProblems: Action,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>,
    deleteTargetWrongProblem: Consumer<Problem>,
    onProblemClick: (Problem) -> Unit,
    windowSizeClass: WindowSizeClass,
    selectedQuestionInSplitScreen: Problem?,
) {
    wrongProblemsContent(
        wrongProblemsUiState,
        isDeleteAllWrongProblemsDialogOpened,
        updateDeletingAllWrongProblemsDialog,
        deleteAllWrongProblems,
        isDeleteWrongProblemDialogOpened,
        updateDeletingWrongProblemDialogOpened,
        deleteTargetWrongProblem,
        onProblemClick,
        windowSizeClass,
        selectedQuestionInSplitScreen
    )
    totalProblemsContent(
        totalProblemsUiState,
        windowSizeClass,
        onProblemClick,
        selectedQuestionInSplitScreen
    )
}

private fun LazyListScope.wrongProblemsContent(
    uiState: WrongProblemsUiState,
    isDeleteAllWrongProblemsDialogOpened: Boolean,
    updateDeletingAllWrongProblemsDialog: Consumer<Boolean>,
    deleteAllWrongProblems: Action,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>,
    deleteTargetWrongProblem: Consumer<Problem>,
    onProblemClick: (Problem) -> Unit,
    windowSizeClass: WindowSizeClass,
    selectedQuestionInSplitScreen: Problem?,
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
                        deleteAllWrongProblems()
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
                },
                windowSizeClass = windowSizeClass
            )
        }

        val items = uiState.data.map { it.toWrongProblemModel() }
        itemsIndexed(items = items) { index, wrongProblemModel ->
            val problem = wrongProblemModel.problem
            NoteSummaryContent(
                problem = problem,
                windowSizeClass = windowSizeClass,
                onProblemClick = onProblemClick,
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
                updateDeletingWrongProblemDialogOpened = updateDeletingWrongProblemDialogOpened,
                deleteTargetWrongProblem = deleteTargetWrongProblem,
                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen,
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider(windowSizeClass)
            }
        }
    }
}

private fun LazyListScope.totalProblemsContent(
    uiState: TotalProblemsUiState,
    windowSizeClass: WindowSizeClass,
    onProblemClick: (Problem) -> Unit,
    selectedQuestionInSplitScreen: Problem? = null,
) {
    val shouldShowListHeaderAsSticky = windowSizeClass.heightSizeClass != WindowHeightSizeClass.Compact

    if (uiState is TotalProblemsUiState.Success) {
        itemHeader(shouldShowListHeaderAsSticky) {
            TotalNoteHeaderContent(uiState, windowSizeClass)
        }

        val items = uiState.data
        itemsIndexed(items = items) { index, problem ->
            NoteSummaryContent(
                problem = problem,
                windowSizeClass = windowSizeClass,
                onProblemClick = onProblemClick,
                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen,
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                NoteSummaryDivider(windowSizeClass)
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
    windowSizeClass: WindowSizeClass,
) {
    when (state) {
        is WrongProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Wrong problems(${problems.size}) added.")

            if (problems.isNotEmpty()) {
                NoteHeader(
                    windowSizeClass = windowSizeClass,
                    title = stringResource(id = R.string.wrong_note),
                    numOfProblems = problems.size,
                    onHeaderLongClick = onHeaderLongClick
                )
            }
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
    windowSizeClass: WindowSizeClass,
    onProblemClick: ((Problem) -> Unit)? = null,
    onProblemLongClick: Action? = null,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog? = null,
    updateDeletingWrongProblemDialogOpened: Consumer<DeleteWrongProblemDialog>? = null,
    deleteTargetWrongProblem: Consumer<Problem>? = null,
    selectedQuestionInSplitScreen: Problem? = null,
) {
    val useSplitScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

    isDeleteWrongProblemDialogOpened?.let { dialog ->
        if (dialog.isOpened) {
            AppInfoDialog(
                icon = painterResource(id = R.drawable.ic_delete),
                title = stringResource(id = R.string.delete_wrong_problem),
                description = stringResource(id = R.string.question_delete_wrong_note),
                onConfirm = {
                    deleteTargetWrongProblem?.invoke(dialog.problem.toDomain())
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
                    onProblemClick?.invoke(problem)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onProblemLongClick?.invoke()
                }
            )
            .modifyIf(useSplitScreen && problem == selectedQuestionInSplitScreen) {
                background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(0.5.dp))
                    .then(border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = RectangleShape))
            }
            .widthBySplit(useSplitScreen)
            .padding(bottom = 20.dp)
            .animateItemPlacement()
    ) {
        Box {
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

                val containerColorResourceIdByType = chipContainerColorResIdByType(problem.type)
                val borderColorResourceIdByType = chipBorderColorResIdByType(problem.type)

                NotClickableAssistedChip(
                    modifier = Modifier.padding(all = 4.dp),
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
private fun NoteSummaryDivider(windowSizeClass: WindowSizeClass) {
    val useSplitScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    Divider(
        modifier = Modifier
            .widthBySplit(useSplitScreen)
            .padding(horizontal = 12.dp),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    )
}

@Composable
private fun TotalNoteHeaderContent(state: TotalProblemsUiState, windowSizeClass: WindowSizeClass) {
    when (state) {
        is TotalProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Total problems(${problems.size}) added.")

            NoteHeader(
                windowSizeClass = windowSizeClass,
                title = stringResource(id = R.string.total_note),
                numOfProblems = problems.size
            )
        }
        is TotalProblemsUiState.Error -> Unit
        is TotalProblemsUiState.Loading -> Unit
    }
}

@Composable
private fun SearchedNoteHeaderContent(state: SearchedProblemsUiState, windowSizeClass: WindowSizeClass) {
    when (state) {
        is SearchedProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Searched problems(${problems.size}) added.")

            NoteHeader(
                windowSizeClass = windowSizeClass,
                title = stringResource(id = R.string.searched_problem),
                numOfProblems = problems.size
            )
        }
        is SearchedProblemsUiState.Error -> Unit
        is SearchedProblemsUiState.Loading -> Unit
    }
}

@Composable
private fun NoteFilterMenuContent(
    noteMenuContent: NoteMenuContent,
    noteFilter: NoteFilter,
    hideMenu: Action,
    selectableFilteredYears: List<SelectableTextItem>,
    selectableFilteredTypes: List<SelectableTextItem>,
    setFilter: (List<Int>, List<QuizType>) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isYearFilterDialogOpened by rememberSaveable { mutableStateOf(false) }

    if (isYearFilterDialogOpened) {
        AppCheckboxDialog(
            icon = painterResource(id = R.drawable.ic_filter),
            title = stringResource(id = R.string.year),
            description = stringResource(id = R.string.choose_filtered_years),
            selectableItems = selectableFilteredYears,
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

                setFilter(items.filter { it.isSelected }.map { it.text.toInt() }, emptyList())
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
            selectableItems = selectableFilteredTypes,
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

                setFilter(emptyList(), items.filter { it.isSelected }.map { QuizType.from(it.text) })
                isQuizTypeFilterDialogOpened = false
            },
            onDismiss = {
                isQuizTypeFilterDialogOpened = false
            }
        )
    }

    val isYearFiltering: Boolean
    val isQuizTypeFiltering: Boolean

    when (noteMenuContent) {
        is NoteMenuContent.Filter -> {
            isYearFiltering = noteFilter.isYearFiltering()
            isQuizTypeFiltering = noteFilter.isQuizTypeFiltering()
        }
        is NoteMenuContent.Search -> {
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
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
private fun NoteSearchMenuContent(
    isMenuOpened: Boolean,
    hideMenu: Action,
    searchKeyword: String,
    updateSearchKeyword: Consumer<String>,
) {
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    var keyword by remember {
        mutableStateOf(TextFieldValue(searchKeyword))
    }

    LaunchedEffect(isMenuOpened) {
        if (isMenuOpened) {
            delay(100L)
            keyboardController?.show()
            focusRequester.requestFocus()
        } else {
            keyboardController?.hide()
        }
    }

    LaunchedEffect(searchKeyword) {
        if (searchKeyword != keyword.text) {
            keyword = TextFieldValue(text = searchKeyword, selection = TextRange(keyword.text.length))
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
        OutlinedTextField(
            value = keyword,
            onValueChange = { newKeyword ->
                keyword = newKeyword
                updateSearchKeyword(keyword.text)
            },
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
                }
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        keyword = keyword.copy(selection = TextRange(keyword.text.length))
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
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun NoteHeader(
    windowSizeClass: WindowSizeClass,
    title: String = "",
    numOfProblems: Int = 0,
    onHeaderClick: Action = {},
    onHeaderLongClick: Action = {},
) {
    val haptic = LocalHapticFeedback.current
    val useSplitScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact

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
            .widthBySplit(useSplitScreen)
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

            Badge(
                modifier = Modifier.animateContentSize(),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                AnimatedContent(
                    targetState = numOfProblems,
                    transitionSpec = {
                        fadeIn() with fadeOut()
                    }
                ) { numOfProblems ->
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
    updateSearchKeyword: Consumer<String>,
    setFilter: (List<Int>, List<QuizType>) -> Unit,
) {
    AnimatedVisibility(
        visible = isSearching,
        enter = scaleIn(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut(animationSpec = tween(300)) + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        AssistChip(
            onClick = { updateSearchKeyword("") },
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
            onClick = { setFilter(Problem.allYears(), QuizType.all()) },
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
                targetState = isMenuOpened && noteMenuContent is NoteMenuContent.Search,
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
            imageVector = if (isMenuOpened) Icons.Filled.Search else Icons.Outlined.Search,
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
                targetState = isMenuOpened && noteMenuContent is NoteMenuContent.Filter,
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

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun NoteScreenViewPreview() {
    CpaQuizTheme {
        BoxWithConstraints {
            NoteScreen(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight)),
                noteUiState = NoteUiState(
                    totalProblemsUiState = TotalProblemsUiState.Success(
                        listOf(
                            Problem(year = 2023, pid = 1, type = QuizType.Accounting, description = "blah"),
                            Problem(year = 2023, pid = 2, type = QuizType.Business, description = "blahblah"),
                        )
                    ),
                    wrongProblemsUiState = WrongProblemsUiState.Success(
                        listOf(
                            Problem(
                                year = 2022,
                                pid = 15,
                                type = QuizType.CommercialLaw,
                                description = "hello"
                            ),
                            Problem(year = 2021, pid = 22, type = QuizType.TaxLaw, description = "world"),
                        )
                    ),
                    searchedProblemsUiState = SearchedProblemsUiState.Loading,
                ),
                searchKeyword = "",
                selectedQuestionInSplitScreen = Problem(),
                updateSearchKeyword = {},
                setFilter = { _, _ -> },
                selectableFilteredYears = listOf(),
                selectableFilteredTypes = listOf(),
                deleteAllWrongProblems = { },
                deleteTargetWrongProblem = { },
                noteFilter = NoteFilter.default(),
                setSelectedQuestion = {},
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
private fun NoteScreenSearchPreview() {
    CpaQuizTheme {
        BoxWithConstraints {
            NoteScreen(
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(maxWidth, maxHeight)),
                noteUiState = NoteUiState(
                    totalProblemsUiState = TotalProblemsUiState.Loading,
                    wrongProblemsUiState = WrongProblemsUiState.Loading,
                    searchedProblemsUiState = SearchedProblemsUiState.Success(
                        listOf(
                            Problem(
                                year = 2022,
                                pid = 15,
                                type = QuizType.CommercialLaw,
                                description = "blah"
                            ),
                            Problem(
                                year = 2021,
                                pid = 22,
                                type = QuizType.TaxLaw,
                                description = "blahblah"
                            ),
                            Problem(
                                year = 2020,
                                pid = 12,
                                type = QuizType.Business,
                                description = "blahblahblah"
                            ),
                        )
                    ),
                ),
                searchKeyword = "blah",
                selectedQuestionInSplitScreen = Problem(),
                updateSearchKeyword = {},
                setFilter = { _, _ -> },
                selectableFilteredYears = listOf(),
                selectableFilteredTypes = listOf(),
                deleteAllWrongProblems = { },
                deleteTargetWrongProblem = { },
                noteFilter = NoteFilter.default(),
                setSelectedQuestion = {},
            )
        }
    }
}

private enum class NoteScreenType {
    Question,
    QuestionWithDetails
}

private fun getNoteScreenType(useSplitScreen: Boolean) =
    if (useSplitScreen) NoteScreenType.QuestionWithDetails else NoteScreenType.Question

private fun Modifier.widthBySplit(useSplitScreen: Boolean) =
    this.then(Modifier.fillMaxWidth(if (useSplitScreen) 0.45f else 1f))
