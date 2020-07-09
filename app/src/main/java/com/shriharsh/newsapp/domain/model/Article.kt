package com.shriharsh.newsapp.domain.model

data class Article(
    val title: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishDate: String?,
    val articleSource: String?,
    val articleUrl: String?,
    val articleBanner: String?
)