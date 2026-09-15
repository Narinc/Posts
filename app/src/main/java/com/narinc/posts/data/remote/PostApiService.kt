package com.narinc.posts.data.remote

import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}