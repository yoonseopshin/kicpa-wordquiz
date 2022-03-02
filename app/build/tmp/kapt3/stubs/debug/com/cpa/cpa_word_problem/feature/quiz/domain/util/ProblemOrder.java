package com.cpa.cpa_word_problem.feature.quiz.domain.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0001\tB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001d\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0001\u0001\n\u00a8\u0006\u000b"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/ProblemOrder;", "", "orderType", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/OrderType;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/OrderType;)V", "invoke", "", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/model/Problem;", "problems", "Time", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/ProblemOrder$Time;", "app_debug"})
public abstract class ProblemOrder {
    private final com.cpa.cpa_word_problem.feature.quiz.domain.util.OrderType orderType = null;
    
    private ProblemOrder(com.cpa.cpa_word_problem.feature.quiz.domain.util.OrderType orderType) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> invoke(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cpa.cpa_word_problem.feature.quiz.domain.model.Problem> problems) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/ProblemOrder$Time;", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/ProblemOrder;", "orderType", "Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/OrderType;", "(Lcom/cpa/cpa_word_problem/feature/quiz/domain/util/OrderType;)V", "app_debug"})
    public static final class Time extends com.cpa.cpa_word_problem.feature.quiz.domain.util.ProblemOrder {
        
        public Time(@org.jetbrains.annotations.NotNull()
        com.cpa.cpa_word_problem.feature.quiz.domain.util.OrderType orderType) {
            super(null);
        }
    }
}