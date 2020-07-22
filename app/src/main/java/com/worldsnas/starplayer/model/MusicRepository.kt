package com.worldsnas.starplayer.model

import kotlinx.coroutines.flow.Flow


interface MusicRepository {

    suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel>
    suspend fun getLocalData(): List<MusicRepoModel>
    fun getFavoriteData(): Flow<List<MusicRepoModel>>
    suspend fun favoritesHandler(musicRepoModel: MusicRepoModel)
}