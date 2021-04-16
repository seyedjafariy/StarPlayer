package com.worldsnas.starplayer.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val musicRepository: MusicRepository) :
    ViewModel() {
    private lateinit var postMusicList: LiveData<List<MusicRepoModel>>

    init {
        getFavoriteMusic()
    }

    private fun getFavoriteMusic() =
        viewModelScope.launch {
            postMusicList = musicRepository.getFavoriteData().asLiveData()
        }

    fun postMusic(): LiveData<List<MusicRepoModel>> =
        postMusicList

    fun favoritesHandler(musicRepoModel: MusicRepoModel) =
        viewModelScope.launch {
            musicRepository.favoritesHandler(musicRepoModel)
        }
}