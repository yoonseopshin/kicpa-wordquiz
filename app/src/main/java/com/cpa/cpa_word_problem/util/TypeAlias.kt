package com.cpa.cpa_word_problem.util

typealias Action = () -> Unit
typealias Supplier<T> = () -> T
typealias Consumer<T> = (T) -> Unit
typealias Function<T, P> = (T) -> P
typealias SuspendAction = suspend () -> Unit
typealias SuspendSupplier<T> = suspend () -> T
typealias SuspendConsumer<T> = suspend (T) -> Unit
typealias SuspendFunction<T, P> = suspend (T) -> P