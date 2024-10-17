package com.example.lloydassignment.di

import com.example.lloydassignment.domain.mapper.NewsArticleMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideNewsArticleMapper(): NewsArticleMapper {
        return NewsArticleMapper()
    }
}