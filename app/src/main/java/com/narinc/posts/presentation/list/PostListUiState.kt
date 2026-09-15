package com.narinc.posts.presentation.list

import com.narinc.posts.domain.model.Post

data class PostListUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false
)