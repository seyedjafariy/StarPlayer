package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse
import com.worldsnas.starplayer.api.WebServiceApi
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val webServiceApi: WebServiceApi) : Repository {
    override suspend fun getDataFromApi(): List<MusicResponse> {

        return webServiceApi.getMusics()
    }


}