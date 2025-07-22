package com.example.watchlistmovieapp.data.Network

import com.example.watchlistmovieapp.CONSTS.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    val okHttpClient= OkHttpClient.Builder()

    fun apiprovider()= Retrofit.Builder()
        .client(okHttpClient.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

}