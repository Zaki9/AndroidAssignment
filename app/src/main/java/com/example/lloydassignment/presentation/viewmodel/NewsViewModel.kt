package com.example.lloydassignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lloydassignment.domain.model.NewsArticleUI
import com.example.lloydassignment.domain.result.Result
import com.example.lloydassignment.domain.usecase.FetchNewsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val fetchNewsListUseCase: FetchNewsListUseCase
) : ViewModel() {
    private val _newsResult = MutableStateFlow<Result<List<NewsArticleUI>>>(Result.Loading)
    val newsResult = _newsResult.asStateFlow()

    // Fetching news list on init
    init {
        viewModelScope.launch {
            _newsResult.value = fetchNewsListUseCase.invoke()
        }
    }

    /**
     * This function fetches a `NewsArticleUI` by its unique identifier.
     * This function searches through the current list of news articles stored in `_newsResult`
     * and returns the first match found
     *
     * @param articleId Id for article
     * @return `NewsArticleUI` object or `null` if not found
     */
    fun fetchArticleFromId(articleId: String): NewsArticleUI? {
        return (_newsResult.value as Result.Success).data.firstOrNull {
            it.id == articleId
        }
    }
}