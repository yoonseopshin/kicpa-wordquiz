package com.ysshin.cpaquiz.core.base

typealias Action = () -> Unit
typealias Supplier<T> = () -> T
typealias Consumer<T> = (T) -> Unit
typealias Consumers<T, P> = (T, P) -> Unit
typealias Function<T, P> = (T) -> P
typealias SuspendAction = suspend () -> Unit
typealias SuspendSupplier<T> = suspend () -> T
typealias SuspendConsumer<T> = suspend (T) -> Unit
typealias SuspendFunction<T, P> = suspend (T) -> P
