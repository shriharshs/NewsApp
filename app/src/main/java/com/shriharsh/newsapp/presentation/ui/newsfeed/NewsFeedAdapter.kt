package com.shriharsh.newsapp.presentation.ui.newsfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shriharsh.newsapp.R
import com.shriharsh.newsapp.domain.model.Article
import com.shriharsh.newsapp.utils.getFormattedDate
import com.shriharsh.newsapp.utils.loadImage
import kotlinx.android.synthetic.main.news_feed_item.view.*

class NewsFeedAdapter(private var list: List<Article>, private val onClick: (Article) -> Unit) :
    RecyclerView.Adapter<NewsFeedAdapter.NewsFeedItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsFeedAdapter.NewsFeedItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_feed_item, parent, false)
        return NewsFeedItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        newsFeedItemViewHolder: NewsFeedAdapter.NewsFeedItemViewHolder,
        position: Int
    ) {
        newsFeedItemViewHolder.bindData(article = list[position])
    }

    fun setData(articleList: List<Article>){
        list = articleList
        notifyDataSetChanged()
    }

    inner class NewsFeedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(article: Article) {
            loadImage(itemView.context, article.articleBanner, itemView.iv_news_feed_banner)
            itemView.tv_news_title.text = article.title
            itemView.tv_news_source.text = article.articleSource
            itemView.tv_news_date.text = getFormattedDate(article.publishDate)
            itemView.setOnClickListener { onClick.invoke(article) }
        }
    }
}