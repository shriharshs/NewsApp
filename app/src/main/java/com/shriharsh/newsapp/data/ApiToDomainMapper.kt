package com.shriharsh.newsapp.data

import com.shriharsh.newsapp.data.remote.model.ArticleApi
import com.shriharsh.newsapp.data.remote.model.NewsApi
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.domain.model.TopHeadlines

fun NewsApi.toDomainModel(): TopHeadlines {
    return TopHeadlines(
        totalResults = totalResults,
        topArticles = articles.map { it.toDomainModel() }
    )
}

fun ArticleApi.toDomainModel(): Article {
    return Article(
        title = title,
        author = author,
        content = content,
        publishDate = publishedAt,
        articleSource = source.name,
        description = description,
        articleUrl = url,
        articleBanner = urlToImage
    )
}