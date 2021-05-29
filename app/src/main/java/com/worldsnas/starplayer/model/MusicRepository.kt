package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.utils.Resource

interface MusicRepository {

    suspend fun getApiData(page: Int, count: Int): Resource<List<MusicRepoModel>>
    suspend fun getLocalData(): Resource<List<MusicRepoModel>>

}