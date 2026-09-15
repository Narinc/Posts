package com.narinc.posts.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narinc.posts.core.DomainError
import com.narinc.posts.core.Result
import com.narinc.posts.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val UNDO_WINDOW_MS = 3000L

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    private val pendingDeletions = MutableStateFlow<Map<Int, Job>>(emptyMap())
    val pendingDeletionIds: StateFlow<Set<Int>> = pendingDeletions
        .map { it.keys }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    val uiState: StateFlow<PostListUiState> = combine(
        repository.observePosts(),
        isLoading
    ) { posts, loading ->
        PostListUiState(posts = posts, isLoading = loading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PostListUiState(isLoading = true)
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.refreshIfNeeded()) {
                is Result.Success -> Unit
                is Result.Error -> _errorEvents.tryEmit(result.error.toMessage())
            }
            isLoading.value = false
        }
    }

    /**
     * Kullanıcı item'ı swipe ettiğinde çağrılır. Item'ı hemen Room'dan silmez;
     * geri alma suresi boyunca beklemeye alır.
     */
    fun scheduleDelete(postId: Int) {
        val job = viewModelScope.launch {
            delay(UNDO_WINDOW_MS.milliseconds)
            repository.deletePost(postId)
            pendingDeletions.update { it - postId }
        }
        pendingDeletions.update { it + (postId to job) }
    }

    fun undoDelete(postId: Int) {
        pendingDeletions.value[postId]?.cancel()
        pendingDeletions.update { it - postId }
    }

    private fun DomainError.toMessage(): String = when (this) {
        is DomainError.NetworkError -> "İnternet bağlantınızı kontrol edin"
        is DomainError.ServerError -> "Sunucu hatası (kod: $code)"
        is DomainError.UnknownError -> "Beklenmeyen bir hata oluştu"
    }
}