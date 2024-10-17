package com.example.lloydassignment.domain.model

data class NewsArticleUI(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String,
    val source: String
)