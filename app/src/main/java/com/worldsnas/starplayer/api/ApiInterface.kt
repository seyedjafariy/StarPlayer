package com.worldsnas.starplayer.api

import com.worldsnas.starplayer.model.MusicBrowsingSpecs
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/request")
    fun getMusics(): Deferred<Response<List<MusicBrowsingSpecs>>>
}