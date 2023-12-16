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

    // [A. , B., ... , Z. , a. , b. , ... , z. ]
    private val alphabetRegex = RegexWithCheckDigit("[A-Za-z]\\. ".toRegex(), 3)

    // [ㄱ. , ㄴ. , ... ㅎ. ]
    private val hangeulJamoRegex = RegexWithCheckDigit("[\u1100-\u11ff]\\. ".toRegex(), 3)

    // [ㄱ. , ㄴ. , ... ㅎ. ]
    private val hangeulCompatJamoRegex = RegexWithCheckDigit("[\u3131-\u314e]\\. ".toRegex(), 3)

    // [가. , 나. , ... , 힣. ]
    private val hangeulSyllablesRegex = RegexWithCheckDigit("[\uAC00-\ud79d]\\. ".toRegex(), 3)

    // [㉠. , ... , ㉭. ]
    private val hangeulEnclosedCJKNumsRegex = RegexWithCheckDigit("[㉠-㉭] ".toRegex(), 2)
    private val indentRegexes =
        listOf(
            alphabetRegex,
            hangeulJamoRegex,
            hangeulCompatJamoRegex,
            hangeulSyllablesRegex,
            hangeulEnclosedCJKNumsRegex,
        )
}

private data class RegexWithCheckDigit(val regex: Regex, val minCheckDigit: Int) {
    fun head(text: String) = text.substring(0, minCheckDigit)
    fun tail(text: String) = text.substring(minCheckDigit)
    fun isMatch(text: String) = regex.matches(head(text))
}
