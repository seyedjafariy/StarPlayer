package com.worldsnas.starplayer.view.online_music_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.worldsnas.starplayer.model.Music
import javax.inject.Inject

class OnlineMusicListViewModel @Inject constructor() : ViewModel() {

    fun postLiveMusicList(): LiveData<List<Music>>? {
        return null
    }

}