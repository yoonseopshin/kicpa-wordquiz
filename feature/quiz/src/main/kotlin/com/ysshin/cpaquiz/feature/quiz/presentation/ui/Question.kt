package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.modifier.modifyIf
import com.ysshin.cpaquiz.core.android.ui.animation.PopScaleAnimation
import com.ysshin.cpaquiz.core.android.ui.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.RegexUtils
import com.ysshin.cpaquiz.core.android.util.chipBorderColorResIdByType
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
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
    val scrollState = rememberScrollState()

    LaunchedEffect(quizState.value) {
        when (quizState.value) {
            is QuizState.Solving -> scrollState.animateScrollTo(0)
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

    var fabSize by remember { mutableStateOf(IntSize.Zero) }
    var fabPosition by remember { mutableStateOf(Offset.Zero) }
    var quizDetailSize by remember { mutableStateOf(IntSize.Zero) }
    var quizDetailPosition by remember { mutableStateOf(Offset.Zero) }

    CpaQuizTheme {
        Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, topBar = {
            QuestionTopAppBar(
                isToolbarTitleVisible.value,
                numOfTotalQuestion.value,
                numOfSolvedQuestion.value,
                useTimer.value,
                elapsedTime.value,
                activity::finish
            )
        }, floatingActionButton = {
            val isFabVisible = viewModel.isFabVisible.collectAsStateWithLifecycle()
            val areFabAndQuizDetailScreenOverlapped =
                fabPosition.y.toInt() < quizDetailSize.height + quizDetailPosition.y.toInt()
            val fabElevation =
                if (areFabAndQuizDetailScreenOverlapped) FloatingActionButtonDefaults.elevation(
                    0.dp, 0.dp, 0.dp, 0.dp
                ) else FloatingActionButtonDefaults.elevation()

            if (isFabVisible.value) {
                FloatingActionButton(
                    modifier = Modifier
                        .bounceClickable()
                        .padding(16.dp)
                        .onGloballyPositioned { coordinates ->
                            fabSize = coordinates.size
                            fabPosition = coordinates.positionInWindow()
                        }
                        .modifyIf(areFabAndQuizDetailScreenOverlapped) {
                            alpha(0.25f)
                        },
                    onClick = viewModel::selectAnswer,
                    elevation = fabElevation,
                ) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Next")
                }
            }
        }) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(bottom = 8.dp)
            ) {
                Column(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        quizDetailSize = coordinates.size
                        quizDetailPosition = coordinates.positionInWindow()
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 8.dp)
                        ) {
                            val assistChipContainerColor = colorResource(id = R.color.daynight_gray070s)
                            val assistChipBorderColor = colorResource(id = R.color.daynight_gray300s)

                            NotClickableAssistedChip(
                                modifier = Modifier.padding(all = 4.dp),
                                label = {
                                    ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                        Text(text = "${currentQuestion.value.year}년 ${currentQuestion.value.pid}번")
                                    }
                                },
                                colors = AssistChipDefaults.assistChipColors(containerColor = assistChipContainerColor),
                                border = AssistChipDefaults.assistChipBorder(
                                    borderColor = assistChipBorderColor,
                                    borderWidth = 0.5.dp,
                                )
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(end = 8.dp)
                        ) {
                            val containerColorResourceIdByType =
                                chipContainerColorResIdByType(currentQuestion.value.type)
                            val borderColorResourceIdByType =
                                chipBorderColorResIdByType(currentQuestion.value.type)

                            NotClickableAssistedChip(
                                modifier = Modifier.padding(all = 4.dp), label = {
                                    ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                        Text(text = currentQuestion.value.type.toKorean())
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

                    QuestionDetail(
                        mode = mode.value,
                        currentQuestion = currentQuestion.value,
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
            description = "Sample description",
            subDescriptions = listOf("A. Hello", "B. World"),
            questions = listOf("Q1", "Q2", "Q3", "Q4", "Q5")
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
) {
    TopAppBar(title = {
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
    }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }, actions = {
        Clock(useTimer, elapsedTime)
    })
}

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
            onBackClick = {}
        )
    }
}
