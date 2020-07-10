package com.shriharsh.newsapp.di

import com.shriharsh.newsapp.data.NewsDataRepository
import com.shriharsh.newsapp.data.local.model.ArticleDao
import com.shriharsh.newsapp.data.local.source.NewsLocalDataSource
import com.shriharsh.newsapp.data.remote.network.NewsNetworkAPI
import com.shriharsh.newsapp.data.remote.source.NewsRemoteDataSource
import com.shriharsh.newsapp.domain.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {

    @Provides
    fun providesNewsRemoteRepository(networkAPI: NewsNetworkAPI) =
        NewsRemoteDataSource(
            networkAPI
        )

    @Provides
    fun providesNewsLocalRepository(articleDao: ArticleDao) =
        NewsLocalDataSource(
            articleDao
        )

    @Provides
    fun providesNewsDataRepository(
        remoteDataSource: NewsRemoteDataSource,
        localDataSource: NewsLocalDataSource
    ): NewsRepository =
        NewsDataRepository(
            remoteDataSource, localDataSource
        )
}