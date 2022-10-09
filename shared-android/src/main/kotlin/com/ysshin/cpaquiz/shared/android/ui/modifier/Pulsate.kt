package com.ysshin.cpaquiz.shared.android.ui.modifier

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
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
import com.ysshin.cpaquiz.shared.base.Action

private enum class ButtonState {
    Pressed, Idle;
}

fun Modifier.bounceClickable(
    dampingRatio: Float = 0.85f,
    enabled: Boolean = true,
    onClick: Action = {},
) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(when (buttonState) {
        ButtonState.Pressed -> dampingRatio
        ButtonState.Idle -> 1f
    })
    val view = LocalView.current

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            enabled = enabled,
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
        )
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = when (buttonState) {
                    ButtonState.Pressed -> {
                        waitForUpOrCancellation()
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
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
