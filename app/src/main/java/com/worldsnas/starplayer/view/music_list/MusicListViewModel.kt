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
) : ViewModel() {


    private val _musicList = MutableLiveData<List<MusicRepoModel>>()
    val musicList: LiveData<List<MusicRepoModel>>
        get() = _musicList


    fun getMusics() {
        viewModelScope.launch {
            _musicList.postValue(musicRepository.getLocalData())
        }
    }


    fun getMusics(page: Int, count: Int) {
        viewModelScope.launch {
            _musicList.postValue(musicRepository.getApiData(page, count))
        }
    }


}

