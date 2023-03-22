package com.ysshin.cpaquiz.core.android.ui.ad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NativeMediumAd() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.Center),
            text = "NativeMedium Area",
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
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.Center),
            text = "NativeSmallAd Area",
        )
    }
}

@Preview
@Composable
private fun NativeSmallAdPreview() {
    NativeSmallAd()
}
