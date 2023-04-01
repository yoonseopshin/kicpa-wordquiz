package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.ad.NativeSmallAd
import com.ysshin.cpaquiz.core.android.ui.dialog.AppCheckboxDialog
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.dialog.SelectableTextItem
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.base.Action
import com.ysshin.cpaquiz.core.base.Consumer
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.model.isValid
import com.ysshin.cpaquiz.feature.quiz.R
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
        clearFilter = viewModel::clearFilter,
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
    clearFilter: () -> Unit,
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

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
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
            clearFilter = clearFilter,
        )
    }) { padding ->
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
                        isOpened = false, problem = Problem.default().toModel()
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
                    LazyColumn(
                        modifier = Modifier.resourceTestTag("noteLazyColumn"), state = listState
                    ) {
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
                                    isDeleteWrongProblemDialogOpened = isDeleteWrongProblemDialogOpened.copy(
                                        isOpened = dialog.isOpened, problem = dialog.problem
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
                        LazyColumn(
                            modifier = Modifier.resourceTestTag("noteLazyColumn"), state = listState
                        ) {
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
                                                isOpened = dialog.isOpened, problem = dialog.problem
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
        modifier = Modifier.fillMaxSize()
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
    clearFilter: () -> Unit,
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
                clearFilter = clearFilter,
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
            QuestionSummaryContent(
                problem = problem,
                windowSizeClass = windowSizeClass,
                onProblemClick = onProblemClick,
                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                QuestionSummaryDivider(windowSizeClass)
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
        totalProblemsUiState, windowSizeClass, onProblemClick, selectedQuestionInSplitScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
                state = uiState, onHeaderLongClick = {
                    updateDeletingAllWrongProblemsDialog(true)
                }, windowSizeClass = windowSizeClass
            )
        }

        val items = uiState.data.map { it.toWrongProblemModel() }

        itemsIndexed(items = items, key = { index, problem ->
            "${index}_$problem"
        }) { index, wrongProblemModel ->
            val scope = rememberCoroutineScope()
            var isSlideUp by remember {
                mutableStateOf(false)
            }
            val animationDurationMillis = 150L

            val dismissState = rememberDismissState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == DismissValue.DismissedToStart) {
                        scope.launch {
                            isSlideUp = true
                            delay(animationDurationMillis)
                            deleteTargetWrongProblem(wrongProblemModel.problem)
                        }
                        true
                    } else {
                        false
                    }
                },
            )

            SwipeToDismiss(
                state = dismissState,
                background = {
                    val multiplier = 3
                    val alpha = (dismissState.progress * multiplier).coerceAtMost(1f)
                    val scale by animateFloatAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) 0.66f else 1f
                    )
                    val paddingEnd by animateDpAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) 12.dp else 24.dp
                    )
                    val backgroundColor by animateColorAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = alpha)
                        }
                    )
                    val onBackgroundColor by animateColorAsState(
                        targetValue = if (dismissState.targetValue == DismissValue.Default) {
                            MaterialTheme.colorScheme.onBackground
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer.copy(alpha = alpha)
                        }
                    )

                    AnimatedVisibility(
                        visible = isSlideUp.not(),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Top,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                                .padding(end = paddingEnd),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .scale(scale),
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = stringResource(id = R.string.delete_wrong_note),
                                tint = onBackgroundColor,
                            )
                        }
                    }
                },
                dismissContent = {
                    val problem = wrongProblemModel.problem

                    AnimatedVisibility(
                        visible = isSlideUp.not(),
                        exit = shrinkVertically(
                            shrinkTowards = Alignment.Top,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
                        )
                    ) {
                        QuestionSummaryContent(
                            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
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
                    }

                },
                directions = setOf(DismissDirection.EndToStart)
            )

            if (index < items.lastIndex) {
                QuestionSummaryDivider(windowSizeClass)
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
            QuestionSummaryContent(
                problem = problem,
                windowSizeClass = windowSizeClass,
                onProblemClick = onProblemClick,
                selectedQuestionInSplitScreen = selectedQuestionInSplitScreen,
            ).takeIf { problem.isValid() }

            if (index < items.lastIndex) {
                QuestionSummaryDivider(windowSizeClass)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.itemHeader(
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
                QuestionSummaryHeader(
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

@Composable
private fun TotalNoteHeaderContent(state: TotalProblemsUiState, windowSizeClass: WindowSizeClass) {
    when (state) {
        is TotalProblemsUiState.Success -> {
            val problems = state.data.map { problem ->
                UserSolvedProblemModel(problem = problem)
            }
            Timber.d("Total problems(${problems.size}) added.")

            QuestionSummaryHeader(
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

            QuestionSummaryHeader(
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
                            withDismissAction = true,
                            duration = SnackbarDuration.Short,
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
                            withDismissAction = true,
                            duration = SnackbarDuration.Short,
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
            onClick = { hideMenu() }, modifier = Modifier.padding(end = 4.dp)
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
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                hideMenu()
            })
        )

        IconButton(
            onClick = hideMenu, modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowUp,
                contentDescription = stringResource(id = R.string.hide_menu),
                tint = MaterialTheme.colorScheme.secondary
            )
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
    clearFilter: () -> Unit,
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
        AssistChip(onClick = { clearFilter() }, modifier = Modifier.padding(all = 4.dp), label = {
            Text(text = stringResource(id = R.string.clear_filter))
        }, leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = stringResource(id = R.string.clear_filter),
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 4.dp)
            )
        })
    }

    IconButton(
        onClick = {
            toggleMenu(NoteMenuContent.Search)
        },
        enabled = isFiltering.not(),
    ) {
        val isEnabled = isFiltering.not()

        val transition = updateTransition(
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
            }, label = "SearchingMenuIconColor"
        ) { isExpanded ->
            if (isExpanded) {
                MaterialTheme.colorScheme.primary
            } else {
                if (isEnabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
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
        }, enabled = isSearching.not()
    ) {
        val isEnabled = isSearching.not()

        val transition = updateTransition(
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
            }, label = "FilteringMenuIconColor"
        ) { isExpanded ->
            if (isExpanded) {
                MaterialTheme.colorScheme.primary
            } else {
                if (isEnabled) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
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
                            Problem(
                                year = 2023,
                                pid = 1,
                                type = QuizType.Accounting,
                                description = "blah",
                                source = ProblemSource.CPA
                            ),
                            Problem(
                                year = 2023,
                                pid = 2,
                                type = QuizType.Business,
                                description = "blahblah",
                                source = ProblemSource.CPA
                            ),
                        )
                    ),
                    wrongProblemsUiState = WrongProblemsUiState.Success(
                        listOf(
                            Problem(
                                year = 2022,
                                pid = 15,
                                type = QuizType.CommercialLaw,
                                description = "hello",
                                source = ProblemSource.CPA
                            ),
                            Problem(
                                year = 2021,
                                pid = 22,
                                type = QuizType.TaxLaw,
                                description = "world",
                                source = ProblemSource.CPA
                            ),
                        )
                    ),
                    searchedProblemsUiState = SearchedProblemsUiState.Loading,
                ),
                searchKeyword = "",
                selectedQuestionInSplitScreen = Problem.default(),
                updateSearchKeyword = {},
                setFilter = { _, _ -> },
                clearFilter = { },
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
                                description = "blah",
                                source = ProblemSource.CPA
                            ),
                            Problem(
                                year = 2021,
                                pid = 22,
                                type = QuizType.TaxLaw,
                                description = "blahblah",
                                source = ProblemSource.CPA
                            ),
                            Problem(
                                year = 2020,
                                pid = 12,
                                type = QuizType.Business,
                                description = "blahblahblah",
                                source = ProblemSource.CPA
                            ),
                        )
                    ),
                ),
                searchKeyword = "blah",
                selectedQuestionInSplitScreen = Problem.default(),
                updateSearchKeyword = { },
                setFilter = { _, _ -> },
                clearFilter = { },
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
    Question, QuestionWithDetails
}

private fun getNoteScreenType(useSplitScreen: Boolean) =
    if (useSplitScreen) NoteScreenType.QuestionWithDetails else NoteScreenType.Question

fun Modifier.widthBySplit(useSplitScreen: Boolean) =
    this.then(Modifier.fillMaxWidth(if (useSplitScreen) 0.45f else 1f))
