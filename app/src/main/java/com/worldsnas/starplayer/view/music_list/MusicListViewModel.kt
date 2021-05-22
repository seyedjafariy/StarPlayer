package com.worldsnas.starplayer.view.music_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import kotlinx.coroutines.*
import javax.inject.Inject

open class MusicListViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) :
    ViewModel() {
    init {
        getMusics()
    }

    private val postMusicList = MutableLiveData<List<MusicRepoModel>>()

   private fun getMusics() {
        viewModelScope.launch(Dispatchers.IO) {
            val musicsList = musicRepository.getLocalData()
            postMusicList.postValue(musicsList)
        }
    }

    fun getMusics(page: Int, count: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val musicsList = musicRepository.getApiData(page, count)
            postMusicList.postValue(musicsList)
        }
    }

    fun postMusic(): LiveData<List<MusicRepoModel>> {
        return postMusicList
    }


}

