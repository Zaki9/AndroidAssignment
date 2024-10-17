package com.example.lloydassignment.domain.mapper

import com.example.lloydassignment.data.remote.model.NewsArticle
import com.example.lloydassignment.data.remote.model.Source
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class NewsArticleMapperTest {
    private lateinit var mapper: NewsArticleMapper
    private lateinit var mockNewsArticle: NewsArticle

    @Before
    fun setUp() {
        mapper = NewsArticleMapper()
        mockNewsArticle = NewsArticle(
            title = "mockTitle",
            description = "mockDescription",
            url = "http://mockurl.com",
            author = "mock",
            content = "mock",
            publishedAt = "2024-10-17T11:42:12Z",
            source = Source(name = "mock", id = "123"),
            urlToImage = "http://mockurl.com",
        )
    }

    @Test
    fun mapSingleArticleSuccess() {
        val result = mapper.map(mockNewsArticle)

        //Assert
        TestCase.assertEquals("mockTitle", result.title)
        TestCase.assertEquals("mockDescription", result.description)
        TestCase.assertEquals("http://mockurl.com", result.url)
        TestCase.assertEquals("http://mockurl.com", result.urlToImage)
        TestCase.assertEquals("mock", result.source)
    }

    @Test
    fun mapListArticleSuccess() {
        val newsArticles = listOf(mockNewsArticle)
        val result = mapper.map(newsArticles)

        //Assert
        TestCase.assertEquals(1, result.size)
        TestCase.assertEquals("mockTitle", result[0].title)
    }
}