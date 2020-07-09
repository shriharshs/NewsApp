package com.shriharsh.newsapp.data.remote.source

import com.shriharsh.newsapp.data.remote.model.NewsApi
import com.shriharsh.newsapp.data.remote.network.NewsNetworkAPI
import com.shriharsh.newsapp.utils.Resource
import com.shriharsh.newsapp.utils.handleNetworkCall
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val networkAPI: NewsNetworkAPI) {

    suspend fun fetchTopHeadlines(): Resource<NewsApi> {
        return handleNetworkCall {
            networkAPI.fetchTopHeadlines()
        }
    }
}