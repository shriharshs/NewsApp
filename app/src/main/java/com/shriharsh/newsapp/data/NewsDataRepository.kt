package com.shriharsh.newsapp.data

import com.shriharsh.newsapp.data.local.source.NewsLocalDataSource
import com.shriharsh.newsapp.data.remote.source.NewsRemoteDataSource
import com.shriharsh.newsapp.domain.NewsRepository
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.domain.model.TopHeadlines
import com.shriharsh.newsapp.utils.Resource
import timber.log.Timber
import javax.inject.Inject


class NewsDataRepository @Inject constructor(private val remoteDataSource: NewsRemoteDataSource,
                                             private val localDataSource: NewsLocalDataSource
) :
    NewsRepository {

    override suspend fun fetchTopHeadlines(): Resource<TopHeadlines> {
        return when (val newsApi = remoteDataSource.fetchTopHeadlines()) {
            is Resource.Success -> {
                Timber.e("Success = ${newsApi.data.totalResults}")
                val topHeadlines = newsApi.data.toDomainModel()

                if (!topHeadlines.topArticles.isNullOrEmpty()) {
                    localDataSource.saveAllArticles(topHeadlines.topArticles)
                }

                Resource.Success(topHeadlines)
            }
            is Resource.Failure -> {
                Timber.e("Error = ${newsApi.throwable}")
                Resource.Failure(newsApi.throwable)
            }
            is Resource.Loading -> {
                Timber.e("Loading")
                Resource.Loading()
            }
        }
    }

    override suspend fun getCachedArticles(): List<Article> {
        return localDataSource.getAllArticles()
    }
}
