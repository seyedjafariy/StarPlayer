package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.mockGetMusicsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel> {

        val apiMusicList = mockGetMusicsApi()
//            val apiMusicList = webServiceApi.getMusics(page, count)

        return apiMusicList.map {

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


    override suspend fun getLocalData(): List<MusicRepoModel> =
        localMusicProvider.getAllMusic()

}