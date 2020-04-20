package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse

interface Repository {

    suspend fun getDataFromApi(): List<MusicResponse>
}