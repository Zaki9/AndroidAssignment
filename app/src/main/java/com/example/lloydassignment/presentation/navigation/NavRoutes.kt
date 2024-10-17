package com.example.lloydassignment.presentation.navigation

/**
 * Depicts the navigation routes for the News app
 * @property route The string path for the navigation route.
 */
sealed class NewsAppRoutes(val route: String) {
    object NewsHomeScreen : NewsAppRoutes("newsScreen")

    object NewsDetailsScreen : NewsAppRoutes("newsDetail/{articleId}") {
        fun createRoute(articleId: String): String {
            return "newsDetail/$articleId"
        }
    }
}
