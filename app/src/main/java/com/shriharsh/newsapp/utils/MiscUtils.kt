package com.shriharsh.newsapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.shriharsh.newsapp.R
import java.text.SimpleDateFormat

fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
    Glide.with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.bg_banner_placeholder)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(imageView)
}

fun getFormattedDate(date: String?): String {
    return date?.let {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        formatter.format(parser.parse(date))
    } ?: ""
}