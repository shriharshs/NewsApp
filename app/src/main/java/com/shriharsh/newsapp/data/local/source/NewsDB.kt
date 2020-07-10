package com.shriharsh.newsapp.data.local.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shriharsh.newsapp.data.local.model.ArticleDao
import com.shriharsh.newsapp.domain.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDB: RoomDatabase() {
    abstract fun articlesDao(): ArticleDao
}