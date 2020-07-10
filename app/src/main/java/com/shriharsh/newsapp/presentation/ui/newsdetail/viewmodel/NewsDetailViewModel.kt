package com.shriharsh.newsapp.presentation.ui.newsdetail.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.utils.Resource

class NewsDetailViewModel @ViewModelInject constructor() : ViewModel() {

    private var newsDetail = MutableLiveData<Resource<Article>>()

    fun observeArticleData() : LiveData<Resource<Article>> {
       return newsDetail
    }

    fun setArticleData(article: Article) {
        newsDetail.postValue(Resource.Success(article))
    }
}