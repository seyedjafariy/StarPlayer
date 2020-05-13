package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.model.persistent.FavoriteMusic

interface MusicRepository {

    suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel>
    suspend fun getLocalData(): List<MusicRepoModel>
    suspend fun saveToDatabase()
    suspend fun favoriteItemHandler(favoriteMusic: FavoriteMusic)

}