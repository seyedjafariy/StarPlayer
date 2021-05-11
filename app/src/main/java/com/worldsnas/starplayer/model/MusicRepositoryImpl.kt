package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.mockGetMusicsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
        private val localMusicProvider: LocalMusicProvider,
        private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel> =
            withContext(Dispatchers.IO) {

                val apiMusicList = mockGetMusicsApi()
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


    override suspend fun getLocalData(): List<MusicRepoModel> =
            withContext(Dispatchers.IO) {
                localMusicProvider.getAllMusic()
            }
}