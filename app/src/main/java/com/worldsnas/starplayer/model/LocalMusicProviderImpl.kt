package com.worldsnas.starplayer.model

interface LocalMusicProviderImpl {

    suspend fun getAllMusic() : List<Music>
}