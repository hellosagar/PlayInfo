package dev.sagar.playinfo.domain

typealias ResultComplete = Result<Unit>

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(
        val error: Throwable,
    ) : Result<Nothing>()
}

fun <T : Any> Result<T>.getOrThrow(): T {
    return if (this is Result.Success) {
        data
    } else {
        throw (this as Result.Error).error
    }
}

fun <T : Any> Result<T>.getOrNull(): T? {
    return if (this is Result.Success) {
        data
    } else {
        null
    }
}

fun <T : Any> Result<List<T>>.getOrEmptyList(): List<T> {
    return if (this is Result.Success) {
        data
    } else {
        emptyList()
    }
}

fun <A : Any, B : Any> Result<A>.flatMap(transform: (A) -> Result<B>): Result<B> {
    return if (this is Result.Success) {
        transform(data)
    } else {
        this as Result.Error
    }
}

suspend fun <A : Any, B : Any> Result<A>.suspendFlatMap(transform: suspend (A) -> Result<B>): Result<B> {
    return if (this is Result.Success) {
        transform(data)
    } else {
        this as Result.Error
    }
}

inline fun <A : Any, B : Any> Result<A>.map(transform: (A) -> B): Result<B> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> Result.Success(transform(data))
    }
}

inline fun <T : Any> Result<T>.onSuccess(execute: (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        execute(data)
    }
    return this
}

inline fun <T : Any> Result<T>.onError(execute: (Throwable) -> Unit): Result<T> {
    if (this is Result.Error) {
        execute(error)
    }
    return this
}
