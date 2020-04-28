package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(): List<LocalMusic> {
        val localMusicList: MutableList<LocalMusic> = ArrayList<LocalMusic>().toMutableList()
        return withContext(Dispatchers.IO) {
            val musicList = webServiceApi.getMusics(1,10)

            if (musicList.isSuccessful && musicList.body()?.isNotEmpty()!!) {
                musicList.body()?.forEach {

                    val music =
                        LocalMusic(
                            it.id.toInt(),
                            it.name,
                            it.artist,
                            "album",
                            "genre",
                            it.musicLink
                        )
                    localMusicList += music
                }
            }
            return@withContext localMusicList
        }
    }

    override suspend fun getLocalData(): List<LocalMusic> =
        withContext(Dispatchers.IO) {
            return@withContext localMusicProvider.getAllMusic()
        }


}