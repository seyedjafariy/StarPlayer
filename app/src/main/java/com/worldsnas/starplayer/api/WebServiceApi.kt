package com.worldsnas.starplayer.api

import retrofit2.http.GET

interface WebServiceApi {

    @GET("/api/request")
   suspend fun getMusics(): List<MusicResponse>
}