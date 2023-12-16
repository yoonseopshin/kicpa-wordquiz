package com.ysshin.cpaquiz.presentation.navigation

import com.ysshin.core.navigation.CpaQuizNavigationDestination
import com.ysshin.cpaquiz.designsystem.icon.CpaIcon

data class TopLevelDestination(
    override val route: String,
    override val destination: String,
    val selectedIcon: CpaIcon,
    val unselectedIcon: CpaIcon,
    val iconTextResourceId: Int,
) : CpaQuizNavigationDestination
