package com.renatsayf.network.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiBuilder {

    private const val API_URL = "https://run.mocky.io/v3/"

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(API_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    val api: IApi by lazy {
        provideRetrofit().create(IApi::class.java)
    }
}