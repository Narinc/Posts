package com.narinc.posts.core

/**
 * generic result wrapper
 */
sealed class Result<out D, out E> {
    data class Success<out D>(val data: D) : Result<D, Nothing>()
    data class Error<out E>(val error: E) : Result<Nothing, E>()

    inline fun <R> map(transform: (D) -> R): Result<R, E> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }

    inline fun onSuccess(action: (D) -> Unit): Result<D, E> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (E) -> Unit): Result<D, E> {
        if (this is Error) action(error)
        return this
    }
}