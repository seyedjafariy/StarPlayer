package com.worldsnas.starplayer.view.musics_list

import androidx.lifecycle.MutableLiveData
import com.worldsnas.starplayer.model.Music


class MusicItemViewModel(private val music: Music, private val listener: Listener) {
    var musicTitle: MutableLiveData<String> = MutableLiveData()
    var musicArtist: MutableLiveData<String> = MutableLiveData()

    init {
        musicTitle.value = music.title
        musicArtist.value = music.artist
    }

    interface Listener {
        fun onItemClick()
    }
}