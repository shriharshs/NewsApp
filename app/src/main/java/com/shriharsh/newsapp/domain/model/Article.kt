package com.shriharsh.newsapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article(
    @PrimaryKey
    val title: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishDate: String?,
    val articleSource: String?,
    val articleUrl: String?,
    val articleBanner: String?
)