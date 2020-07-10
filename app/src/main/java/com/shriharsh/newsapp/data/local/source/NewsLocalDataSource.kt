package com.shriharsh.newsapp.data.local.source

import com.shriharsh.newsapp.data.local.model.ArticleDao
import com.shriharsh.newsapp.domain.model.Article
import timber.log.Timber
import javax.inject.Inject


class NewsLocalDataSource @Inject constructor(private val articleDao: ArticleDao) {

    suspend fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles()
    }

    suspend fun saveAllArticles(articlesList: List<Article>) {
        Timber.e("Saving articles ${articlesList.size}")
        articleDao.saveArticles(articlesList)
    }

}