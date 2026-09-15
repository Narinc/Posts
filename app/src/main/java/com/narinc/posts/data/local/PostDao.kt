package com.narinc.posts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id ASC")
    fun observePosts(): Flow<List<PostEntity>>

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deleteById(postId: Int)

    @Query("UPDATE posts SET title = :title, body = :body WHERE id = :postId")
    suspend fun updatePost(postId: Int, title: String, body: String)

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun observePostById(postId: Int): Flow<PostEntity?>
}