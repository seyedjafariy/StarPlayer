package com.worldsnas.starplayer.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceApi {
    @GET("/api/request")
    suspend fun getMusics(): List<MusicResponse>

    @GET("/api/request")
    fun searchMusicWithNameAndArtist(
        @Query("musicName") musicName : String,
        @Query("artist") artist : String
    ): MusicResponse
}