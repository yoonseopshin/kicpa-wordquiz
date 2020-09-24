package com.cpa.cpa_word_problem.util

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

    fun classify(description: String): Pair<String, String> {
        val pattern = "[a-z][.]".toRegex()
        val tokens = description.split(pattern).map { it.trim() }
        val description = tokens[0]
        val subDescriptionBuilder = StringBuilder()
        for (i in 1 until tokens.size) {
            subDescriptionBuilder.append(('a' + i - 1)).append(tokens[i]).append('\n')
        }
        val subDescription = subDescriptionBuilder.toString()
        return Pair(description, subDescription)
    }
}