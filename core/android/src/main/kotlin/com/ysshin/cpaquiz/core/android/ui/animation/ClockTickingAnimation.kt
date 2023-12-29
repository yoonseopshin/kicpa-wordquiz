package com.ysshin.cpaquiz.core.android.ui.animation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ClockTickingAnimation(
    timeMillis: Long,
    clockSize: Dp,
    clockEndOffset: Dp,
    clockBackgroundColor: Color,
    clockHandColor: Color,
    clockHandStroke: Dp,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val rotationPerSecond = 360f
    val secondProgress by updateTransition(
        targetState = timeMillis.toFloat() / 1_000 / 60,
        label = "ClockTickingTransition",
    ).animateFloat(label = "ClockTickingRotation") { it }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .size(clockSize)
                .shadow(elevation = 6.dp, shape = CircleShape)
                .background(color = clockBackgroundColor, shape = CircleShape),
        ) {
            val middle = Offset(size.minDimension / 2, size.minDimension / 2)

            withTransform(
                transformBlock = {
                    rotate(rotationPerSecond * secondProgress, middle)
                },
                drawBlock = {
                    drawLine(
                        strokeWidth = clockHandStroke.toPx(),
                        cap = StrokeCap.Round,
                        color = clockHandColor,
                        start = middle,
                        end = Offset(
                            x = size.minDimension / 2,
                            y = clockEndOffset.toPx(),
                        ),
                    )
                },
            )
        }

        content()
    }
}
