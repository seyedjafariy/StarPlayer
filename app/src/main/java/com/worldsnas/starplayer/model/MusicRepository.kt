package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse

interface MusicRepository {

    suspend fun getApiData(): List<LocalMusic>
    suspend fun getLocalData(): List<LocalMusic>

}