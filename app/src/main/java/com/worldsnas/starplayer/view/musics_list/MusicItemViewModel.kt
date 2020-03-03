package com.worldsnas.starplayer.view.musics_list

import androidx.databinding.ObservableField
import com.worldsnas.starplayer.model.Music


class MusicItemViewModel(private val music: Music,private val listener:Listener) {
    var musicTitle: ObservableField<String> = ObservableField(music.title)
    var musicArtist: ObservableField<String> = ObservableField(music.artist)

    interface Listener {
        fun onItemClick()
    }
}