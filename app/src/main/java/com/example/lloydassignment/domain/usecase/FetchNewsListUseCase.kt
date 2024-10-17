package com.example.lloydassignment.domain.usecase

import com.example.lloydassignment.domain.mapper.NewsArticleMapper
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.repository.NewsRepository
import com.example.lloydassignment.domain.result.Result
import javax.inject.Inject

class FetchNewsListUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsArticleMapper: NewsArticleMapper
) {

    /**
     * This function fetches the latest news articles and transforms them to a list of `NewsArticleUI` objects
     * If the `Result` is successful it returns the list of `NewsArticleUI` objects
     * If the `Result` is an error or loading state, it returns the appropriate`Result` object
     *
     * @return A `Result` object with success/error/loading state.
     */
    suspend operator fun invoke(): Result<List<NewsArticleUI>> {
        // UseCase logic here
        return when (val result = newsRepository.fetchNews()) {
            is Result.Loading -> Result.Loading
            is Result.Error -> Result.Error(result.exception)
            is Result.Success -> {
                val articleUI = result.data.articles?.let {
                    newsArticleMapper.map(it)
                }
                return Result.Success(articleUI ?: emptyList())
            }
        }
    }
}