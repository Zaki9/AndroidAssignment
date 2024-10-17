package com.example.lloydassignment.domain.repository

import com.example.lloydassignment.data.remote.model.NewsResponse
import com.example.lloydassignment.domain.result.Result

interface NewsRepository {
    suspend fun fetchNews() : Result<NewsResponse>
}