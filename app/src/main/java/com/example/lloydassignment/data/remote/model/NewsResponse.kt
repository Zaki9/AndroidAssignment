package com.example.lloydassignment.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val articles: List<NewsArticle>?,
    val status: String,
    val totalResults: Int
)

@Serializable
data class NewsArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Source?,
    val title: String,
    val url: String,
    val urlToImage: String
)

@Serializable
data class Source(
    val id: String?,
    val name: String?
)