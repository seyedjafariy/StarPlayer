package com.worldsnas.starplayer.viewModel

import androidx.lifecycle.*
import com.worldsnas.starplayer.service.model.Music
import com.worldsnas.starplayer.service.repository.ProjectRepository
import kotlinx.coroutines.launch

class MusicListViewModel(private val repository: ProjectRepository) : ViewModel() {

    private val _musicList = MutableLiveData<List<Music>>()
    val musicList: LiveData<List<Music>> = _musicList

    fun fetchMusicList() {
        viewModelScope.launch {
            _musicList.value = repository.fetchMusicList()
        }
    }
}