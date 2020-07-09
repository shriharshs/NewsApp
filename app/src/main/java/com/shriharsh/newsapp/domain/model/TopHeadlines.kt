package com.shriharsh.newsapp.domain.model

data class TopHeadlines(
    val totalResults: Int,
    val topArticles: List<Article>
)