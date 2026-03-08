package com.pasindu.gitregistry.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    private const val baseUrl="https://api.github.com/"

    val logInspec = HttpLoggingInterceptor().apply { level= HttpLoggingInterceptor.Level.BODY }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor (logInspec )
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val api : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}