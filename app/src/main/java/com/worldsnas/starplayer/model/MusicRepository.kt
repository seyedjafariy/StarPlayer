package com.worldsnas.starplayer.model

interface MusicRepository {

    suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel>
    suspend fun getLocalData(): List<MusicRepoModel>

}