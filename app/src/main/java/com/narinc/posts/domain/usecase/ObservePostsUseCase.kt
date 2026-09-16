package com.narinc.posts.domain.usecase

import com.narinc.posts.domain.model.Post
import com.narinc.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> = repository.observePosts()
}