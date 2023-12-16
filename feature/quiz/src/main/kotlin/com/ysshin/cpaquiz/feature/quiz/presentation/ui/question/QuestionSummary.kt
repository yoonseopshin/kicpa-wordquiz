package com.ysshin.cpaquiz.feature.quiz.presentation.ui.question

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ysshin.cpaquiz.core.android.modifier.modifyIf
import com.ysshin.cpaquiz.core.android.ui.dialog.AppInfoDialog
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.designsystem.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.designsystem.icon.CpaIcons
import com.ysshin.cpaquiz.designsystem.theme.DayNightGray070S
import com.ysshin.cpaquiz.designsystem.theme.DayNightGray900S
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.mapper.toDomain
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.main.DeleteWrongProblemDialog
import com.ysshin.cpaquiz.feature.quiz.presentation.ui.widthBySplit
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun QuestionSummaryHeader(
    windowSizeClass: WindowSizeClass? = null,
    title: String = "",
    numOfProblems: Int = 0,
    onHeaderClick: () -> Unit = {},
    onHeaderLongClick: () -> Unit = {},
) {
    val haptic = LocalHapticFeedback.current
    val useSplitScreen = if (windowSizeClass == null) {
        false
    } else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .combinedClickable(
                onClick = onHeaderClick,
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onHeaderLongClick()
                },
            )
            .widthBySplit(useSplitScreen)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = 3.dp))
            .defaultMinSize(minHeight = 52.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Badge(
                modifier = Modifier.animateContentSize(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ) {
                AnimatedContent(
                    targetState = numOfProblems,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "",
                ) { numOfProblems ->
                    Text(
                        text = numOfProblems.toString(),
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.QuestionSummaryContent(
    problem: Problem,
    totalProblems: List<Problem>,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass? = null,
    onProblemClick: ((Problem, List<Problem>) -> Unit)? = null,
    onProblemLongClick: (() -> Unit)? = null,
    isDeleteWrongProblemDialogOpened: DeleteWrongProblemDialog? = null,
    updateDeletingWrongProblemDialogOpened: ((DeleteWrongProblemDialog) -> Unit)? = null,
    deleteTargetWrongProblem: ((Problem) -> Unit)? = null,
    selectedQuestionInSplitScreen: Problem? = null,
) {
    val useSplitScreen = if (windowSizeClass == null) {
        false
    } else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }

    isDeleteWrongProblemDialogOpened?.let { dialog ->
        if (dialog.isOpened) {
            AppInfoDialog(
                icon = CpaIcons.Delete,
                title = stringResource(id = R.string.delete_wrong_problem),
                description = stringResource(id = R.string.question_delete_wrong_note),
                onConfirm = {
                    deleteTargetWrongProblem?.invoke(dialog.problem.toDomain())
                    updateDeletingWrongProblemDialogOpened?.invoke(
                        isDeleteWrongProblemDialogOpened.copy(isOpened = false),
                    )
                },
                onDismiss = {
                    updateDeletingWrongProblemDialogOpened?.invoke(
                        isDeleteWrongProblemDialogOpened.copy(isOpened = false),
                    )
                },
            )
        }
    }

    val haptic = LocalHapticFeedback.current

    Column(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onProblemClick?.invoke(problem, totalProblems)
                },
                onLongClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onProblemLongClick?.invoke()
                },
            )
            .modifyIf(useSplitScreen && problem == selectedQuestionInSplitScreen) {
                background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(0.5.dp))
                    .then(
                        border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RectangleShape,
                        ),
                    )
            }
            .widthBySplit(useSplitScreen)
            .padding(bottom = 20.dp)
            .animateItemPlacement(),
    ) {
        Box {
            LazyRow(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp),
            ) {
                item {
                    val assistChipContainerColor = DayNightGray070S

                    NotClickableAssistedChip(
                        modifier = Modifier.padding(all = 4.dp),
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = "${problem.source} ${problem.year}년 ${problem.pid}번")
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(containerColor = assistChipContainerColor),
                        border = null,
                    )
                }

                val containerColorResourceIdByType = chipContainerColorResIdByType(problem.type)
                item {
                    NotClickableAssistedChip(
                        modifier = Modifier.padding(all = 4.dp),
                        label = {
                            ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                Text(text = problem.type.toKorean())
                            }
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = colorResource(id = containerColorResourceIdByType)
                                .copy(alpha = 0.2f),
                        ),
                        border = null,
                    )
                }

                item {
                    if (problem.subtype.isNotBlank()) {
                        NotClickableAssistedChip(
                            modifier = Modifier.padding(all = 4.dp),
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(text = problem.subtype)
                                }
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = containerColorResourceIdByType)
                                    .copy(alpha = 0.2f),
                            ),
                            border = null,
                        )
                    }
                }
            }
        }

        Text(
            text = buildAnnotatedString {
                for (keyword in QuizUtil.highlightKeywords) {
                    if (problem.description.contains(keyword)) {
                        val start = problem.description.indexOf(keyword)
                        val end = start + keyword.length

                        append(problem.description.substring(0, start))
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = DayNightGray900S,
                                textDecoration = TextDecoration.Underline,
                            ),
                        ) {
                            append(problem.description.substring(start, end))
                        }
                        append(problem.description.substring(end))
                        return@buildAnnotatedString
                    }
                }

                append(problem.description)
            },
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp),
            style = Typography.bodyMedium,
        )
    }
}

@Composable
fun QuestionSummaryDivider(windowSizeClass: WindowSizeClass? = null) {
    val useSplitScreen = if (windowSizeClass == null) {
        false
    } else {
        windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    }
    Divider(
        modifier = Modifier
            .widthBySplit(useSplitScreen)
            .padding(horizontal = 12.dp),
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
    )
}
