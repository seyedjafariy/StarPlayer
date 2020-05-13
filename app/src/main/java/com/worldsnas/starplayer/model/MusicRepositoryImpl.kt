package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.model.persistent.FavoriteMusic
import com.worldsnas.starplayer.model.persistent.FavoriteMusicDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi,
    private val favoriteMusicDao: FavoriteMusicDao
) : MusicRepository {

    override suspend fun getApiData(page: Int, count: Int): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {

            val apiMusicList = webServiceApi.getMusics(page, count)

            apiMusicList.body()?.map {

                MusicRepoModel(
                    it.id.toInt(),
                    it.name,
                    it.artist,
                    "album",
                    "genre",
                    it.musicLink,
                    false
                )
            }!!
        }


    override suspend fun getLocalData(): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {

            favoriteMusicDao.getAllMusics()
        }

    override suspend fun saveToDatabase() {
        localMusicProvider.getAllMusic().map {

            val favoriteMusic =
                FavoriteMusic(it.id, it.title, it.artist, it.album, it.genre, it.address, false)
            favoriteMusicDao.insert(favoriteMusic)
        }
    }

    override suspend fun favoriteItemHandler(favoriteMusic: FavoriteMusic) =
        withContext(Dispatchers.IO) {
            favoriteMusicDao.update(favoriteMusic)
        }
}