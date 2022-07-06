package com.ysshin.cpaquiz.feature.home.presentation.util

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.shared.android.util.setTextFadeAnimation
import com.ysshin.cpaquiz.shared.android.util.visibleOrGone
import com.ysshin.cpaquiz.shared.android.util.visibleOrInvisible
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

@BindingAdapter("dday")
fun Toolbar.bindDDay(nextExamDate: String?) {
    if (nextExamDate.isNullOrBlank()) return

    val now = LocalDate.now()
    val target = LocalDate.parse(nextExamDate, DateTimeFormatter.ISO_DATE)

    val dday = Duration.between(now.atStartOfDay(), target.atStartOfDay()).toDays()

    title = "D-$dday"
}
