package com.renatsayf.network.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {

    private const val API_URL = "https://run.mocky.io/v3/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    val api: IApi by lazy {
        provideRetrofit().create(IApi::class.java)
    }
}