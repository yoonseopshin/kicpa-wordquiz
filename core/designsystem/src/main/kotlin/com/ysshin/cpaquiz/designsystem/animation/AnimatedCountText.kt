package com.ysshin.cpaquiz.designsystem.animation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun AnimatedCountText(count: Int, modifier: Modifier = Modifier, style: TextStyle = LocalTextStyle.current) {
    var oldCount by remember { mutableIntStateOf(count) }
    SideEffect {
        oldCount = count
    }

    Row(modifier = modifier) {
        val countString = count.toString()
        val oldCountString = oldCount.toString()

        val slideInDirection = if (count > oldCount) 1 else -1
        val slideOutDirection = -slideInDirection

        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) oldChar else newChar

            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { slideInDirection * it } togetherWith
                        slideOutVertically { slideOutDirection * it }
                },
                label = "Content Slider Animation",
            ) {
                Text(text = it.toString(), style = style)
            }
        }
    }
}
