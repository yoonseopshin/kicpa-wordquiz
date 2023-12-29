package com.ysshin.cpaquiz.feature.home.presentation.screen.main

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.ysshin.cpaquiz.core.android.base.BaseViewModel
import com.ysshin.cpaquiz.domain.model.AdType
import com.ysshin.cpaquiz.domain.model.DEFAULT_QUIZ_NUMBER
import com.ysshin.cpaquiz.domain.model.DEFAULT_USE_TIMER
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.domain.usecase.config.ConfigUseCases
import com.ysshin.cpaquiz.domain.usecase.problem.ProblemUseCases
import com.ysshin.cpaquiz.domain.usecase.quiz.QuizUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val problemUseCases: ProblemUseCases,
    private val quizUseCases: QuizUseCases,
    configUseCases: ConfigUseCases,
) : BaseViewModel() {

    private val countMap = MutableStateFlow<MutableMap<QuizType, Int>>(mutableMapOf())
    private val subtypesMap = MutableStateFlow<MutableMap<QuizType, List<String>>>(mutableMapOf())
    private val selectedSubtypes = MutableStateFlow<MutableMap<String, Boolean>>(mutableMapOf())

    val isHomeNativeMediumAdEnabled = configUseCases.getAdConfig(AdType.HomeNativeMediumAd)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = true,
        )

    val homeQuizUiState: StateFlow<HomeQuizUiState> =
        combine(
            countMap,
            subtypesMap,
            selectedSubtypes,
            // To subscribe questions changes in database, but not used actually.
            problemUseCases.getTotalProblems(),
        ) { count, subtypes, selectedSubtypes, _ ->
            fetchAndSetCountAndSubtypes()

            HomeQuizUiState.Success(
                accountingCount = count[QuizType.Accounting] ?: 0,
                accountingSubtypes = createSelectableSubtypeByQuizType(
                    subtypes,
                    selectedSubtypes,
                    QuizType.Accounting,
                ),
                businessCount = count[QuizType.Business] ?: 0,
                businessSubtypes = createSelectableSubtypeByQuizType(
                    subtypes,
                    selectedSubtypes,
                    QuizType.Business,
                ),
                commercialLawCount = count[QuizType.CommercialLaw] ?: 0,
                commercialLawSubtypes = createSelectableSubtypeByQuizType(
                    subtypes,
                    selectedSubtypes,
                    QuizType.CommercialLaw,
                ),
                taxLawCount = count[QuizType.TaxLaw] ?: 0,
                taxLawSubtypes = createSelectableSubtypeByQuizType(
                    subtypes,
                    selectedSubtypes,
                    QuizType.TaxLaw,
                ),
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeQuizUiState.Loading,
            )

    val homeInfoUiState: StateFlow<HomeInfoUiState> =
        combine(
            quizUseCases.getNextExamDate(),
            quizUseCases.getQuizNumber(),
            quizUseCases.getUseTimer(),
        ) { nextExamDate, quizNumber, useTimer ->
            val now = LocalDate.now()
            val target = LocalDate.parse(nextExamDate, DateTimeFormatter.ISO_DATE)
            val dday =
                Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays().toString()
            HomeInfoUiState(dday, quizNumber, useTimer)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeInfoUiState(
                    dday = "",
                    quizNumber = DEFAULT_QUIZ_NUMBER,
                    useTimer = DEFAULT_USE_TIMER,
                ),
            )

    private fun setTimer(value: Boolean) {
        viewModelScope.launch {
            quizUseCases.setUseTimer(value)
        }
    }

    fun toggleTimer() {
        setTimer(homeInfoUiState.value.useTimer.not())
    }

    fun setQuizNumber(value: Int) {
        viewModelScope.launch {
            quizUseCases.setQuizNumber(value)
        }
    }

    fun toggleSubtype(subtype: String) {
        val newSelectedSubtypes = selectedSubtypes.value.toMutableMap()
        // Set to false on toggle because initial value is true.
        val newValue = newSelectedSubtypes[subtype]?.not() ?: false
        newSelectedSubtypes[subtype] = newValue
        selectedSubtypes.value = newSelectedSubtypes
    }

    private fun createSelectableSubtypeByQuizType(
        subtypes: Map<QuizType, List<String>>,
        selectedSubtypes: MutableMap<String, Boolean>,
        quizType: QuizType,
    ): SelectableSubtypes {
        return SelectableSubtypes(
            (subtypes[quizType] ?: emptyList())
                .filter(String::isNotEmpty)
                .map {
                    SelectableSubtype(it, selectedSubtypes[it] ?: true)
                },
        )
    }

    fun getSelectedSubtypes(quizType: QuizType): List<String> {
        val subtypesByQuizType = subtypesMap.value[quizType] ?: return emptyList()

        return selectedSubtypes.value
            .filterKeys(subtypesByQuizType::contains)
            .filterKeys(String::isNotEmpty)
            .filterValues { it }.keys
            .toList()
    }

    private fun fetchAndSetCountAndSubtypes() = viewModelScope.launch {
        // Fetch and set question count
        problemUseCases.getProblemCount(scope = viewModelScope) { countMap.value = it }

        // Fetch and set subtypes
        problemUseCases.getSubtypesByQuizType(scope = viewModelScope) {
            subtypesMap.value = it
            val newSelectedSubtypes = selectedSubtypes.value.toMutableMap()
            for (value in it.values) {
                value.forEach { subtype ->
                    if (newSelectedSubtypes.containsKey(subtype).not()) {
                        newSelectedSubtypes[subtype] = true
                    }
                }
            }
            selectedSubtypes.value = newSelectedSubtypes
        }
    }
}

data class SelectableSubtype(val text: String, val isSelected: Boolean)

@Immutable
data class SelectableSubtypes(val items: List<SelectableSubtype>)

sealed interface HomeQuizUiState {
    data object Loading : HomeQuizUiState
    data class Success(
        val accountingCount: Int,
        val accountingSubtypes: SelectableSubtypes,
        val businessCount: Int,
        val businessSubtypes: SelectableSubtypes,
        val commercialLawCount: Int,
        val commercialLawSubtypes: SelectableSubtypes,
        val taxLawCount: Int,
        val taxLawSubtypes: SelectableSubtypes,
    ) : HomeQuizUiState
}

data class HomeInfoUiState(
    val dday: String,
    val quizNumber: Int,
    val useTimer: Boolean,
)
