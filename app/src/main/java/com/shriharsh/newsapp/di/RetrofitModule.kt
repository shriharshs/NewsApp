package com.shriharsh.newsapp.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.shriharsh.newsapp.BuildConfig
import com.shriharsh.newsapp.data.remote.network.NewsNetworkAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RetrofitModule {

    private val API_URL: String = "https://newsapi.org"

    fun getBaseApiUrl(): String {
        return API_URL
    }

    @Provides
    fun providesHttplogging(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun providesOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()
    }

    @Singleton
    @Provides
    fun providesNewsNetworkApi(okHttpClient: OkHttpClient): NewsNetworkAPI {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        return Retrofit.Builder().baseUrl(getBaseApiUrl())
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
            .create(NewsNetworkAPI::class.java)
    }

}