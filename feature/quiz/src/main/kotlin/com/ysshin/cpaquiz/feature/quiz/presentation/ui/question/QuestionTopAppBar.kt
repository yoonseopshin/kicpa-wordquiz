package com.ysshin.cpaquiz.feature.quiz.presentation.ui.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTopAppBar(
    isVisible: Boolean,
    total: Int,
    solved: Int,
    useTimer: Boolean,
    elapsedTime: () -> Long,
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
                        style = Typography.titleLarge,
                    )
                    Text(
                        text = "$solved/$total",
                        modifier = Modifier.fillMaxWidth(),
                        style = Typography.titleSmall,
                    )
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
        scrollBehavior = scrollBehavior
    )
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
            elapsedTime = { 20000L },
            onBackClick = {},
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        )
    }
}
