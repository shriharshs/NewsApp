package com.shriharsh.newsapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class NewsApi(
    @SerializedName("articles")
    val articles: List<ArticleApi>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)