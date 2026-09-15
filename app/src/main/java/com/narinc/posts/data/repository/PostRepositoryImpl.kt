package com.narinc.posts.data.repository

import com.narinc.posts.core.DispatcherProvider
import com.narinc.posts.core.DomainError
import com.narinc.posts.core.Result
import com.narinc.posts.data.local.PostDao
import com.narinc.posts.data.mapper.toDomain
import com.narinc.posts.data.mapper.toEntity
import com.narinc.posts.data.remote.PostApiService
import com.narinc.posts.domain.model.Post
import com.narinc.posts.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService,
    private val postDao: PostDao,
    private val dispatchers: DispatcherProvider
) : PostRepository {

    override fun observePosts(): Flow<List<Post>> =
        postDao.observePosts().map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshIfNeeded(): Result<Unit, DomainError> = withContext(dispatchers.io) {
        if (postDao.count() > 0) return@withContext Result.Success(Unit)

        try {
            val dtos = apiService.getPosts()
            postDao.insertAll(dtos.map { it.toEntity() })
            Result.Success(Unit)
        } catch (_: IOException) {
            Result.Error(DomainError.NetworkError)
        } catch (e: HttpException) {
            Result.Error(DomainError.ServerError(e.code()))
        } catch (_: Exception) {
            Result.Error(DomainError.UnknownError)
        }
    }

    override suspend fun deletePost(postId: Int) = withContext(dispatchers.io) {
        postDao.deleteById(postId)
    }

    override suspend fun updatePost(postId: Int, title: String, body: String) = withContext(dispatchers.io) {
        postDao.updatePost(postId, title, body)
    }
}