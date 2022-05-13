package com.ysshin.cpaquiz.domain.usecase.quiz

import com.ysshin.cpaquiz.domain.repository.QuizRepository

class GetShouldRequestInAppReview(private val repository: QuizRepository) {

    operator fun invoke() = repository.getShouldRequestInAppReview()

}
