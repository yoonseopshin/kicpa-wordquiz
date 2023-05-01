package com.ysshin.cpaquiz.feature.quiz.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.core.android.ui.animation.ClockTickingAnimation
import com.ysshin.cpaquiz.core.android.util.TimeFormatter
import com.ysshin.cpaquiz.designsystem.theme.Typography

@Preview
@Composable
private fun ClockPreview() {
    Clock(useTimer = true, elapsedTime = 15000L)
}

@Composable
fun Clock(useTimer: Boolean, elapsedTime: Long) {
    Box(contentAlignment = Alignment.BottomStart) {
        if (useTimer) {
            ClockTickingAnimation(
                modifier = Modifier.padding(16.dp),
                timeMillis = elapsedTime,
                clockSize = 52.dp,
                clockEndOffset = 8.dp,
                clockBackgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 2.dp),
                clockHandColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                clockHandStroke = 3.dp,
            ) {
                Text(
                    text = TimeFormatter.format(elapsedTime),
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
