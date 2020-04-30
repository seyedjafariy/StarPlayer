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
            val musicRepoModelList: MutableList<MusicRepoModel> = ArrayList()

            val musicList = webServiceApi.getMusics(page, count)

            if (musicList.isSuccessful && musicList.body()?.isNotEmpty()!!) {
                musicList.body()?.map {

                    val music =
                        MusicRepoModel(
                            it.id.toInt(),
                            it.name,
                            it.artist,
                            "album",
                            "genre",
                            it.musicLink
                        )
                    musicRepoModelList += music
                }
            }
            musicRepoModelList
        }


    override suspend fun getLocalData(): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {
            localMusicProvider.getAllMusic()
        }


}