package com.ysshin.cpaquiz.feature.quiz.presentation.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.SubDescriptionAdapter
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailActivity
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.ysshin.cpaquiz.shared.android.util.*
import com.ysshin.cpaquiz.shared.base.isNullOrDefault
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("SetTextI18n")
@BindingAdapter("year", "pid", requireAll = true)
fun TextView.bindYearAndPid(year: Int?, pid: Int?) {
    year ?: return
    pid ?: return

    visibleOrGone(year > 0 && pid > 0)
    text = "${year}년 ${pid}번"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("source")
fun Chip.bindSource(source: ProblemSource?) {
    source ?: run {
        gone()
        return
    }

    text = when (source) {
        ProblemSource.CPA, ProblemSource.CTA -> {
            visible()
            source.name
        }
        ProblemSource.None -> {
            gone()
            ""
        }
    }
}

@BindingAdapter("sub_descriptions")
fun RecyclerView.bindSubDescription(subDescriptions: List<String>?) {
    val descriptions = subDescriptions ?: return

    adapter = SubDescriptionAdapter().apply {
        submitList(descriptions)
    }
}

@BindingAdapter("problem_detail_mode")
fun FloatingActionButton.bindProblemDetailMode(mode: ProblemDetailMode?) {
    mode ?: return
    visibleOrGone(mode == ProblemDetailMode.Quiz)
}

@BindingAdapter("problem_detail_mode")
fun RadioGroup.bindProblemDetailMode(mode: ProblemDetailMode?) {
    mode ?: return

    if (mode == ProblemDetailMode.Detail) {
        for (index in 0 until childCount) {
            getChildAt(index).isClickable = false
        }
    }
}

@BindingAdapter("problem_type")
fun Chip.bindByType(type: QuizType?) {
    type ?: return

    when (type) {
        QuizType.Accounting -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.accounting)
            chipStrokeColor = context.colorStateList(R.color.accounting_highlight_color)
            chipBackgroundColor = context.colorStateList(R.color.accounting_highlight_color_0_20)
        }
        QuizType.Business -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.business)
            chipStrokeColor = context.colorStateList(R.color.business_highlight_color)
            chipBackgroundColor = context.colorStateList(R.color.business_highlight_color_0_20)
        }
        QuizType.CommercialLaw -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.commercial_law)
            chipStrokeColor = context.colorStateList(R.color.commercial_law_highlight_color)
            chipBackgroundColor = context.colorStateList(R.color.commercial_law_highlight_color_0_20)
        }
        QuizType.TaxLaw -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.tax_law)
            chipStrokeColor = context.colorStateList(R.color.tax_law_highlight_color)
            chipBackgroundColor = context.colorStateList(R.color.tax_law_highlight_color_0_20)
        }
        QuizType.None -> {
            visibility = View.GONE
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("dday")
fun Toolbar.bindDDay(nextExamDate: String?) {
    if (nextExamDate.isNullOrBlank()) return

    val now = LocalDate.now()
    val target = LocalDate.parse(nextExamDate, DateTimeFormatter.ISO_DATE)

    val dday = Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays()
    title = "D-$dday"
}

@BindingAdapter("timer", "last_lap_time", requireAll = true)
fun TextView.bindTimer(timeMillis: Long?, lastLapTime: Long?) {
    timeMillis ?: return

    val currentElapsedTime = (timeMillis - (lastLapTime ?: 0L))
    if (currentElapsedTime <= 0L) return

    text = TimeFormatter.format(currentElapsedTime)
}

@BindingAdapter("solved", "total", requireAll = true)
fun Toolbar.bindProblems(solved: Int?, total: Int?) {
    if (solved.isNullOrDefault() || total.isNullOrDefault()) return

    subtitle = "$solved/$total"
}

@BindingAdapter("opened")
fun ImageView.bindOpened(isOpened: Boolean) {
    animate().rotation(if (isOpened) 0f else 180f).start()
}

private val HIGHLIGHT_KEYWORDS = listOf("틀린", "아닌", "옳은", "옳지 않은", "적절한", "적절하지 않은", "모두")

@BindingAdapter("quiz_description")
fun TextView.bindQuizDescription(description: String) {
    HIGHLIGHT_KEYWORDS.map { keyword ->
        if (description.contains(keyword)) {
            val start = description.indexOf(keyword)
            val end = start + keyword.length
            text = SpannableStringBuilder(description).apply {
                setSpan(UnderlineSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(StyleSpan(Typeface.BOLD), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(context, R.color.daynight_gray900s)),
                    start,
                    end,
                    SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            return
        }
    }

    text = description
}

@BindingAdapter("subject_total_count")
fun TextView.bindSubjectTotalCount(count: Int) {
    visibleOrGone(count > 0)
    text = context.getString(R.string.subject_total_count, count)
}

@BindingAdapter("quiz_settings_opened")
fun FloatingActionButton.bindQuizSettingsOpened(value: Boolean) {
    visibleOrInvisible(value)
}

@BindingAdapter("fade_anim_text")
fun TextView.bindFadeAnimationText(value: String) {
    setTextFadeAnimation(value)
}

@BindingAdapter("quiz_type", "quiz_number", "use_timer", requireAll = true)
fun MaterialCardView.bindOnClickListener(quizType: QuizType, quizNumber: Int, useTimer: Boolean) {
    setOnThrottleClick {
        context.startActivity(
            ProblemDetailActivity.newIntent(
                context = context,
                quizType = quizType,
                quizNumbers = quizNumber,
                useTimer = useTimer
            )
        )
    }
}

@BindingAdapter("is_filtering")
fun Chip.bindFiltering(isFiltering: Boolean) {
    if (isFiltering) {
        chipBackgroundColor = context.colorStateList(R.color.primaryColor_0_15)
        chipStrokeColor = context.colorStateList(R.color.primaryColor)
    } else {
        chipBackgroundColor = context.colorStateList(R.color.daynight_gray070s)
        chipStrokeColor = context.colorStateList(R.color.daynight_gray300s)
    }
}

@BindingAdapter("is_searching")
fun ConstraintLayout.bindSearching(isSearching: Boolean) {
    actionWithChild {
        isEnabled = isSearching.not()
    }
}
