package com.worldsnas.starplayer.view.musics_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import com.worldsnas.starplayer.model.Music
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicListViewModel @Inject constructor(private val localMusicProviderImpl: LocalMusicProviderImpl) :
    ViewModel() {

    private val postMusicList = MutableLiveData<List<Music>>()
    private val viewModelJob = SupervisorJob()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getAllMusic(): MutableLiveData<List<Music>> {
        uiScope.launch {
            val musicsList = localMusicProviderImpl.getAllMusic()

            postMusicList.postValue(musicsList)
        }
        return postMusicList
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}