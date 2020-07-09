package com.worldsnas.starplayer.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val musicRepository: MusicRepository) : ViewModel() {
    private val postMusicList = MutableLiveData<List<MusicRepoModel>>()

    init {
        getFavoriteMusic()
    }
    private fun getFavoriteMusic() =
        viewModelScope.launch {
            val musicsList = musicRepository.getFavoriteData()
            postMusicList.postValue(musicsList)
        }
    fun postMusic(): LiveData<List<MusicRepoModel>> =
        postMusicList

    fun favoritesHandler(musicRepoModel: MusicRepoModel) =
        viewModelScope.launch {
            musicRepository.favoritesHandler(musicRepoModel)
        }
}