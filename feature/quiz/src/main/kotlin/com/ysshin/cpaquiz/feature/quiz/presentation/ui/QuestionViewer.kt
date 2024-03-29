package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.designsystem.animation.AnimatedCountText
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionPagerUiState
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionViewerViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionDetail
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuestionViewerScreen(
    modifier: Modifier = Modifier,
    viewModel: QuestionViewerViewModel = hiltViewModel(),
) {
    val questionPagerUiState = viewModel.questionPagerUiState.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val activity = context.findActivity()
    val questionContentScrollState = rememberScrollState()
    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    when (questionPagerUiState) {
        QuestionPagerUiState.Loading -> Unit
        is QuestionPagerUiState.Success -> {
            val pagerState = rememberPagerState(
                initialPage = questionPagerUiState.currentPage,
                initialPageOffsetFraction = 0f,
                pageCount = { questionPagerUiState.pageCount },
            )
            var currentPage by remember { mutableIntStateOf(questionPagerUiState.currentPage) }

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collect { page ->
                    currentPage = page
                }
            }

            CpaQuizTheme {
                Scaffold(
                    modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                    topBar = {
                        TopAppBar(
                            title = {
                                QuestionTopAppBar(
                                    currentPage,
                                    questionPagerUiState.totalQuestions.size,
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = activity::finish) {
                                    CpaIcon(icon = CpaIcons.ArrowBack)
                                }
                            },
                            scrollBehavior = topAppBarScrollBehavior,
                        )
                    },
                ) { contentPadding ->
                    HorizontalQuestionPager(
                        modifier = Modifier
                            .padding(contentPadding)
                            .fillMaxSize(),
                        questionPagerUiState = questionPagerUiState,
                        scrollState = questionContentScrollState,
                        pagerState = pagerState,
                    )
                }
            }
        }
    }
}

@Composable
fun QuestionTopAppBar(currentPage: Int, totalPage: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        AnimatedCountText(count = currentPage + 1)
        Text(text = "/$totalPage")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalQuestionPager(
    questionPagerUiState: QuestionPagerUiState.Success,
    pagerState: PagerState,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        verticalAlignment = Alignment.Top,
    ) { page ->
        Column(
            Modifier.verticalScroll(scrollState),
        ) {
            val question = questionPagerUiState.getQuestion(page)
            QuestionDetail(
                currentQuestion = question,
                questionClickable = false,
                isSelectedQuestion = { position -> position == question.answer },
            )
        }
    }
}
