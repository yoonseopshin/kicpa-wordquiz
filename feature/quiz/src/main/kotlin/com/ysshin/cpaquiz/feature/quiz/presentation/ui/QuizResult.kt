package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.ui.ad.NativeMediumAd
import com.ysshin.cpaquiz.core.android.ui.theme.CpaQuizTheme
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics.QuizResultUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.statistics.QuizResultViewModel
import timber.log.Timber

@Composable
fun QuizResultRoute(
    viewModel: QuizResultViewModel = hiltViewModel(),
    requestInAppReview: () -> Unit,
    showInterstitialAd: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val quizResultUiState = viewModel.quizResultUiState.collectAsStateWithLifecycle()
    val onProblemClick: (Problem) -> Unit = { problem ->
        context.startActivity(
            QuestionActivity.newIntent(
                context = context,
                mode = ProblemDetailMode.Detail,
                problemModel = problem.toModel()
            )
        )
    }

    QuizResultScreen(
        quizResultUiState = quizResultUiState.value,
        onConfirmClick = activity::finish,
        onProblemClick = onProblemClick,
        requestInAppReview = requestInAppReview,
        showInterstitialAd = showInterstitialAd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    quizResultUiState: QuizResultUiState,
    onConfirmClick: () -> Unit = {},
    onProblemClick: (Problem) -> Unit = {},
    requestInAppReview: () -> Unit = {},
    showInterstitialAd: () -> Unit = {},
) {
    LaunchedEffect(quizResultUiState) {
        if (quizResultUiState is QuizResultUiState.QuizResult) {
            if (quizResultUiState.shouldShowInterstitialAd) {
                showInterstitialAd()
                Timber.d("Show interstitial ad")
            }
            if (quizResultUiState.shouldRequestInAppReview) {
                requestInAppReview()
                Timber.d("Request in-app review")
            }
        }
    }

    Scaffold(
        topBar = {
            QuizResultTopAppBar(
                quizResultUiState = quizResultUiState,
                onConfirmClick = onConfirmClick
            )
        }
    ) { padding ->
        when (quizResultUiState) {
            QuizResultUiState.Loading -> {
                // TODO: Loading screen
            }
            is QuizResultUiState.QuizResult -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    item {
                        NativeMediumAd()
                    }

                    val solvedQuestions = quizResultUiState.solvedQuestions

                    itemHeader(shouldShowListHeaderAsSticky = true) {
                        QuestionSummaryHeader(
                            title = stringResource(id = R.string.solved_problems),
                            numOfProblems = solvedQuestions.size
                        )
                    }

                    itemsIndexed(items = solvedQuestions) { index, problemModel ->
                        QuestionSummaryContent(
                            problem = problemModel.toDomain(),
                            modifier = if (quizResultUiState.selectedIndices[index] == problemModel.answer) {
                                Modifier.background(color = colorResource(id = R.color.color_on_correct_bg))
                            } else {
                                Modifier.background(color = colorResource(id = R.color.color_on_incorrect_bg))
                            },
                            onProblemClick = onProblemClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuizResultScreenPreview() {
    val quizResultUiState = QuizResultUiState.QuizResult(
        totalElapsedTime = 44000,
        shouldRequestInAppReview = false,
        shouldShowInterstitialAd = false,
        selectedIndices = listOf(1, 2, 3),
        solvedQuestions = listOf(
            ProblemModel(
                year = 2021,
                pid = 14,
                answer = 1,
                description = "문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. ",
                type = QuizType.Accounting,
                source = ProblemSource.CPA,
            ),
            ProblemModel(
                year = 2022,
                pid = 22,
                answer = 1,
                description = "문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. ",
                type = QuizType.Accounting,
                source = ProblemSource.CPA
            ),
            ProblemModel(
                year = 2020,
                pid = 31,
                answer = 1,
                description = "문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. ",
                type = QuizType.Accounting,
                source = ProblemSource.CPA
            ),
        )
    )
    CpaQuizTheme {
        QuizResultScreen(quizResultUiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuizResultTopAppBar(quizResultUiState: QuizResultUiState, onConfirmClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.result),
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
            if (quizResultUiState is QuizResultUiState.QuizResult) {
                Clock(useTimer = true, elapsedTime = quizResultUiState.totalElapsedTime)
            }
            IconButton(onClick = onConfirmClick) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
    )
}
