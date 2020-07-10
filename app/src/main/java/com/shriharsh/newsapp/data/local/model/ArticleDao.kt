package com.shriharsh.newsapp.data.local.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shriharsh.newsapp.domain.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticles(list: List<Article>)

    @Query("SELECT * FROM Article")
    suspend fun getAllArticles(): List<Article>

}