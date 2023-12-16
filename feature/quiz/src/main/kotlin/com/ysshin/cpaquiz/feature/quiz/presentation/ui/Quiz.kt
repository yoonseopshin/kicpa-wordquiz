package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.modifier.modifyIf
import com.ysshin.cpaquiz.core.android.modifier.resourceTestTag
import com.ysshin.cpaquiz.core.android.ui.animation.PopScaleAnimation
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.designsystem.animation.AnimatedCountText
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.navigation.QuizEndNavigationActionsProvider
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuizState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuizViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.SnackbarState
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionDetail
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(viewModel: QuizViewModel = hiltViewModel()) {
    // State
    val numOfSolvedQuestion = viewModel.numOfSolvedQuestions.collectAsStateWithLifecycle()
    val numOfTotalQuestion = viewModel.numOfTotalQuestions.collectAsStateWithLifecycle()
    val useTimer = viewModel.useTimer.collectAsStateWithLifecycle()
    val elapsedTime = viewModel.elapsedTime.collectAsStateWithLifecycle()
    val currentQuestion = viewModel.currentQuestion.collectAsStateWithLifecycle()

    val selectedQuestionIndex = viewModel.selectedQuestionIndex.collectAsStateWithLifecycle()
    val isPopScaleAnimationShowing = viewModel.isAnimationShowing.collectAsStateWithLifecycle()
    val popScaleAnimationInfo = viewModel.animationInfo.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val appContext = context.applicationContext
    val activity = context.findActivity()
    val quizState = viewModel.quizState.collectAsStateWithLifecycle()
    val snackbarState = viewModel.snackbarState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val questionContentScrollState = rememberScrollState()
    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    var fabPosition by remember { mutableStateOf(Offset.Zero) }
    var quizDetailSize by remember { mutableStateOf(IntSize.Zero) }
    var quizDetailPosition by remember { mutableStateOf(Offset.Zero) }
    val areFabAndQuizDetailScreenOverlapped by remember {
        derivedStateOf {
            fabPosition.y.toInt() < quizDetailSize.height + quizDetailPosition.y.toInt()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    ObserveLifecycle(lifecycleOwner = lifecycleOwner) { event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> viewModel.onPause()
            Lifecycle.Event.ON_RESUME -> viewModel.onResume()
            else -> {}
        }
    }

    LaunchedEffect(snackbarState.value) {
        when (val state = snackbarState.value) {
            is SnackbarState.Show -> {
                snackbarHostState.showSnackbar(
                    message = state.message.asString(context),
                    actionLabel = state.actionLabel.asString(context).takeUnless(String::isBlank),
                    withDismissAction = true,
                    duration = SnackbarDuration.Short,
                )
            }

            SnackbarState.Hide -> Unit
        }
    }

    LaunchedEffect(quizState.value) {
        when (quizState.value) {
            is QuizState.Solving -> {
                topAppBarState.heightOffset = 0f
                topAppBarState.contentOffset = 0f
                questionContentScrollState.animateScrollTo(0)
            }

            QuizState.Grading -> Unit
            QuizState.End -> {
                val quizEndNavActions =
                    (appContext as QuizEndNavigationActionsProvider).quizEndNavActions
                quizEndNavActions.onQuizEnd(
                    activity = activity,
                    problems = viewModel.questions,
                    selected = viewModel.selected,
                    timesPerQuestion = viewModel.timesPerQuestion,
                )
            }
        }
    }

    CpaQuizTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                QuizTopAppBar(
                    numOfSolvedQuestion = numOfSolvedQuestion.value,
                    numOfTotalQuestion = numOfTotalQuestion.value,
                    useTimer = useTimer.value,
                    elapsedTime = { elapsedTime.value },
                    onBackClick = activity::finish,
                    scrollBehavior = topAppBarScrollBehavior,
                )
            },
            floatingActionButton = {
                QuizFloatingActionButton(
                    areFabAndQuizDetailScreenOverlapped = areFabAndQuizDetailScreenOverlapped,
                    onFabGloballyPositioned = { coordinates ->
                        val newPositionInWindow = coordinates.positionInWindow()
                        if (fabPosition != newPositionInWindow) {
                            Timber.d("fabPosition updated")
                            fabPosition = newPositionInWindow
                        }
                    },
                    onFabClick = viewModel::selectAnswer,
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(questionContentScrollState)
                    .padding(bottom = 8.dp),
            ) {
                Column(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        if (quizDetailSize != coordinates.size) {
                            Timber.d("quizDetailSize updated")
                            quizDetailSize = coordinates.size
                        }
                        if (quizDetailPosition != coordinates.positionInWindow()) {
                            Timber.d("quizDetailPosition updated")
                            quizDetailPosition = coordinates.positionInWindow()
                        }
                    },
                ) {
                    QuestionDetail(
                        currentQuestion = currentQuestion.value,
                        onQuestionClick = viewModel::selectQuestion,
                        onSelectAnswer = viewModel::selectAnswer,
                        isSelectedQuestion = { position -> position == selectedQuestionIndex.value },
                    )
                }
            }

            PopScaleAnimation(
                isVisible = isPopScaleAnimationShowing.value,
                circleColor = colorResource(id = popScaleAnimationInfo.value.backgroundColorResId),
                radius = 360f,
            ) {
                CpaIcon(
                    icon = CpaIcon.ImageVectorIcon(popScaleAnimationInfo.value.icon),
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                    tint = colorResource(id = popScaleAnimationInfo.value.iconTintColorResId),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizTopAppBar(
    numOfSolvedQuestion: Int,
    numOfTotalQuestion: Int,
    useTimer: Boolean,
    elapsedTime: () -> Long,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.quiz),
                    modifier = Modifier.fillMaxWidth(),
                    style = Typography.titleLarge,
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    ProvideTextStyle(Typography.titleSmall) {
                        AnimatedCountText(count = numOfSolvedQuestion)
                        Text(text = "/$numOfTotalQuestion")
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                CpaIcon(icon = CpaIcons.ArrowBack)
            }
        },
        actions = {
            Clock(useTimer, elapsedTime())
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
fun ObserveLifecycle(lifecycleOwner: LifecycleOwner, onEvent: (event: Lifecycle.Event) -> Unit) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            onEvent(event)
        }

        val lifecycle = lifecycleOwner.lifecycle

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun QuizFloatingActionButton(
    areFabAndQuizDetailScreenOverlapped: Boolean,
    onFabGloballyPositioned: (coordinates: LayoutCoordinates) -> Unit,
    onFabClick: () -> Unit,
) {
    val fabElevation =
        if (areFabAndQuizDetailScreenOverlapped) {
            FloatingActionButtonDefaults.elevation(
                0.dp,
                0.dp,
                0.dp,
                0.dp,
            )
        } else {
            FloatingActionButtonDefaults.elevation()
        }

    FloatingActionButton(
        modifier = Modifier
            .resourceTestTag("fab")
            .bounceClickable(useHapticFeedback = false)
            .padding(16.dp)
            .onGloballyPositioned(onFabGloballyPositioned)
            .modifyIf(areFabAndQuizDetailScreenOverlapped) {
                alpha(0.25f)
                    .then(
                        border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = FloatingActionButtonDefaults.shape,
                        ),
                    )
            },
        onClick = onFabClick,
        elevation = fabElevation,
    ) {
        CpaIcon(icon = CpaIcons.Check)
    }
}
