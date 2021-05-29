package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.utils.NetworkMusicMapper
import com.worldsnas.starplayer.utils.Resource
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): Resource<List<MusicRepoModel>> {
        return try {
            Resource.loading(null)
            val result = webServiceApi.getMusics(page, count)
            if (result.isSuccessful) {
                val resultMap = result.body()?.let {
                    NetworkMusicMapper.mapToLocalList(it)
                }
                if (resultMap != null)
                    Resource.success(resultMap) else Resource.error("unKnown Error", null)
            } else Resource.error(result.message(), null)

        } catch (e: Exception) {
            Resource.error(e.message, null)
        }

    }


    override suspend fun getLocalData(): List<MusicRepoModel> {
        return localMusicProvider.getAllMusic()
    }


}