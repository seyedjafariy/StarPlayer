package com.worldsnas.starplayer.view.musics_list

import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.persistent.FavoriteMusic

interface OnClickListener {

    fun onItemClickListener(music: Music)
    fun onFavoriteClickListener(favoriteMusic: FavoriteMusic)
}