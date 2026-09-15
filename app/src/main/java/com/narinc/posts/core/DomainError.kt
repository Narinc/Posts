package com.narinc.posts.core

/**
 * Domain seviyesinde, UI'ın anlayabileceği hata tipleri.
 */
sealed class DomainError {
    data object NetworkError : DomainError()
    data class ServerError(val code: Int) : DomainError()
    data object UnknownError : DomainError()
}