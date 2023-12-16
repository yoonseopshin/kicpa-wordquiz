package com.ysshin.cpaquiz.domain.model

enum class QuizType {
    Accounting,
    Business,
    CommercialLaw,
    TaxLaw,
    ;

    fun toKorean() = when (this) {
        Accounting -> "회계학"
        Business -> "경영학"
        CommercialLaw -> "상법"
        TaxLaw -> "세법"
    }

    companion object {
        fun all() = listOf(Accounting, Business, CommercialLaw, TaxLaw)

        fun from(value: String): QuizType {
            return when (value) {
                "회계학" -> Accounting
                "경영학" -> Business
                "상법" -> CommercialLaw
                "세법" -> TaxLaw
                else -> throw IllegalArgumentException("Fail to convert String \"$value\" to QuizType.")
            }
        }
    }
}
