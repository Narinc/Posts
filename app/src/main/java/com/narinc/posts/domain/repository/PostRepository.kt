package com.narinc.posts.domain.repository

import com.narinc.posts.core.DomainError
import com.narinc.posts.core.Result
import com.narinc.posts.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun observePosts(): Flow<List<Post>>
    fun observePost(postId: Int): Flow<Post?>
    suspend fun refreshIfNeeded(): Result<Unit, DomainError>
    suspend fun deletePost(postId: Int)
    suspend fun updatePost(postId: Int, title: String, body: String)
}