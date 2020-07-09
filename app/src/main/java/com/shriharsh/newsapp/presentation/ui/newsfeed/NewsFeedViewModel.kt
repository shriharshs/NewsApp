package com.shriharsh.newsapp.presentation.ui.newsfeed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shriharsh.newsapp.domain.NewsRepository
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.utils.Resource
import kotlinx.coroutines.launch
import timber.log.Timber

class NewsFeedViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private var newsFeedList = MutableLiveData<Resource<List<Article>>>()

    fun fetchTopHeadlines() {
        viewModelScope.launch {
            when (val result = repository.fetchTopHeadlines()) {
                is Resource.Success -> {
                    Timber.e("Success = ${result.data.totalResults}")
                    newsFeedList.postValue(Resource.Success(result.data.topArticles))
                }
                is Resource.Failure -> {
                    Timber.e("Error = ${result.throwable}")
                    newsFeedList.postValue(Resource.Failure(result.throwable))
                }
                is Resource.Loading -> {
                    Timber.e("Loading")
                    newsFeedList.postValue(Resource.Loading())
                }
            }
        }
    }

}