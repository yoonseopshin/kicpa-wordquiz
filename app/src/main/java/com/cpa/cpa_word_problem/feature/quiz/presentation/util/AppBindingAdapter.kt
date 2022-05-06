package com.cpa.cpa_word_problem.feature.quiz.presentation.util

import android.annotation.SuppressLint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.cpa.cpa_word_problem.R
import com.cpa.cpa_word_problem.feature.quiz.domain.model.ProblemSource
import com.cpa.cpa_word_problem.feature.quiz.domain.model.QuizType
import com.cpa.cpa_word_problem.feature.quiz.presentation.screen.quiz.ProblemDetailMode
import com.cpa.cpa_word_problem.util.*
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("SetTextI18n")
@BindingAdapter("year", "pid", requireAll = true)
fun TextView.bindYearAndPid(year: Int?, pid: Int?) {
    year ?: return
    pid ?: return

    visibility = if (year == 0 || pid == 0) View.GONE else View.VISIBLE
    text = "${year}년 ${pid}번"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("source")
fun Chip.bindSource(source: ProblemSource?) {
    source?.let { problemSource ->
        text = when (problemSource) {
            ProblemSource.CPA, ProblemSource.CTA -> {
                visible()
                problemSource.name
            }
            else -> {
                gone()
                ""
            }
        }
        visible()
    } ?: run {
        gone()
    }
}

@BindingAdapter("sub_descriptions")
fun TextView.bindSubDescription(subDescriptions: List<String>?) {
    if (subDescriptions.isNullOrEmpty()) {
        visibility = View.GONE
        return
    }

    visibility = View.VISIBLE

    val builder = StringBuilder()
    subDescriptions.map { subDescription ->
        builder.append(subDescription.trim()).append("\n")
    }
    text = builder.toString().trim()
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
            chipBackgroundColor = context.colorStateList(R.color.commercial_law_highlight_color_0_20)
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
    if (isOpened) {
        animate().rotation(0f).start()
    } else {
        animate().rotation(180f).start()
    }
}

@BindingAdapter("use_alarm", "alarm_hour_of_day", "alarm_minute", requireAll = true)
fun TextView.bindAlarmTime(useAlarm: Boolean?, hourOfDay: Int?, minute: Int?) {
    if (useAlarm == null || useAlarm.not()) {
        text = context.getString(R.string.time_picker_desc)
        return
    }

    if (hourOfDay == null || hourOfDay < 0L || minute == null || minute < 0L) {
        text = context.getString(R.string.time_picker_desc)
        return
    }

    val description = if (minute == 0) {
        context.getString(R.string.time_picker_desc_with_hour_only, hourOfDay)
    } else {
        context.getString(R.string.time_picker_desc_with_time, hourOfDay, minute)
    }
    val spannable = SpannableString(description)

    val start = 3
    val end = description.indexOf("에")

    spannable.setSpan(ForegroundColorSpan(context.getColor(R.color.secondaryColor)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    text = spannable
}
