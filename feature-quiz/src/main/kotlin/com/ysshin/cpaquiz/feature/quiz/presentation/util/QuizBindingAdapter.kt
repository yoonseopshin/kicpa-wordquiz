package com.ysshin.cpaquiz.feature.quiz.presentation.util

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ysshin.cpaquiz.domain.model.ProblemDetailMode
import com.ysshin.cpaquiz.domain.model.ProblemSource
import com.ysshin.cpaquiz.domain.model.QuizType
import com.ysshin.cpaquiz.feature.quiz.R
import com.ysshin.cpaquiz.feature.quiz.presentation.adapter.SubDescriptionAdapter
import com.ysshin.cpaquiz.shared.android.util.*
import com.ysshin.cpaquiz.shared.base.isNullOrDefault

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

    visible()
    text = source.name
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
    }
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
