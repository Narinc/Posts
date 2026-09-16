package com.narinc.posts.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.narinc.posts.presentation.detail.PostDetailScreen
import com.narinc.posts.presentation.list.PostListScreen

private const val ROUTE_POST_LIST = "postList"
private const val ROUTE_POST_DETAIL = "postDetail/{postId}"

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = ROUTE_POST_LIST
    ) {
        composable(ROUTE_POST_LIST) {
            PostListScreen(
                onPostClick = { postId ->
                    navController.navigate("postDetail/$postId")
                }
            )
        }
        composable(
            route = ROUTE_POST_DETAIL,
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) {
            PostDetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}