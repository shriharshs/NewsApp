package com.shriharsh.newsapp.data.local.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shriharsh.newsapp.data.local.source.NewsDB
import com.shriharsh.newsapp.domain.model.Article
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class ArticleDaoTest {

    private lateinit var articleDao: ArticleDao
    private lateinit var newsDB: NewsDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        newsDB = Room.inMemoryDatabaseBuilder(context, NewsDB::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
        articleDao = newsDB.articlesDao()
    }

    @Test
    @Throws(Exception::class)
    fun writeArticlesAndReadTheSame() = runBlocking {
        articleDao.saveArticles(
            listOf(
                Article(
                    "abc",
                    "pqr",
                    "xyz",
                    "lmnn",
                    "today",
                    "cnn",
                    "url",
                    "banner"
                )
            )
        )
        val allArticles = articleDao.getAllArticles()
        assert(allArticles.size == 1)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        newsDB.close()
    }

}