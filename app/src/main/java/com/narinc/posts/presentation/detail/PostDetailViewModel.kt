package com.narinc.posts.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narinc.posts.domain.usecase.ObservePostUseCase
import com.narinc.posts.domain.usecase.UpdatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    observePostUseCase: ObservePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase
) : ViewModel() {

    private val postId: Int = checkNotNull(savedStateHandle["postId"])

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState: StateFlow<PostDetailUiState> = _uiState

    init {
        viewModelScope.launch {
            observePostUseCase(postId).collect { post ->
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
            updatePostUseCase(postId, _uiState.value.title, _uiState.value.body)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}