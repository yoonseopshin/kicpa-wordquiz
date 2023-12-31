package com.ysshin.cpaquiz.core.android.ui.modifier

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalView

private enum class ButtonState {
    Pressed,
    Idle,
}

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bounceClickable(
    dampingRatio: Float = 0.85f,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onDoubleClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    useHapticFeedback: Boolean = true,
) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        targetValue = when (buttonState) {
            ButtonState.Pressed -> dampingRatio
            ButtonState.Idle -> 1f
        },
        label = "BounceScale",
    )
    val view = LocalView.current

    this
        .combinedClickable(
            enabled = enabled,
            onClick = {
                if (useHapticFeedback) view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                onClick()
            },
            onDoubleClick = onDoubleClick,
            onLongClick = onLongClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        )
        .graphicsLayer {
            this.shape = shape
            this.scaleX = scale
            this.scaleY = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = when (buttonState) {
                    ButtonState.Pressed -> {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    }

                    ButtonState.Idle -> {
                        awaitFirstDown(false)
                        ButtonState.Pressed
                    }
                }
            }
        }
}
