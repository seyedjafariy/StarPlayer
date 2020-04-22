package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse

interface Repository {

    suspend fun getApiData(): List<MusicResponse>
    suspend fun getLocalData(): List<LocalMusic>

}