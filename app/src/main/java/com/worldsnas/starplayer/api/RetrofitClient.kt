package com.worldsnas.starplayer.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    var BASE_URL: String = "http://starplayerservice.herokuapp.com/"
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient().newBuilder().build())
        .build()

    val apiInterface : ApiInterface = retrofit().create(ApiInterface::class.java)
}