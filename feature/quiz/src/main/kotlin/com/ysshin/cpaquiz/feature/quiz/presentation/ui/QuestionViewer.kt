package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ysshin.cpaquiz.core.android.util.findActivity
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.QuestionViewerViewModel
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.question.QuestionDetail

// TODO: Hoist to QuestionRoute
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuestionViewerScreen(viewModel: QuestionViewerViewModel = hiltViewModel()) {
    val currentQuestion = viewModel.currentQuestion.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context.findActivity()
    val questionContentScrollState = rememberScrollState()
    val topAppBarState = rememberTopAppBarState()
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(topAppBarState)

    CpaQuizTheme {
        Scaffold(
            modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = activity::finish) {
                            CpaIcon(icon = CpaIcons.ArrowBack)
                        }
                    },
                    scrollBehavior = topAppBarScrollBehavior
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(questionContentScrollState)
                    .padding(bottom = 8.dp)
            ) {
                QuestionDetail(
                    currentQuestion = currentQuestion.value,
                    questionClickable = false,
                    isSelectedQuestion = { position -> position == currentQuestion.value.answer }
                )
            }
        }
    }
}
