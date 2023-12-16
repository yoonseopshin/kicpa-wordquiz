package com.ysshin.cpaquiz.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipBorder
import androidx.compose.material3.ChipColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun NotClickableAssistedChip(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    colors: ChipColors = AssistChipDefaults.assistChipColors(),
    border: ChipBorder? = AssistChipDefaults.assistChipBorder(),
) {
    Box {
        AssistChip(
            modifier = modifier,
            onClick = {},
            label = label,
            colors = colors,
            border = border,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = {}),
        )
    }
}
