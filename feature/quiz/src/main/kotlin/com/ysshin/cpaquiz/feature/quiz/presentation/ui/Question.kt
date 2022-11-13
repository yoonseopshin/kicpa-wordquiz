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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.ui.animation.ClockTickingAnimation
import com.ysshin.cpaquiz.core.android.ui.animation.PopScaleAnimation
import com.ysshin.cpaquiz.core.android.ui.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.ui.theme.Typography
import com.ysshin.cpaquiz.core.android.util.RegexUtils
import com.ysshin.cpaquiz.core.android.util.TimeFormatter
import com.ysshin.cpaquiz.core.android.util.chipBorderColorResIdByType
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(viewModel: QuestionViewModel = hiltViewModel()) {
    val useTimer = viewModel.useTimer.collectAsStateWithLifecycle()
    val numOfSolvedQuestion = viewModel.numOfSolvedQuestions.collectAsStateWithLifecycle()
    val numOfTotalQuestion = viewModel.numOfTotalQuestions.collectAsStateWithLifecycle()
    val currentQuestion = viewModel.currentQuestion.collectAsStateWithLifecycle()
    val elapsedTime = viewModel.elapsedTime.collectAsStateWithLifecycle()
    val isToolbarTitleVisible = viewModel.isToolbarTitleVisible.collectAsStateWithLifecycle()
    val isFabVisible = viewModel.isFabVisible.collectAsStateWithLifecycle()
    val mode = viewModel.mode.collectAsStateWithLifecycle()

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

    LaunchedEffect(snackbarHostState) {
        // Handle UI event
        viewModel.uiEvent.collect { event ->
            when (event) {
                is QuestionViewModel.UiEvent.NavigateToQuizResult -> {
                    Timber.d("Navigate to quiz result screen")
                    val quizEndNavActions =
                        (appContext as QuizEndNavigationActionsProvider).quizEndNavActions
                    quizEndNavActions.onQuizEnd(
                        activity = activity,
                        problems = viewModel.questions,
                        selected = viewModel.selected,
                        timesPerProblem = viewModel.timesPerProblem,
                    )
                }
                is QuestionViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        actionLabel = event.actionLabel.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }
                is QuestionViewModel.UiEvent.ScrollToTop -> {
                    scrollState.animateScrollTo(value = 0)
                }
            }
        }
    }

    val selectedQuestionIndex = viewModel.selectedQuestionIndex.collectAsStateWithLifecycle()
    val isPopScaleAnimationShowing = viewModel.isAnimationShowing.collectAsStateWithLifecycle()
    val popScaleAnimationInfo = viewModel.animationInfo.collectAsStateWithLifecycle()

    CpaQuizTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                QuestionTopAppBar(
                    isToolbarTitleVisible.value,
                    numOfTotalQuestion.value,
                    numOfSolvedQuestion.value
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Column {
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
                                modifier = Modifier.padding(all = 4.dp),
                                label = {
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
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(contentAlignment = Alignment.BottomStart) {
                        if (useTimer.value) {
                            ClockTickingAnimation(
                                modifier = Modifier.padding(16.dp),
                                timeMillis = elapsedTime.value,
                                clockSize = 64.dp,
                                clockEndOffset = 12.dp,
                                clockBackgroundColor = colorScheme.surfaceColorAtElevation(elevation = 2.dp),
                                clockHandColor = colorScheme.primary.copy(alpha = 0.2f),
                                clockHandStroke = 3.dp,
                            ) {
                                Text(
                                    text = TimeFormatter.format(elapsedTime.value),
                                    style = Typography.headlineSmall,
                                    color = colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(weight = 1f, fill = true))

                    if (isFabVisible.value) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            FloatingActionButton(
                                modifier = Modifier
                                    .bounceClickable()
                                    .padding(16.dp),
                                onClick = viewModel::selectAnswer,
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Next")
                            }
                        }
                    }
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
) {
    ElevatedCard(modifier = Modifier.padding(horizontal = 12.dp)) {
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
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // questions
            currentQuestion.questions.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bounceClickable(dampingRatio = 0.95f, useHapticFeedback = false)
                        .selectable(
                            selected = when (mode) {
                                ProblemDetailMode.Detail -> index == currentQuestion.answer
                                ProblemDetailMode.Quiz -> index == selectedQuestionIndex
                            },
                            onClick = {
                                when (mode) {
                                    ProblemDetailMode.Detail -> Unit
                                    ProblemDetailMode.Quiz -> onQuestionClick(index)
                                }
                            }
                        )
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = when (mode) {
                            ProblemDetailMode.Detail -> index == currentQuestion.answer
                            ProblemDetailMode.Quiz -> index == selectedQuestionIndex
                        },
                        onClick = null
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(isVisible: Boolean, total: Int, solved: Int) {
    val activity = LocalContext.current.findActivity()

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
            IconButton(onClick = activity::finish) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
    )
}
