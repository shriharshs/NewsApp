package com.shriharsh.newsapp.presentation.ui.newsdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shriharsh.newsapp.R
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.presentation.ui.newsdetail.viewmodel.NewsDetailViewModel
import com.shriharsh.newsapp.utils.Resource
import com.shriharsh.newsapp.utils.getFormattedDate
import com.shriharsh.newsapp.utils.loadImage
import com.shriharsh.newsapp.utils.withClickableSpan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_news_detail.*

@AndroidEntryPoint
class NewsDetailActivity : AppCompatActivity() {

    private val newsDetailViewModel by viewModels<NewsDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        initViews()

        observeNewsDetail()

        val article: Article = getIntentData()

        newsDetailViewModel.setArticleData(article)
    }

    private fun initViews() {
        iv_detail_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out)
    }

    private fun getIntentData(): Article {
        return intent.getParcelableExtra(INTENT_EXTRA_ARTICLE)
    }

    private fun observeNewsDetail() {
        newsDetailViewModel.observeArticleData().observe(this, Observer { result ->
            when (result) {
                is Resource.Success -> {
                    showNewsDetail(result.data)
                }
            }
        })
    }

    private fun showNewsDetail(article: Article) {
        loadImage(this, article.articleBanner, iv_news_detail_banner)
        tv_detail_news_title.text = article.title
        tv_detail_news_source.text = article.articleSource
        tv_detail_news_date.text = getFormattedDate(article.publishDate)
        tv_detail_news_description.movementMethod = LinkMovementMethod()
        tv_detail_news_description.text =
            getSpannableDescription(article.description, article.articleUrl)
    }

    private fun getSpannableDescription(
        description: String?,
        articleUrl: String?
    ): SpannableString? {
        if (!description.isNullOrEmpty()) {
            val readMore = "\nRead more"
            val descriptionWithReadMore = (description + readMore)
            return SpannableString(descriptionWithReadMore).withClickableSpan(readMore) {
                openFullArticle(articleUrl)
            }
        }

        return null
    }

    private fun openFullArticle(articleUrl: String?) {
        articleUrl?.let {
            val uri: Uri = Uri.parse(it)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        } ?: Toast.makeText(
            this,
            getString(R.string.cannot_open_link),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val INTENT_EXTRA_ARTICLE = "article"
        fun newInstance(context: Context, article: Article) =
            Intent(context, NewsDetailActivity::class.java).apply {
                putExtra(INTENT_EXTRA_ARTICLE, article)
            }
    }
}