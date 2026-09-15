package com.narinc.posts.data.mapper

import com.narinc.posts.data.local.PostEntity
import com.narinc.posts.data.remote.PostDto
import com.narinc.posts.domain.model.Post

fun PostDto.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun PostEntity.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
    imageUrl = "https://picsum.photos/300/300?random=$id&grayscale"
)