package com.cpa.cpa_word_problem.utils

class QuizClassifier private constructor() {

    companion object {
        @Volatile
        private var instance: QuizClassifier? = null

        @JvmStatic
        fun getInstance(): QuizClassifier =
            instance ?: synchronized(this) {
                instance ?: QuizClassifier().also {
                    instance = it
                }
            }
    }

    fun classify(str: String): Pair<String, String> {
        val pattern = "[a-z][.]".toRegex()
        val tokens = str.split(pattern).map { it.trim() }
        val subDescriptionBuilder = StringBuilder()
        for (i in 1 until tokens.size) {
            subDescriptionBuilder.append("${('a' + i - 1)}. ${tokens[i]}\n")
        }
        val description = tokens[0].trim()
        val subDescription = subDescriptionBuilder.toString().trim()
        return Pair(description, subDescription)
    }
}