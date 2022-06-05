package com.ysshin.cpaquiz.domain.usecase.problem

data class ProblemUseCases(
    val getLocalProblems: GetLocalProblems,
    val getWrongProblems: GetWrongProblems,
    val searchProblems: SearchProblems,
    val insertWrongProblems: InsertWrongProblems,
    val deleteWrongProblem: DeleteWrongProblem,
    val deleteAllWrongProblems: DeleteAllWrongProblems,
    val getProblems: GetProblems,
    val syncRemoteProblems: SyncRemoteProblems,
    val getProblemCount: GetProblemCount,
)
