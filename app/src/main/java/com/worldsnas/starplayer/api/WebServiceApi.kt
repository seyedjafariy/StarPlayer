package com.worldsnas.starplayer.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceApi {
    @GET("/api/paging")
    suspend fun getMusics(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Response<List<MusicResponse>>
}