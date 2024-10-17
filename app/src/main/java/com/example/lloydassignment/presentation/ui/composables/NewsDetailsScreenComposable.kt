package com.example.lloydassignment.presentation.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.presentation.util.UIConstants.NO_DESCRIPTION
import com.example.lloydassignment.presentation.util.UIConstants.NO_TITLE
import com.example.lloydassignment.presentation.util.UIConstants.PUBLISHED_AT
import com.example.lloydassignment.presentation.util.UIConstants.SOURCED_AT

@Composable
fun NewsDetailScreen(article: NewsArticleUI) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ImageViewComposable(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f),
            urlToImage = article.urlToImage
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = article.title ?: NO_TITLE,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.description ?: NO_DESCRIPTION,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        AnnotatedTextComposable(Modifier.fillMaxWidth(), PUBLISHED_AT, article.publishedAt)
        Spacer(modifier = Modifier.height(8.dp))
        AnnotatedTextComposable(Modifier.fillMaxWidth(), SOURCED_AT, article.source)
    }
}