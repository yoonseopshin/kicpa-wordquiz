package com.ysshin.cpaquiz.domain.usecase.problem

data class ProblemUseCases(
    val getTotalProblems: GetTotalProblems,
    val getWrongProblems: GetWrongProblems,
    val searchProblems: SearchProblems,
    val insertWrongProblems: InsertWrongProblems,
    val deleteWrongProblem: DeleteWrongProblem,
    val deleteAllWrongProblems: DeleteAllWrongProblems,
    val getProblems: GetProblems,
    val syncRemoteProblems: SyncRemoteProblems,
    val getProblemCount: GetProblemCount,
    val getSubtypesByQuizType: GetSubtypesByQuizType,
)
