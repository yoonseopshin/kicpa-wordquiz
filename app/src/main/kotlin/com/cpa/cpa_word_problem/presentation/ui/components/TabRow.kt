package com.cpa.cpa_word_problem.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cpa.cpa_word_problem.presentation.MainScreen
import com.ysshin.cpaquiz.shared.base.Consumer

@Composable
fun MainTabRow(
    allScreens: List<MainScreen>,
    onTabSelected: Consumer<MainScreen>,
    currentScreen: MainScreen,
) {
    Surface(
        Modifier
            .height(56.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.selectableGroup()) {
            allScreens.forEach { screen ->
                MainTab(
                    text = screen.name,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun MainTab(
    text: String,
    onSelected: () -> Unit,
    selected: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .height(56.dp)
            .selectable(
                selected = selected,
                onClick = onSelected
            )
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text)
    }
}
