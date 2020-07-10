package com.shriharsh.newsapp.domain

import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.domain.model.TopHeadlines
import com.shriharsh.newsapp.utils.Resource


interface NewsRepository {
    suspend fun fetchTopHeadlines(): Resource<TopHeadlines>
    suspend fun getCachedArticles(): List<Article>
}
