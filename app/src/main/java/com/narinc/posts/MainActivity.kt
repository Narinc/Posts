package com.narinc.posts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.narinc.posts.presentation.list.PostListScreen
import com.narinc.posts.ui.theme.PostsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PostListScreen(onPostClick = {
                        TODO()
                    })
                }
            }
        }
    }
}