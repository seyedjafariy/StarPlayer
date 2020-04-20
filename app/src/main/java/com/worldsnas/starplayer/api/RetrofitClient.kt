package com.worldsnas.starplayer.api

import com.worldsnas.starplayer.ConstValues
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ConstValues.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient().newBuilder().build())
        .build()

    val WEB_SERVICE_API : WebServiceApi = retrofit().create(WebServiceApi::class.java)
}