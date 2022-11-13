package com.ysshin.cpaquiz.core.android.util

object RegexUtils {

    fun getMarkedString(str: String): Pair<String, String> {
        for (indentRegex in indentRegexes) {
            if (indentRegex.isMatch(str)) {
                return Pair(indentRegex.head(str), indentRegex.tail(str))
            }
        }

        return Pair("", str)
    }

    private val alphabetIndentRegex = RegexWithCheckDigit("[A-Za-z]\\. ".toRegex(), 3)
    private val koreanIndentRegex = RegexWithCheckDigit("[ㄱ-ㅎ]\\. ".toRegex(), 3)
    private val specialKoreanIndentRegex = RegexWithCheckDigit("[㉠-㉭] ".toRegex(), 2)
    private val indentRegexes =
        listOf(alphabetIndentRegex, koreanIndentRegex, specialKoreanIndentRegex)
}

private data class RegexWithCheckDigit(val regex: Regex, val minCheckDigit: Int) {
    fun head(text: String) = text.substring(0, minCheckDigit)
    fun tail(text: String) = text.substring(minCheckDigit)
    fun isMatch(text: String) = regex.matches(head(text))
}
