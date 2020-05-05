package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {
            lateinit var musicRepoModelList: List<MusicRepoModel>
            val apiMusicList = webServiceApi.getMusics(page, count)

            if (apiMusicList.isSuccessful && apiMusicList.body()?.isNotEmpty()!!) {
                musicRepoModelList = apiMusicList.body()?.map {

                    val music = MusicRepoModel(
                        it.id.toInt(),
                        it.name,
                        it.artist,
                        "album",
                        "genre",
                        it.musicLink
                    )
                    music
                }!!
            }
            musicRepoModelList
        }

    override suspend fun getLocalData(): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {
            localMusicProvider.getAllMusic()
        }
}