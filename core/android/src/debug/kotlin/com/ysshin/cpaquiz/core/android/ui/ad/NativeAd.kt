package com.ysshin.cpaquiz.core.android.ui.ad

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.designsystem.animation.ShimmerAnimation

@Composable
fun NativeMediumAd() {
    ShimmerAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 300.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.Center),
            text = "NativeMedium Area",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun NativeMediumAdPreview() {
    NativeMediumAd()
}

@Composable
fun NativeSmallAd() {
    ShimmerAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp),
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.Center),
            text = "NativeSmallAd Area",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview
@Composable
private fun NativeSmallAdPreview() {
    NativeSmallAd()
}
