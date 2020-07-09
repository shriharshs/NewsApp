package com.shriharsh.newsapp.presentation.ui.newsfeed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shriharsh.newsapp.R
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_feed.*
import timber.log.Timber

@AndroidEntryPoint
class NewsFeedActivity : AppCompatActivity() {

    private val newsListViewModel by viewModels<NewsFeedViewModel>()
    private val newsFeedAdapter: NewsFeedAdapter by lazy {
        NewsFeedAdapter(listOf(), onClick = { article ->
           //open detail
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)
        initViews()
        observerFeeds()
        fetchTopHeadlines()
    }

    private fun initViews() {
        with(rv_news_feed){
            adapter = newsFeedAdapter
        }
    }

    private fun fetchTopHeadlines() {
        newsListViewModel.fetchTopHeadlines()
    }

    private fun observerFeeds() {
        newsListViewModel.observerFeeds().observe(this, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    Timber.e("Success = ${result.data}")
                    error_view.visibility = View.GONE
                    shimmer_recycler_view.hideShimmerAdapter()
                    showArticles(result.data)
                }
                is Resource.Failure -> {
                    Timber.e("Error = ${result.throwable}")
                    Toast.makeText(
                        this,
                        getString(R.string.something_went_wrong_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    shimmer_recycler_view.hideShimmerAdapter()
                    error_view.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    Timber.e("Loading")
                    shimmer_recycler_view.showShimmerAdapter()
                }
            }
        })
    }

    private fun showArticles(topArticles: List<Article>) {
        if (!topArticles.isNullOrEmpty()) {
            newsFeedAdapter.setData(topArticles)
        }
    }
}