package com.ysshin.cpaquiz.designsystem.animation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.designsystem.component.ThemePreviews
import com.ysshin.cpaquiz.designsystem.theme.CpaQuizTheme
import com.ysshin.cpaquiz.designsystem.theme.shimmerColorShades


@Composable
fun ShimmerAnimation(
    modifier: Modifier,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val density = LocalDensity.current.density

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = screenWidthDp * density,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = MaterialTheme.colorScheme.shimmerColorShades,
        start = Offset(0f, 0f),
        end = Offset(translateAnim, translateAnim)
    )

    Box(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(brush = brush)
        )
        content()
    }
}


@ThemePreviews
@Composable
fun ShimmerAnimationPreview() {
    CpaQuizTheme {
        ShimmerAnimation(modifier = Modifier.size(400.dp))
    }
}
