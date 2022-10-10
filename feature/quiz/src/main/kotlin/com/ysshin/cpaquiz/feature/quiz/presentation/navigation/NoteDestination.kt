package com.ysshin.cpaquiz.feature.quiz.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.NoteScreen
import com.ysshin.core.navigation.CpaQuizNavigationDestination

object NoteDestination : CpaQuizNavigationDestination {
    override val route = "NoteRoute"
    override val destination = "NoteDestination"
}

fun NavGraphBuilder.noteGraph(windowSizeClass: WindowSizeClass) {
    composable(NoteDestination.route) {
        NoteScreen(windowSizeClass)
    }
}
