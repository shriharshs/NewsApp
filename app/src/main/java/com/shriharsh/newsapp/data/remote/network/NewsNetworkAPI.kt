package com.shriharsh.newsapp.data.remote.network

import com.shriharsh.newsapp.data.remote.model.NewsApi
import retrofit2.http.GET


interface NewsNetworkAPI {

    companion object {
        const val VERSION_V2 = "/v2"
        const val API_KEY = "58f61a5154974e2989f508080396decc"
    }

    @GET("$VERSION_V2/top-headlines?country=us&apiKey=$API_KEY")
    suspend fun fetchTopHeadlines(): NewsApi
}