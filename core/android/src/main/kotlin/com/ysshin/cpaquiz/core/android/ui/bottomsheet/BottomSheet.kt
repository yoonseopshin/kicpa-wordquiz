package com.ysshin.cpaquiz.core.android.ui.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.core.android.R

@Composable
fun BottomSheetHandle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.daynight_gray050s))
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.size(width = 80.dp, height = 4.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.daynight_gray900s))
        ) {}
    }
}
