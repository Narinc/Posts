package com.narinc.posts.domain.usecase

import com.narinc.posts.core.DomainError
import com.narinc.posts.core.Result
import com.narinc.posts.domain.repository.PostRepository
import javax.inject.Inject

class RefreshPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(): Result<Unit, DomainError> = repository.refreshIfNeeded()
}