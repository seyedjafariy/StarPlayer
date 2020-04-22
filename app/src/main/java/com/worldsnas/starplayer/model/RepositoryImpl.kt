package com.worldsnas.starplayer.model

import android.util.Log
import com.worldsnas.starplayer.api.MusicResponse
import com.worldsnas.starplayer.api.WebServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : Repository {

    override suspend fun getApiData(): List<MusicResponse> {

        return withContext(Dispatchers.IO) {
            val musicList = webServiceApi.getMusics()
            Log.d("myTag", musicList.toString())
            return@withContext musicList
        }
    }

    override suspend fun getLocalData(): List<LocalMusic> =
        withContext(Dispatchers.IO) {
            return@withContext localMusicProvider.getAllMusic()
        }


}