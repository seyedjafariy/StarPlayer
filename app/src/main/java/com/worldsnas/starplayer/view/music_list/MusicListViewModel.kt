package com.worldsnas.starplayer.view.music_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.di.qualifier.IoDispatcher
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepository
import com.worldsnas.starplayer.utils.Resource
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicListViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : ViewModel() {


    private val _musicList = MutableLiveData<Resource<List<MusicRepoModel>>>()
    val musicList: LiveData<Resource<List<MusicRepoModel>>>
        get() = _musicList

    private val _localMusicList = MutableLiveData<Resource<List<MusicRepoModel>>>()
    val localMusicList: LiveData<Resource<List<MusicRepoModel>>>
        get() = _localMusicList


    fun getMusics() {
        viewModelScope.launch(dispatcher) {
            _localMusicList.postValue(Resource.loading(null))
            _localMusicList.postValue(musicRepository.getLocalData())
        }
    }


    fun getMusics(page: Int, count: Int) {
        viewModelScope.launch(dispatcher) {
            _musicList.postValue(Resource.loading(null))
            _musicList.postValue(musicRepository.getApiData(page, count))
        }
    }


}

