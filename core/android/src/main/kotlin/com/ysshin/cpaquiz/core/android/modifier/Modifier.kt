package com.ysshin.cpaquiz.core.android.modifier

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.modifyIf(condition: Boolean, modify: @Composable Modifier.() -> Modifier) = composed {
    if (condition) modify() else this
}
