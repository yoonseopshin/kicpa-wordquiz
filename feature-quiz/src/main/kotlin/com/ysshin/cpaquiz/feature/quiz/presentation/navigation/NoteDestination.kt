package com.ysshin.cpaquiz.feature.quiz.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.NoteScreen
import com.ysshin.shared.navigation.CpaQuizNavigationDestination

object NoteDestination : CpaQuizNavigationDestination {
    override val route = "NoteRoute"
    override val destination = "NoteDestination"
}

fun NavGraphBuilder.noteGraph() {
    composable(NoteDestination.route) {
        NoteScreen()
    }
}
