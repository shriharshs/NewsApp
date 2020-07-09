package com.shriharsh.newsapp.presentation.ui.newsfeed

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shriharsh.newsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFeedActivity : AppCompatActivity() {

    private val newsListViewModel by viewModels<NewsFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        fetchTopHeadlines()
    }

    private fun fetchTopHeadlines() {
        newsListViewModel.fetchTopHeadlines()
    }
}