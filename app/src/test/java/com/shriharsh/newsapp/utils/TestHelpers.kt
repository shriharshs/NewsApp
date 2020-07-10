package com.shriharsh.newsapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.shriharsh.newsapp.data.remote.model.ArticleApi
import com.shriharsh.newsapp.data.remote.model.NewsApi
import com.shriharsh.newsapp.data.remote.model.SourceApi
import com.shriharsh.newsapp.domain.model.Article
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

fun getDummyNewsAPI(): NewsApi {
    return NewsApi(listOf(getDummyArticleApi()),"ok", 38)
}


fun getDummyHTTPException(): HttpException {
    return HttpException(
        Response.error<Any>(500,
        ResponseBody.create(null, ByteArray(0))))
}

fun getDummyArticles(): List<Article> {
    return  listOf(getDummyArticle())
}

fun getDummyArticle() =
    Article("abc", "pqr", "xyz", "lmn", "today", "cnn", "url", "banner")

private fun getDummyArticleApi() =
    ArticleApi("author","content", "desc", "date", SourceApi("id", "name"),"title","url","urltoimage")