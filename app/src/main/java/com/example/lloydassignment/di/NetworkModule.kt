package com.example.lloydassignment.di

import com.example.lloydassignment.BuildConfig
import com.example.lloydassignment.data.constants.ApiConstants.AUTHORIZATION
import com.example.lloydassignment.data.constants.ApiConstants.BASE_URL
import com.example.lloydassignment.data.remote.service.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiKey(): String {
        // return BuildConfig.API_KEY_NAME

// Currently the API key is hardcoded for simplicity.
// Ideally, we should add the API key to the gradle.properties file for dynamic access.
// You can later retrieve it using BuildConfig.API_KEY_NAME.
        return "f67ace1b27b24ce4b95c7f71fde88920"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header(AUTHORIZATION, provideApiKey())
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}