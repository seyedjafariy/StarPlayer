package com.worldsnas.starplayer.view.musics_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.MusicRepositoryImpl
import kotlinx.coroutines.*
import javax.inject.Inject

class MusicListViewModel @Inject constructor(
    private val repositoryImpl: MusicRepositoryImpl
) :
    ViewModel() {
    init {
        getLocalMusic()
    }

    private val postMusicList = MutableLiveData<List<MusicRepoModel>>()

    private fun getLocalMusic() {
        viewModelScope.launch {
            val musicsList = repositoryImpl.getApiData()
            Log.d("myTag", musicsList.toString())

            postMusicList.postValue(musicsList)
        }
    }

    fun postMusic(): LiveData<List<MusicRepoModel>> {
        return postMusicList
    }
}