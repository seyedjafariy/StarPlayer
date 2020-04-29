package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(): List<MusicRepoModel> {
        val musicRepoModelList: MutableList<MusicRepoModel> = ArrayList<MusicRepoModel>().toMutableList()
        return withContext(Dispatchers.IO) {
            val musicList = webServiceApi.getMusics(1,10)

            if (musicList.isSuccessful && musicList.body()?.isNotEmpty()!!) {
                musicList.body()?.forEach {

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
            return@withContext musicRepoModelList
        }
    }

    override suspend fun getLocalData(): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {
            return@withContext localMusicProvider.getAllMusic()
        }


}