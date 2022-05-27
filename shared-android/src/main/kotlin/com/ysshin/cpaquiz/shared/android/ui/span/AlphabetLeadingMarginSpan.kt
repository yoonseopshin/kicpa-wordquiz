package com.ysshin.cpaquiz.shared.android.ui.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import timber.log.Timber

class AlphabetLeadingMarginSpan : LeadingMarginSpan {

    private var indentMargin: Int = 0

    override fun getLeadingMargin(first: Boolean): Int {
        return if (first) 0 else indentMargin
    }

    override fun drawLeadingMargin(
            canvas: Canvas,
            paint: Paint,
            x: Int,
            dir: Int,
            top: Int,
            baseline: Int,
            bottom: Int,
            text: CharSequence,
            start: Int,
            end: Int,
            first: Boolean,
            layout: Layout
    ) {
        if (first.not()) {
            return
        }

        val lineStartText = runCatching { text.substring(start, start + 3) }.getOrNull() ?: return
        Timber.d(lineStartText)

        indentMargin = when {
            ALPHABET_INDENT_REGEX.matches(lineStartText) || HANGUL_INDEX_REGEX.matches(lineStartText) -> {
                (paint.measureText(lineStartText) + .5f).toInt()
            }
            HANGUL_INDEX_REGEX_2.matches(lineStartText) -> {
                (paint.measureText(lineStartText.substring(0, lineStartText.length - 1)) + .5f).toInt()
            }
            else -> 0
        }
    }

    companion object {
        private val ALPHABET_INDENT_REGEX = "[A-Za-z]\\. ".toRegex()
        private val HANGUL_INDEX_REGEX = "[ㄱ-ㅎ]\\. ".toRegex()
        private val HANGUL_INDEX_REGEX_2 = "[㉠-㉭] .".toRegex()
    }
}