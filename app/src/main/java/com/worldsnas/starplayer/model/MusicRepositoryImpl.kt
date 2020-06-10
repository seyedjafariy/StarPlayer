package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.model.persistent.AppDataBase
import com.worldsnas.starplayer.model.persistent.FavoriteMusic
import com.worldsnas.starplayer.model.persistent.FavoriteMusicDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val localMusicProvider: LocalMusicProvider,
    private val webServiceApi: WebServiceApi,
    private val appDataBase: AppDataBase
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
            val localList = localMusicProvider.getAllMusic().toMutableList()
            val favoritesList = appDataBase.favoriteMusicDao().getAllMusics()

            for (localItem in localList) {
                for (favItem in favoritesList) {
                    if (localItem.id == favItem.id)
                        localItem.isFavorite = true
                }
            }
            localList
        }

    override suspend fun favoritesHandler(musicRepoModel: MusicRepoModel) {
        val favoriteMusic = FavoriteMusic(
            musicRepoModel.id,
            musicRepoModel.title,
            musicRepoModel.artist,
            musicRepoModel.album,
            musicRepoModel.genre,
            musicRepoModel.address
        )

        if (musicRepoModel.isFavorite) {
            appDataBase.favoriteMusicDao().insert(favoriteMusic)
        } else {
            appDataBase.favoriteMusicDao().delete(favoriteMusic)
        }
    }


}