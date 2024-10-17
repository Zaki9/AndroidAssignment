package com.example.lloydassignment.domain.mapper

import com.example.lloydassignment.data.remote.model.NewsArticle
import com.example.lloydassignment.domain.constants.DomainConstants.UNKNOWN
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.util.generateUniqueId
import com.example.lloydassignment.domain.util.toFormattedDate

class NewsArticleMapper {
    /**
     * Maps a `NewsArticle` object to a `NewsArticleUI` object.
     *
     * This function transforms a `NewsArticle` object into a `NewsArticleUI` object.
     * @param article The `NewsArticle` object to be mapped.
     * @return `NewsArticleUI`
     */
    fun map(article: NewsArticle): NewsArticleUI {
        return NewsArticleUI(
            id = generateUniqueId(),
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt.toFormattedDate(),
            source = article.source?.name ?: UNKNOWN
        )
    }

    /**
     * Maps a list of `NewsArticle` objects to a list of `NewsArticleUI` objects.
     *
     * This function transforms a list of `NewsArticle` objects into a `NewsArticleUI` object
     * @param articles The list of `NewsArticle` objects to be mapped.
     * @return A list of `NewsArticleUI`
     */
    fun map(articles: List<NewsArticle>): List<NewsArticleUI> {
        return articles.map { map(it) }
    }
}