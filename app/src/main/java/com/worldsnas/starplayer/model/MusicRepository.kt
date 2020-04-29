package com.worldsnas.starplayer.model

interface MusicRepository {

    suspend fun getApiData(): List<MusicRepoModel>
    suspend fun getLocalData(): List<MusicRepoModel>

}