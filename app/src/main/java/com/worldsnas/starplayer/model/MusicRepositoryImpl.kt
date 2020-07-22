package com.worldsnas.starplayer.model

import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.model.persistent.AppDataBase
import com.worldsnas.starplayer.model.persistent.FavoriteMusic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    //TODO : online musics are removing because they are not in local List
    //TODO : change to flow
    override suspend fun getLocalData(): List<MusicRepoModel> =
        withContext(Dispatchers.IO) {
            val localList = localMusicProvider.getAllMusic()
           val favList =  appDataBase.favoriteMusicDao().getAllFavorites().first().toMutableList()


            for (localItem in localList) {
                for (favItem in favList) {
                    if (localItem.id == favItem.id) {
                        localItem.isFavorite = true
                        favList.remove(favItem)
                        break
                    }
                }

            }
            notExistMusic(favList)
            localList
        }

    override fun getFavoriteData(): Flow<List<MusicRepoModel>> =
        appDataBase.favoriteMusicDao().getAllFavorites().map {
            it.map { value ->
                MusicRepoModel(
                    value.id,
                    value.title,
                    value.artist,
                    value.album,
                    value.genre,
                    value.address,
                    true
                )
            }
        }

    private suspend fun notExistMusic(favoritesList: MutableList<FavoriteMusic>) {
        appDataBase.favoriteMusicDao().deleteFavoriteMusicsList(favoritesList)
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