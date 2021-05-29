package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.utils.Resource

/**
 * this fake repository is gonna used to simulate repository's treats
 */
class FakeMusicRepository : MusicRepository {

    private var isEmptyList = false
    private var isError = false

    /**
     * by this method you can set empty list as a return for other methods
     * @param isEmptyList is used to determine if methods returns empty or not
     */
    fun setFlagReturnEmptyList(isEmptyList: Boolean) {
        this.isEmptyList = isEmptyList
    }

    /**
     * by this method you can set an error as a return for other methods
     * @param isError is used to determine if methods returns error or not
     */
    fun setFlagReturnError(isError: Boolean) {
        this.isError = isError
    }


    /**
     * get musics from api
     */
    override suspend fun getApiData(page: Int, count: Int): Resource<List<MusicRepoModel>> {
        return when {
            isEmptyList && !isError-> Resource.success(emptyList())
            isError && !isEmptyList-> Resource.error("unknown error", null)
            else -> Resource.success(createFakeMusics(count))
        }
    }

    /**
     * get local musics from your device
     */
    override suspend fun getLocalData(): Resource<List<MusicRepoModel>> {
        return when {
            isEmptyList && !isError -> Resource.success(emptyList())
            isError && !isEmptyList-> Resource.error("unknown error", null)
            else -> Resource.success(createFakeMusics())
        }
    }


    /**
     * this method can be used for creating fake music list
     */
    private fun createFakeMusics(count: Int = 10): List<MusicRepoModel> {
        val fakeMusicList = arrayListOf<MusicRepoModel>()
        for (i in 0 until count) {
            fakeMusicList.add(
                MusicRepoModel(
                    i, "music_$i", "artist_$i", "album_$i", "genre_$i", "address_$i"
                )
            )
        }
        return fakeMusicList
    }
}