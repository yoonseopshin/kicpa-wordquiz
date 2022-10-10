package com.cpa.cpa_word_problem.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.ysshin.core.navigation.CpaQuizNavigationDestination

data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextResourceId: Int
) : CpaQuizNavigationDestination
