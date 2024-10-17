package com.example.lloydassignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.result.Result
import com.example.lloydassignment.domain.usecase.FetchNewsListUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.flow.first
import org.junit.*
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var fetchNewsListUseCase: FetchNewsListUseCase
    private lateinit var newsViewModel: NewsViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fetchNewsListUseCase = mock(FetchNewsListUseCase::class.java)
        newsViewModel = NewsViewModel(fetchNewsListUseCase)
    }

    @Test
    fun initTestSuccess() = runTest {
        val articles = listOf(
            NewsArticleUI(
                id = "123",
                title = "mock",
                description = "mock",
                url = "http://mockurl.com",
                publishedAt = "mock",
                source = "mock",
                urlToImage = "http://mockurl.com"
            )
        )
        val result = Result.Success(articles)
        `when`(fetchNewsListUseCase.invoke()).thenReturn(result)

        newsViewModel = NewsViewModel(fetchNewsListUseCase)
        advanceUntilIdle()

        //Assert
        val newsResult = newsViewModel.newsResult.first()
        assertTrue(newsResult is Result.Success)
        assertEquals(articles, (newsResult as Result.Success).data)
    }

    @Test
    fun fetchArticleFromIdTestSuccess() = runTest {
        val articleId = "123"
        val articles = listOf(
            NewsArticleUI(
                id = "123",
                title = "mock",
                description = "mock",
                url = "http://mockurl.com",
                publishedAt = "mock",
                source = "mock",
                urlToImage = "http://mockurl.com"
            )
        )
        val result = Result.Success(articles)
        `when`(fetchNewsListUseCase.invoke()).thenReturn(result)

        newsViewModel = NewsViewModel(fetchNewsListUseCase)
        advanceUntilIdle()
        val article = newsViewModel.fetchArticleFromId(articleId)

        // Assert
        assertNotNull(article)
        assertEquals(articleId, article?.id)
    }
}