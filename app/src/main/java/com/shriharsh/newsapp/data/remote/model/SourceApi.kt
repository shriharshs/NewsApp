package com.shriharsh.newsapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class SourceApi(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    val name: String
)