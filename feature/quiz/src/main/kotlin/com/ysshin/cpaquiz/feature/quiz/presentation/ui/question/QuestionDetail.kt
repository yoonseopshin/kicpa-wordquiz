package com.ysshin.cpaquiz.feature.quiz.presentation.ui.question

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ysshin.cpaquiz.core.android.R
import com.ysshin.cpaquiz.core.android.ui.modifier.bounceClickable
import com.ysshin.cpaquiz.core.android.util.RegexUtils
import com.ysshin.cpaquiz.core.android.util.chipContainerColorResIdByType
import com.ysshin.cpaquiz.designsystem.component.NotClickableAssistedChip
import com.ysshin.cpaquiz.designsystem.theme.Typography
import com.ysshin.cpaquiz.domain.model.Problem
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.presentation.util.QuizUtil

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun QuestionDetail(
    currentQuestion: Problem,
    modifier: Modifier = Modifier,
    onQuestionClick: (Int) -> Unit = {},
    onSelectAnswer: () -> Unit = {},
    questionClickable: Boolean = true,
    isSelectedQuestion: (Int) -> Boolean = { false },
) {
    ElevatedCard(modifier = modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp, top = 8.dp)) {
        Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 12.dp)) {
            Box(
                modifier = modifier.fillMaxWidth()
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.Start,
                    modifier = modifier.align(Alignment.TopStart)
                ) {
                    item {
                        val assistChipContainerColor = colorResource(id = R.color.daynight_gray100a)
                        NotClickableAssistedChip(
                            modifier = Modifier.padding(all = 4.dp),
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(
                                        text = stringResource(
                                            id = com.ysshin.cpaquiz.feature.quiz.R.string.text_source_year_pid,
                                            currentQuestion.source,
                                            currentQuestion.year,
                                            currentQuestion.pid
                                        )
                                    )
                                }
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = assistChipContainerColor
                            ),
                            border = null,
                        )
                    }

                    val containerColorResourceIdByType =
                        chipContainerColorResIdByType(currentQuestion.type)

                    item {
                        NotClickableAssistedChip(
                            modifier = Modifier.padding(all = 4.dp),
                            label = {
                                ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                    Text(text = currentQuestion.type.toKorean())
                                }
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = colorResource(id = containerColorResourceIdByType)
                                    .copy(alpha = 0.2f)
                            ),
                            border = null,
                        )
                    }

                    item {
                        if (currentQuestion.subtype.isNotBlank()) {
                            NotClickableAssistedChip(
                                modifier = Modifier.padding(all = 4.dp),
                                label = {
                                    ProvideTextStyle(value = MaterialTheme.typography.labelMedium) {
                                        Text(text = currentQuestion.subtype)
                                    }
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = colorResource(id = containerColorResourceIdByType)
                                        .copy(alpha = 0.2f)
                                ),
                                border = null,
                            )
                        }
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp),
                text = buildAnnotatedString {
                    for (keyword in QuizUtil.highlightKeywords) {
                        if (currentQuestion.description.contains(keyword)) {
                            val start = currentQuestion.description.indexOf(keyword)
                            val end = start + keyword.length

                            append(currentQuestion.description.substring(0, start))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.daynight_gray900s),
                                    textDecoration = TextDecoration.Underline,
                                )
                            ) {
                                append(currentQuestion.description.substring(start, end))
                            }
                            append(currentQuestion.description.substring(end))
                            return@buildAnnotatedString
                        }
                    }

                    append(currentQuestion.description)
                },
                style = Typography.bodyMedium,
                color = colorResource(id = R.color.daynight_gray800s),
            )

            if (currentQuestion.subDescriptions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))

                val elevation = 1.dp
                Column(
                    modifier = Modifier
                        .padding(all = elevation)
                        .shadow(elevation = elevation, shape = ShapeDefaults.Medium)
                        .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .fillMaxWidth()
                ) {
                    currentQuestion.subDescriptions.forEach { subDescription ->
                        val (mark, description) = RegexUtils.getMarkedString(subDescription)

                        Row {
                            Text(
                                text = mark,
                                style = Typography.bodyMedium,
                                color = colorResource(id = R.color.daynight_gray600s),
                            )
                            Text(
                                text = description,
                                style = Typography.bodyMedium,
                                color = colorResource(id = R.color.daynight_gray600s),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // questions
            currentQuestion.questions.forEachIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .bounceClickable(
                            enabled = questionClickable,
                            dampingRatio = 0.95f,
                            useHapticFeedback = false,
                            onClick = {
                                onQuestionClick(index)
                            },
                            onLongClick = {
                                onQuestionClick(index)
                                onSelectAnswer()
                            },
                        )
                        .semantics {
                            this.selected = isSelectedQuestion(index)
                        }
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelectedQuestion(index),
                        onClick = null,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier
                            .testTag("rb$index")
                            .semantics { testTagsAsResourceId = true },
                        text = s,
                        style = Typography.bodyMedium,
                        color = colorResource(id = R.color.daynight_gray600s),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionDetailPreview() {
    QuestionDetail(
        currentQuestion = Problem(
            year = 2022,
            pid = 15,
            type = QuizType.Business,
            description = "Sample description",
            subDescriptions = listOf("A. Hello", "B. World"),
            questions = listOf("Q1", "Q2", "Q3", "Q4", "Q5"),
            source = ProblemSource.CPA,
        ),
        onQuestionClick = {},
        onSelectAnswer = {},
        isSelectedQuestion = { it == 1 }
    )
}

@Preview(showBackground = true)
@Composable
fun QuestionDetailHangeulPreview() {
    QuestionDetail(
        currentQuestion = Problem(
            year = 2022,
            pid = 16,
            type = QuizType.CommercialLaw,
            description = "상법상 반대주주의 주식매수청구권이 인정되는 경우를 모두 고른 것은?",
            subDescriptions = listOf(
                "ᄀ. 간이영업양도ᆞ양수에 반대하는 주주",
                "ᄂ. 소규모합병을 반대하는 소멸회사의 주주",
                "ᄃ. 주주총회의 결의에 의하여 해산한 회사에서 회사 계속의 결의에 반대하는 주주",
                "ᄅ. 영업 일부의 임대에 반대하는 주주",
                "ᄆ. 타인과 영업의 손익 전부를 같이 하는 계약의 체결ᆞ변경 또는 해약에 반대하는 주주"
            ),
            questions = listOf(
                "ㄱ, ㄴ, ㄷ",
                "ㄱ, ㄴ, ㅁ",
                "ㄱ, ㄹ, ㅁ",
                "ㄴ, ㄷ, ㄹ",
                "ㄷ, ㄹ, ㅁ"
            ),
            source = ProblemSource.CPA,
            subtype = "회사법"
        ),
        onQuestionClick = {},
        onSelectAnswer = {},
        isSelectedQuestion = { it == 1 }
    )
}
