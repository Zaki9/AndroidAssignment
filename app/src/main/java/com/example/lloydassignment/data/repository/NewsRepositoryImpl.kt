package com.example.lloydassignment.data.repository

import com.example.lloydassignment.data.constants.ApiConstants.ERROR_EMPTY_RESP
import com.example.lloydassignment.data.constants.ApiConstants.ERROR_FETCH
import com.example.lloydassignment.data.remote.model.NewsResponse
import com.example.lloydassignment.data.remote.service.NewsApiService
import com.example.lloydassignment.domain.repository.NewsRepository
import com.example.lloydassignment.domain.result.Result
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val apiService: NewsApiService) :
    NewsRepository {

    /**
     * Fetches latest news articles from the news API.
     * This function makes a network call to the `NewsApiService`
     * to retrieve the latest news articles.
     * @return A `Result` object containing either the `NewsResponse` or an `Exception`.
     */
    override suspend fun fetchNews(): Result<NewsResponse> {
        return try {
            val newsResponse = apiService.getNews()
            val body = newsResponse.body()

            if (newsResponse.isSuccessful && body != null && body.articles?.isNotEmpty() == true) {
                Result.Success(body)
            } else {
                Result.Error(Exception(ERROR_EMPTY_RESP))
            }
        } catch (e: Exception) {
            Result.Error(Exception(ERROR_FETCH + e.message))
        }
    }
}