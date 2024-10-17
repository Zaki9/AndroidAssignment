package com.example.lloydassignment.data.constants

object ApiConstants {
    const val BASE_URL = "https://newsapi.org"
    const val AUTHORIZATION = "Authorization"
    const val HEADLINES_URL = "/v2/top-headlines?country=us"

    const val ERROR_FETCH = "Error fetching news:"
    const val ERROR_EMPTY_RESP = "$ERROR_FETCH Empty Response from Server "
}