package com.worldsnas.starplayer.view.musics_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.LocalMusicProvider
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import com.worldsnas.starplayer.model.Music
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicListViewModel @Inject constructor(private val localMusicProvider: LocalMusicProvider) :
    ViewModel() {

    private val postMusicList = MutableLiveData<List<Music>>()

    private fun getAllMusic() {
        viewModelScope.launch {
            val musicsList = localMusicProvider.getAllMusic()

            postMusicList.postValue(musicsList)
        }
    }

    fun postMusic(): LiveData<List<Music>> {
        getAllMusic()
        return postMusicList
    }
}