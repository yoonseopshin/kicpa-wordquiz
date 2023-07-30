package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.play.core.review.ReviewManagerFactory
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.android.ui.ad.NativeMediumAd
import com.ysshin.cpaquiz.core.android.util.TimeFormatter
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toModel
import com.ysshin.cpaquiz.feature.quiz.presentation.model.ProblemModel
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionViewerActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult.QuizResultUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quizresult.QuizResultViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionSummaryContent
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionSummaryDivider
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionSummaryHeader
import kotlin.math.roundToInt

@Composable
fun QuizResultRoute(
    viewModel: QuizResultViewModel = hiltViewModel(),
    showInterstitialAd: () -> Unit,
) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val quizResultUiState = viewModel.quizResultUiState.collectAsStateWithLifecycle()
    val onProblemClick: (Problem) -> Unit = { problem ->
        context.startActivity(
            QuestionViewerActivity.newIntent(
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
        showInterstitialAd = showInterstitialAd,
    )
}

@Composable
fun QuizResultScreen(
    quizResultUiState: QuizResultUiState,
    onConfirmClick: () -> Unit = {},
    onProblemClick: (Problem) -> Unit = {},
    showInterstitialAd: () -> Unit = {},
) {
    val context = LocalContext.current
    val reviewManager = remember {
        ReviewManagerFactory.create(context)
    }

    LaunchedEffect(quizResultUiState) {
        if (quizResultUiState is QuizResultUiState.QuizResult) {
            if (quizResultUiState.shouldShowInterstitialAd) {
                showInterstitialAd()
            }
            if (quizResultUiState.shouldRequestInAppReview) {
                reviewManager.requestReviewFlow()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            reviewManager.launchReviewFlow(context as Activity, it.result)
                        }
                    }
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
            QuizResultUiState.Loading -> {}

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

                    item {
                        val total = solvedQuestions.size
                        val solved = solvedQuestions.filterIndexed { index, question ->
                            question.answer == quizResultUiState.selectedIndices[index]
                        }.size
                        val elapsedTime = quizResultUiState.totalElapsedTime

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .padding(vertical = 20.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(id = R.string.report_elapsed_time_per_question_1))
                                    append(" ")
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    ) {
                                        append(TimeFormatter.format((1f * elapsedTime / total).roundToInt()))
                                    }
                                    append(stringResource(id = R.string.report_elapsed_time_per_question_2))
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    ) {
                                        append("$total")
                                    }
                                    append(stringResource(id = R.string.report_total_solved_question_1))
                                    append(" ")
                                    withStyle(
                                        style = SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                        )
                                    ) {
                                        append("$solved")
                                    }
                                    append(stringResource(id = R.string.report_total_solved_question_2))
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

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

                        if (index < solvedQuestions.lastIndex) {
                            QuestionSummaryDivider()
                        }
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
                type = QuizType.CommercialLaw,
                source = ProblemSource.CPA,
                subtype = "상행위"
            ),
            ProblemModel(
                year = 2022,
                pid = 22,
                answer = 1,
                description = "문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. ",
                type = QuizType.CommercialLaw,
                source = ProblemSource.CPA,
                subtype = "어음수표법"
            ),
            ProblemModel(
                year = 2020,
                pid = 31,
                answer = 1,
                description = "문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. 문제 설명입니다. ",
                type = QuizType.CommercialLaw,
                source = ProblemSource.CPA,
                subtype = "회사법"
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
