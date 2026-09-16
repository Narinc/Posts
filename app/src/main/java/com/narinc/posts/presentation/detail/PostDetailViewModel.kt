package com.narinc.posts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narinc.posts.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PostRepository
) : ViewModel() {

    private val postId: Int = checkNotNull(savedStateHandle["postId"])

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.observePost(postId).collect { post ->
                if (post != null) {
                    _uiState.update {
                        it.copy(
                            title = post.title,
                            body = post.body,
                            imageUrl = post.imageUrl,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle, isSaved = false) }
    }

    fun onBodyChange(newBody: String) {
        _uiState.update { it.copy(body = newBody, isSaved = false) }
    }

    fun save() {
        viewModelScope.launch {
            repository.updatePost(postId, _uiState.value.title, _uiState.value.body)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}