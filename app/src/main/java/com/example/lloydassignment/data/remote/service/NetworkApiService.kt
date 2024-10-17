package com.example.lloydassignment.data.remote.service;

import com.example.lloydassignment.data.constants.ApiConstants.HEADLINES_URL
import com.example.lloydassignment.data.remote.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiService {
    @GET(HEADLINES_URL)
    suspend fun getNews(): Response<NewsResponse>
}
