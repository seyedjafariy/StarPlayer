package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.di.qualifier.IoDispatcher
import com.worldsnas.starplayer.fakeGetMusicsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    val webServiceApi: WebServiceApi,
    private val dispatcher: CoroutineDispatcher
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel> {
        return withContext(dispatcher) {
            val apiMusicList = fakeGetMusicsApi()
//            val apiMusicList = webServiceApi.getMusics(page, count)
            apiMusicList.map {

                MusicRepoModel(
                    it.id.toInt(),
                    it.name,
                    it.artist,
                    "album",
                    "genre",
                    it.musicLink
                )
            }
        }


    }


    override suspend fun getLocalData(): List<MusicRepoModel> {
        return withContext(dispatcher) {
            localMusicProvider.getAllMusic()
        }
    }


}