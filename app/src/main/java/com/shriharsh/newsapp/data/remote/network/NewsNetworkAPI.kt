package com.shriharsh.newsapp.data.remote.network

import com.shriharsh.newsapp.data.remote.model.NewsApi
import retrofit2.http.GET


interface NewsNetworkAPI {

    companion object {
        const val VERSION_V2 = "/v2"
    }

    @GET("$VERSION_V2/top-headlines?country=us&apiKey=58f61a5154974e2989f508080396decc")
    suspend fun fetchTopHeadlines(): NewsApi
}