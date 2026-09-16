package com.narinc.posts.presentation.detail

data class PostDetailUiState(
    val title: String = "",
    val body: String = "",
    val imageUrl: String = "",
    val isLoading: Boolean = true,
    val isSaved: Boolean = false
)