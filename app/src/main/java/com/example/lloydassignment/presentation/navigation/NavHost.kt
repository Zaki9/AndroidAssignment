package com.example.lloydassignment.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lloydassignment.presentation.ui.composables.NewsDetailScreen
import com.example.lloydassignment.presentation.ui.composables.NewsHomeScreen
import com.example.lloydassignment.presentation.util.UIConstants.ARTICLE_ID
import com.example.lloydassignment.presentation.viewmodel.NewsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: NewsViewModel,
    snackbarState: SnackbarHostState
) {
    NavHost(
        navController = navController,
        startDestination = NewsAppRoutes.NewsHomeScreen.route,

        ) {
        composable(NewsAppRoutes.NewsHomeScreen.route) {
            NewsHomeScreen(viewModel, snackbarState, navController)
        }
        composable(NewsAppRoutes.NewsDetailsScreen.route) { navBackStackEntry ->
            val articleId = navBackStackEntry.arguments?.getString(ARTICLE_ID)
            val article = articleId?.let { viewModel.fetchArticleFromId(it) }
            article?.let {
                NewsDetailScreen(article = it)
            }
        }
    }
}