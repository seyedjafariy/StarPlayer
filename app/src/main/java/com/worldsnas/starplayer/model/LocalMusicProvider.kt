package com.worldsnas.starplayer.model

interface LocalMusicProvider {

    suspend fun getAllMusic() : List<Music>
}