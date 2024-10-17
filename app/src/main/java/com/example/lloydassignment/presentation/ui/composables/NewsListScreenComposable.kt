package com.example.lloydassignment.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.result.Result
import com.example.lloydassignment.presentation.navigation.NewsAppRoutes
import com.example.lloydassignment.presentation.util.UIConstants.ERROR_OCCURRED
import com.example.lloydassignment.presentation.util.UIConstants.NO_DESCRIPTION
import com.example.lloydassignment.presentation.util.UIConstants.NO_TITLE
import com.example.lloydassignment.presentation.viewmodel.NewsViewModel

@Composable
fun NewsHomeScreen(
    viewModel: NewsViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController
) {
    // Collecting news result from NewsViewModel
    when (val newsResult = viewModel.newsResult.collectAsStateWithLifecycle().value) {
        is Result.Error -> {
            LaunchedEffect(key1 = Unit) {
                snackbarHostState.showSnackbar(
                    message = "${newsResult.exception.message}"
                )
            }
            ErrorScreen(errorMessage = ERROR_OCCURRED)
        }

        is Result.Loading -> {
            LoadingComposable()
        }

        is Result.Success -> {
            NewsArticleList(newsArticles = newsResult.data, navController = navController)
        }
    }
}

@Composable
fun NewsArticleList(newsArticles: List<NewsArticleUI>, navController: NavHostController) {
    LazyColumn {
        this.items(newsArticles) { article ->
            // Populating each news article to a card
            NewsArticleItem(article, navController)
        }
    }
}

@Composable
fun NewsArticleItem(article: NewsArticleUI, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(NewsAppRoutes.NewsDetailsScreen.createRoute(article.id))
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            ImageViewComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                urlToImage = article.urlToImage
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title ?: NO_TITLE,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description ?: NO_DESCRIPTION,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.publishedAt,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = article.source,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}