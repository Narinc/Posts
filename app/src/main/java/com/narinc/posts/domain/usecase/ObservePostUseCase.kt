package com.narinc.posts.domain.usecase

import com.narinc.posts.domain.model.Post
import com.narinc.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(postId: Int): Flow<Post?> = repository.observePost(postId)
}