package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.MusicResponse

interface MusicRepository {

    suspend fun getApiData(): List<Music>
    suspend fun getLocalData(): List<LocalMusic>

}