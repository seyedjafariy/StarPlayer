package com.worldsnas.starplayer.view.online_music_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.worldsnas.starplayer.IRepository
import com.worldsnas.starplayer.model.Music
import javax.inject.Inject

class OnlineMusicListViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {

    fun postLiveMusicList(): LiveData<List<Music>>? {
        return repository.getData()
    }

}