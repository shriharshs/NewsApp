package com.shriharsh.newsapp.di

import android.content.Context
import androidx.room.Room
import com.shriharsh.newsapp.data.local.source.NewsDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesNewsDb(@ApplicationContext context: Context): NewsDB {
        return Room.databaseBuilder(
            context,
            NewsDB::class.java,
            "news_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesArticlesDao(newsDB: NewsDB) = newsDB.articlesDao()

}