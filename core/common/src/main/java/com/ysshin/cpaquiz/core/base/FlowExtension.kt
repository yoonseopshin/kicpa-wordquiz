package com.ysshin.cpaquiz.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip

fun <T1, T2, T3, T4, T5, T6, R> combine(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>,
    f5: Flow<T5>,
    f6: Flow<T6>,
    transform: suspend (T1, T2, T3, T4, T5, T6) -> R,
): Flow<R> = combine(
    combine(f1, f2, f3, ::Triple),
    combine(f4, f5, f6, ::Triple),
) { t1, t2 ->
    transform(t1.first, t1.second, t1.third, t2.first, t2.second, t2.third)
}

fun <T1, T2, T3, T4, R> zip(
    f1: Flow<T1>,
    f2: Flow<T2>,
    f3: Flow<T3>,
    f4: Flow<T4>,
    transform: suspend (T1, T2, T3, T4) -> R,
): Flow<R> = f1.zip(f2, ::Pair).zip(f3.zip(f4, ::Pair)) { p1, p2 ->
    transform(p1.first, p1.second, p2.first, p2.second)
}
