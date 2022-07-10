package com.ysshin.cpaquiz.feature.home.presentation.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ysshin.cpaquiz.feature.home.R
import com.ysshin.cpaquiz.shared.android.util.setTextFadeAnimation
import com.ysshin.cpaquiz.shared.android.util.visibleOrGone
import com.ysshin.cpaquiz.shared.android.util.visibleOrInvisible

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
