package com.worldsnas.starplayer.view.music_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicListViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) :
    ViewModel() {
    init {
        getLocalMusic()
//        getMusics()
    }

    private val postMusicList = MutableLiveData<List<MusicRepoModel>>()

    private fun getLocalMusic() {
        viewModelScope.launch {
            val musicsList = musicRepository.getLocalData()
            postMusicList.postValue(musicsList)
        }
    }

    private fun getMusics() {
        viewModelScope.launch {
            val musicsList = musicRepository.getApiData(1, 10)
            postMusicList.postValue(musicsList)
        }
    }

    fun postMusic(): LiveData<List<MusicRepoModel>> {
        return postMusicList
    }
}