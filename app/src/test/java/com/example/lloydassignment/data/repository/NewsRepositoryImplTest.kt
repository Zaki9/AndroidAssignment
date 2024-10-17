package com.example.lloydassignment.data.repository

import com.example.lloydassignment.data.constants.ApiConstants.ERROR_EMPTY_RESP
import com.example.lloydassignment.data.remote.model.NewsArticle
import com.example.lloydassignment.data.remote.service.NewsApiService
import org.junit.Test
import org.mockito.Mockito.`when`
import kotlinx.coroutines.runBlocking
import com.example.lloydassignment.data.remote.model.NewsResponse
import com.example.lloydassignment.data.remote.model.Source
import com.example.lloydassignment.domain.result.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito.mock

class NewsRepositoryImplTest {
    private lateinit var apiServiceMock: NewsApiService
    private lateinit var newsRepository: NewsRepositoryImpl
    private lateinit var mockNewsResponse: NewsResponse

    @Before
    fun setUp() {
        apiServiceMock = mock(NewsApiService::class.java)
        newsRepository = NewsRepositoryImpl(apiServiceMock)
        mockNewsResponse = NewsResponse(
            articles = listOf(
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
            ), status = "ok", totalResults = 1
        )
    }

    @Test
    fun fetchNewsTestResultSuccess() = runBlocking {
        `when`(apiServiceMock.getNews()).thenReturn(Response.success(mockNewsResponse))
        val result = newsRepository.fetchNews()

        //Assert
        assertTrue(result is Result.Success)
        assertEquals(mockNewsResponse, (result as Result.Success).data)
    }

    @Test
    fun fetchNewsTestResultEmpty() = runBlocking {
        `when`(apiServiceMock.getNews()).thenReturn(
            Response.success(
                NewsResponse(articles = null, "200", 0)
            )
        )
        val result = newsRepository.fetchNews()

        //Assert
        assertTrue(result is Result.Error)
        assertEquals(ERROR_EMPTY_RESP, (result as Result.Error).exception.message)
    }

    @Test
    fun fetchNewsTestResultError() = runBlocking {
        val errorResponseBody =
            "{\"error\":\"Internal Server Error\"}".toResponseBody("application/json".toMediaTypeOrNull())
        `when`(apiServiceMock.getNews()).thenReturn(
            Response.error(500, errorResponseBody)
        )
        val result = newsRepository.fetchNews()

        //Assert
        assertTrue(result is Result.Error)
        assertEquals(ERROR_EMPTY_RESP, (result as Result.Error).exception.message)
    }
}