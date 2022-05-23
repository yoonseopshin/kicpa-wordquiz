package com.ysshin.cpaquiz.feature.quiz.presentation.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
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
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.ysshin.cpaquiz.shared.android.ui.span.AlphabetLeadingMarginSpan
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
fun TextView.bindSubDescription(subDescriptions: List<String>?) {
    val joinedDescription = subDescriptions?.joinToString(separator = "\n") { description ->
        description.trim()
    } ?: return

    text = SpannableStringBuilder(joinedDescription).apply {
        setSpan(AlphabetLeadingMarginSpan(), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
            chipBackgroundColor = context.colorStateList(R.color.accounting_highlight_color_0_20)
        }
        QuizType.Business -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.business)
            chipBackgroundColor = context.colorStateList(R.color.business_highlight_color_0_20)
        }
        QuizType.CommercialLaw -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.commercial_law)
            chipBackgroundColor =
                context.colorStateList(R.color.commercial_law_highlight_color_0_20)
        }
        QuizType.TaxLaw -> {
            visibility = View.VISIBLE
            text = context.getString(R.string.tax_law)
            chipBackgroundColor =
                context.colorStateList(R.color.tax_law_highlight_color_0_20)
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

    val dDay = Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays()
    title = "D-${dDay}"
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

    subtitle = "${solved}/${total}"
}

@BindingAdapter("opened")
fun ImageView.bindOpened(isOpened: Boolean) {
    animate().rotation(if (isOpened) 0f else 180f).start()
}

private val RIGHT_WRONG_REGEX = "옳[가-힣|\\s]{0,3}은".toRegex()

@BindingAdapter("quiz_description")
fun TextView.bindQuizDescription(description: String) {
    text = RIGHT_WRONG_REGEX.find(description)?.let { result ->
        val start = result.range.first
        val end = result.range.last + 1

        SpannableStringBuilder(description).apply {
            setSpan(UnderlineSpan(), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.daynight_gray900s)),
                start,
                end,
                SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    } ?: description
}
