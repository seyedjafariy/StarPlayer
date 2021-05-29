package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.utils.NetworkMusicMapper
import com.worldsnas.starplayer.utils.Resource
import kotlinx.coroutines.delay
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): Resource<List<MusicRepoModel>> {
        return try {
            val result = webServiceApi.getMusics(page, count)
            if (result.isSuccessful) {
                result.body()?.let {
                    Resource.success(NetworkMusicMapper.mapToLocalList(it))
                } ?: Resource.error("response body is null", null)
            } else Resource.error(result.message(), null)

        } catch (e: Exception) {
            Resource.error(e.message, null)
        }

    }


    override suspend fun getLocalData(): Resource<List<MusicRepoModel>> {
        return try {
            Resource.success(localMusicProvider.getAllMusic())
        } catch (e: Exception) {
            Resource.error("unKnown Error", null)
        }


    }


}