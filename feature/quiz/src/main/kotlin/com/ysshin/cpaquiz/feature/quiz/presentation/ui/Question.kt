package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.modifier.modifyIf
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.animation.PopScaleAnimation
import com.ysshin.cpaquiz.core.android.ui.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.RegexUtils
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.DeleteWrongProblemDialog
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuizState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.SnackbarState
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil

// TODO: Hoist to QuestionRoute
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(viewModel: QuestionViewModel = hiltViewModel()) {
    val numOfSolvedQuestion = viewModel.numOfSolvedQuestions.collectAsStateWithLifecycle()
    val numOfTotalQuestion = viewModel.numOfTotalQuestions.collectAsStateWithLifecycle()
    val currentQuestion = viewModel.currentQuestion.collectAsStateWithLifecycle()
    val isToolbarTitleVisible = viewModel.isToolbarTitleVisible.collectAsStateWithLifecycle()
    val mode = viewModel.mode.collectAsStateWithLifecycle()
    val quizState = viewModel.quizState.collectAsStateWithLifecycle()
    val snackbarState = viewModel.snackbarState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                viewModel.onPause()
            } else if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.onResume()
            }
        }

        val lifecycle = lifecycleOwner.lifecycle

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    val context = LocalContext.current
    val activity = context.findActivity()
    val appContext = context.applicationContext
    val snackbarHostState = remember { SnackbarHostState() }
    val questionContentScrollState = rememberScrollState()
    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    LaunchedEffect(quizState.value) {
        when (quizState.value) {
            is QuizState.Solving -> {
                if (mode.value == ProblemDetailMode.Quiz) {
                    topAppBarState.heightOffset = 0f
                    topAppBarState.contentOffset = 0f
                }
                questionContentScrollState.animateScrollTo(0)
            }
            QuizState.Grading -> Unit
            QuizState.End -> {
                val quizEndNavActions = (appContext as QuizEndNavigationActionsProvider).quizEndNavActions
                quizEndNavActions.onQuizEnd(
                    activity = activity,
                    problems = viewModel.questions,
                    selected = viewModel.selected,
                    timesPerQuestion = viewModel.timesPerQuestion,
                )
            }
        }
    }

    LaunchedEffect(snackbarState.value) {
        when (val state = snackbarState.value) {
            is SnackbarState.Show -> {
                snackbarHostState.showSnackbar(
                    message = state.message.asString(context),
                    actionLabel = state.actionLabel.asString(context),
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
            }
            SnackbarState.Hide -> Unit
        }
    }

    val selectedQuestionIndex = viewModel.selectedQuestionIndex.collectAsStateWithLifecycle()
    val isPopScaleAnimationShowing = viewModel.isAnimationShowing.collectAsStateWithLifecycle()
    val popScaleAnimationInfo = viewModel.animationInfo.collectAsStateWithLifecycle()
    val useTimer = viewModel.useTimer.collectAsStateWithLifecycle()
    val elapsedTime = viewModel.elapsedTime.collectAsStateWithLifecycle()
    val isFabVisible = viewModel.isFabVisible.collectAsStateWithLifecycle()

    var fabPosition by remember { mutableStateOf(Offset.Zero) }
    var quizDetailSize by remember { mutableStateOf(IntSize.Zero) }
    var quizDetailPosition by remember { mutableStateOf(Offset.Zero) }

    CpaQuizTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                QuestionTopAppBar(
                    isToolbarTitleVisible.value,
                    numOfTotalQuestion.value,
                    numOfSolvedQuestion.value,
                    useTimer.value,
                    elapsedTime.value,
                    activity::finish,
                    topAppBarScrollBehavior,
                )
            },
            floatingActionButton = {
                QuestionFloatingActionButton(
                    isFabVisible = isFabVisible.value,
                    fabPosition = fabPosition,
                    quizDetailSize = quizDetailSize,
                    quizDetailPosition = quizDetailPosition,
                    onFabGloballyPositioned = { coordinates ->
                        fabPosition = coordinates.positionInWindow()
                    },
                    onFabClick = {
                        if (quizState.value == QuizState.Solving) {
                            viewModel.selectAnswer()
                        }
                    }
                )
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(questionContentScrollState)
                    .padding(bottom = 8.dp)
            ) {
                Column(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        quizDetailSize = coordinates.size
                        quizDetailPosition = coordinates.positionInWindow()
                    }
                ) {
                    val question = currentQuestion.value

                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 8.dp)
                        ) {
                            item {
                                val assistChipContainerColor = colorResource(id = R.color.daynight_gray070s)
                                NotClickableAssistedChip(
                                    modifier = Modifier.padding(all = 4.dp),
                                    label = {
                                        ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                            Text(text = "${question.source} ${question.year}년 ${question.pid}번")
                                        }
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = assistChipContainerColor
                                    ),
                                    border = null,
                                )
                            }

                            val containerColorResourceIdByType =
                                chipContainerColorResIdByType(question.type)

                            item {
                                NotClickableAssistedChip(
                                    modifier = Modifier.padding(all = 4.dp),
                                    label = {
                                        ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                            Text(text = question.type.toKorean())
                                        }
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = colorResource(id = containerColorResourceIdByType)
                                    ),
                                    border = null,
                                )
                            }

                            item {
                                if (question.subtype.isNotBlank()) {
                                    NotClickableAssistedChip(
                                        modifier = Modifier.padding(all = 4.dp),
                                        label = {
                                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                                Text(text = question.subtype)
                                            }
                                        },
                                        colors = AssistChipDefaults.assistChipColors(
                                            containerColor = colorResource(id = containerColorResourceIdByType)
                                        ),
                                        border = null,
                                    )
                                }
                            }
                        }
                    }

                    QuestionDetail(
                        mode = mode.value,
                        currentQuestion = question,
                        selectedQuestionIndex = selectedQuestionIndex.value,
                        onQuestionClick = viewModel::selectQuestion,
                        onSelectAnswer = viewModel::selectAnswer
                    )
                }
            }

            PopScaleAnimation(
                isVisible = isPopScaleAnimationShowing.value,
                circleColor = colorResource(id = popScaleAnimationInfo.value.backgroundColorResId),
                radius = 360f,
            ) {
                Icon(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                    imageVector = popScaleAnimationInfo.value.icon,
                    tint = colorResource(id = popScaleAnimationInfo.value.iconTintColorResId),
                    contentDescription = null,
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuestionDetail(
    mode: ProblemDetailMode,
    currentQuestion: Problem,
    selectedQuestionIndex: Int,
    onQuestionClick: (Int) -> Unit,
    onSelectAnswer: () -> Unit,
) {
    ElevatedCard(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)) {
        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp),
                text = buildAnnotatedString {
                    for (keyword in QuizUtil.highlightKeywords) {
                        if (currentQuestion.description.contains(keyword)) {
                            val start = currentQuestion.description.indexOf(keyword)
                            val end = start + keyword.length

                            append(currentQuestion.description.substring(0, start))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.daynight_gray900s),
                                    textDecoration = TextDecoration.Underline,
                                )
                            ) {
                                append(currentQuestion.description.substring(start, end))
                            }
                            append(currentQuestion.description.substring(end))
                            return@buildAnnotatedString
                        }
                    }

                    append(currentQuestion.description)
                },
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.daynight_gray800s)
            )

            if (currentQuestion.subDescriptions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                val elevation = 1.dp
                Column(
                    modifier = Modifier
                        .padding(all = elevation)
                        .shadow(elevation = elevation, shape = ShapeDefaults.Medium)
                        .background(color = colorScheme.surfaceColorAtElevation(5.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    currentQuestion.subDescriptions.forEach { subDescription ->
                        val (mark, description) = RegexUtils.getMarkedString(subDescription)
                        Row {
                            Text(
                                text = mark,
                                style = Typography.bodyMedium,
                                color = colorResource(id = R.color.daynight_gray600s)
                            )
                            Text(
                                text = description,
                                style = Typography.bodyMedium,
                                color = colorResource(id = R.color.daynight_gray600s)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                color = colorScheme.surfaceColorAtElevation(4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // questions
            currentQuestion.questions.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bounceClickable(
                            enabled = mode == ProblemDetailMode.Quiz,
                            dampingRatio = 0.95f,
                            useHapticFeedback = false,
                            onClick = {
                                when (mode) {
                                    ProblemDetailMode.Detail -> Unit
                                    ProblemDetailMode.Quiz -> onQuestionClick(index)
                                }
                            },
                            onLongClick = {
                                onQuestionClick(index)
                                onSelectAnswer()
                            },
                        )
                        .semantics {
                            this.selected = when (mode) {
                                ProblemDetailMode.Detail -> index == currentQuestion.answer
                                ProblemDetailMode.Quiz -> index == selectedQuestionIndex
                            }
                        }
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = when (mode) {
                            ProblemDetailMode.Detail -> index == currentQuestion.answer
                            ProblemDetailMode.Quiz -> index == selectedQuestionIndex
                        },
                        onClick = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier
                            .testTag("rb$index")
                            .semantics { testTagsAsResourceId = true },
                        text = s,
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.daynight_gray600s)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionDetailPreview() {
    QuestionDetail(
        mode = ProblemDetailMode.Detail,
        currentQuestion = Problem(
            year = 2022,
            pid = 15,
            type = QuizType.Business,
            description = "Sample description",
            subDescriptions = listOf("A. Hello", "B. World"),
            questions = listOf("Q1", "Q2", "Q3", "Q4", "Q5"),
            source = ProblemSource.CPA,
        ),
        selectedQuestionIndex = 1, onQuestionClick = {}, onSelectAnswer = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(
    isVisible: Boolean,
    total: Int,
    solved: Int,
    useTimer: Boolean,
    elapsedTime: Long,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            if (isVisible) {
                Column {
                    Text(
                        text = stringResource(id = R.string.quiz),
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.headlineSmall,
                    )
                    Text(
                        text = "$solved/$total",
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.bodyLarge,
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            Clock(useTimer, elapsedTime)
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun QuestionFloatingActionButton(
    isFabVisible: Boolean,
    fabPosition: Offset,
    quizDetailSize: IntSize,
    quizDetailPosition: Offset,
    onFabGloballyPositioned: (coordinates: LayoutCoordinates) -> Unit,
    onFabClick: () -> Unit,
) {
    val areFabAndQuizDetailScreenOverlapped =
        fabPosition.y.toInt() < quizDetailSize.height + quizDetailPosition.y.toInt()
    val fabElevation =
        if (areFabAndQuizDetailScreenOverlapped) {
            FloatingActionButtonDefaults.elevation(
                0.dp,
                0.dp,
                0.dp,
                0.dp
            )
        } else {
            FloatingActionButtonDefaults.elevation()
        }

    if (isFabVisible) {
        FloatingActionButton(
            modifier = Modifier
                .resourceTestTag("fab")
                .bounceClickable()
                .padding(16.dp)
                .onGloballyPositioned { coordinates ->
                    onFabGloballyPositioned(coordinates)
                }
                .modifyIf(areFabAndQuizDetailScreenOverlapped) {
                    alpha(0.25f)
                        .then(
                            border(
                                width = 1.dp,
                                color = colorScheme.primary,
                                shape = FloatingActionButtonDefaults.shape
                            )
                        )
                },
            onClick = onFabClick,
            elevation = fabElevation,
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Next")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true)
private fun QuestionTopAppBarPreview() {
    CpaQuizTheme {
        QuestionTopAppBar(
            isVisible = true,
            total = 10,
            solved = 8,
            useTimer = true,
            elapsedTime = 20000L,
            onBackClick = {},
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun QuestionSummaryHeader(
    windowSizeClass: WindowSizeClass? = null,
    title: String = "",
    numOfProblems: Int = 0,
    onHeaderClick: () -> Unit = {},
    onHeaderLongClick: () -> Unit = {},
) {
    val haptic = LocalHapticFeedback.current
    val useSplitScreen = if (windowSizeClass == null) false else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }

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
            .background(colorScheme.surfaceColorAtElevation(elevation = 3.dp))
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
                containerColor = colorScheme.primaryContainer
            ) {
                AnimatedContent(
                    targetState = numOfProblems,
                    transitionSpec = {
                        fadeIn() with fadeOut()
                    }
                ) { numOfProblems ->
                    Text(
                        text = numOfProblems.toString(),
                        color = colorScheme.primary
                    )
                }
            }
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun LazyItemScope.QuestionSummaryContent(
    problem: Problem,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass? = null,
    onProblemClick: ((Problem) -> Unit)? = null,
    onProblemLongClick: (() -> Unit)? = null,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog? = null,
    updateDeletingWrongProblemDialogOpened: ((DeleteWrongProblemDialog) -> Unit)? = null,
    deleteTargetWrongProblem: ((Problem) -> Unit)? = null,
    selectedQuestionInSplitScreen: Problem? = null,
) {
    val useSplitScreen = if (windowSizeClass == null) false else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }

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
        modifier = modifier
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
                background(color = colorScheme.surfaceColorAtElevation(0.5.dp))
                    .then(
                        border(
                            width = 1.dp,
                            color = colorScheme.primary,
                            shape = RectangleShape
                        )
                    )
            }
            .widthBySplit(useSplitScreen)
            .padding(bottom = 20.dp)
            .animateItemPlacement()
    ) {
        Box {
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp)
            ) {
                item {
                    val assistChipContainerColor =
                        colorResource(id = R.color.daynight_gray070s)

                    NotClickableAssistedChip(
                        modifier = Modifier.padding(all = 4.dp),
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = "${problem.source} ${problem.year}년 ${problem.pid}번")
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(containerColor = assistChipContainerColor),
                        border = null,
                    )
                }

                val containerColorResourceIdByType = chipContainerColorResIdByType(problem.type)
                item {
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
                        border = null,
                    )
                }

                item {
                    if (problem.subtype.isNotBlank()) {
                        NotClickableAssistedChip(
                            modifier = Modifier.padding(all = 4.dp),
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(text = problem.subtype)
                                }
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = containerColorResourceIdByType)
                            ),
                            border = null,
                        )
                    }
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
fun QuestionSummaryDivider(windowSizeClass: WindowSizeClass? = null) {
    val useSplitScreen = if (windowSizeClass == null) false else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }
    Divider(
        modifier = Modifier
            .widthBySplit(useSplitScreen)
            .padding(horizontal = 12.dp),
        color = colorScheme.surfaceColorAtElevation(1.dp)
    )
}
