package com.example.lloydassignment.domain.usecase

import com.example.lloydassignment.data.remote.model.NewsArticle
import com.example.lloydassignment.data.remote.model.NewsResponse
import com.example.lloydassignment.data.remote.model.Source
import com.example.lloydassignment.domain.mapper.NewsArticleMapper
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.repository.NewsRepository
import com.example.lloydassignment.domain.result.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class FetchNewsListUseCaseTest {

    private lateinit var newsRepository: NewsRepository
    private lateinit var newsArticleMapper: NewsArticleMapper
    private lateinit var fetchNewsListUseCase: FetchNewsListUseCase
    private lateinit var mockNewsArticle: NewsArticle
    private lateinit var mockNewsArticleUi: NewsArticleUI

    @Before
    fun setUp() {
        newsRepository = mock(NewsRepository::class.java)
        newsArticleMapper = mock(NewsArticleMapper::class.java)
        fetchNewsListUseCase = FetchNewsListUseCase(newsRepository, newsArticleMapper)

        mockNewsArticle =
            NewsArticle(
                title = "mock",
                description = "mock",
                url = "http://mockurl1.com",
                author = "mock",
                content = "mock",
                publishedAt = "mock",
                source = Source(name = "mock", id = "123"),
                urlToImage = "http://mockurl.com",
            )
        mockNewsArticleUi = NewsArticleUI(
            id = "123",
            title = "mock",
            description = "mock",
            url = "http://mockurl1.com",
            publishedAt = "mock",
            source = "mock",
            urlToImage = "http://mockurl.com",
        )
    }

    @Test
    fun invokeTestSuccess() = runBlocking {
        val articles = listOf(mockNewsArticle)
        val mappedArticles = listOf(mockNewsArticleUi)
        `when`(newsRepository.fetchNews()).thenReturn(
            Result.Success(
                NewsResponse(
                    articles = articles,
                    status = "ok",
                    totalResults = 1
                )
            )
        )
        `when`(newsArticleMapper.map(articles)).thenReturn(mappedArticles)
        val result = fetchNewsListUseCase.invoke()

        //Assert
        assertTrue(result is Result.Success)
        assertEquals(mappedArticles, (result as Result.Success).data)
    }

    @Test
    fun invokeTestError() = runBlocking {
        val exception = Exception("Error")
        `when`(newsRepository.fetchNews()).thenReturn(Result.Error(exception))
        val result = fetchNewsListUseCase.invoke()

        //Assert
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).exception)
    }

    @Test
    fun invokeTestLoading() = runBlocking {
        `when`(newsRepository.fetchNews()).thenReturn(Result.Loading)
        val result = fetchNewsListUseCase.invoke()

        //Assert
        assertTrue(result is Result.Loading)
    }
}