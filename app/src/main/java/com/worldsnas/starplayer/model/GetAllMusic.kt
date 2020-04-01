package com.worldsnas.starplayer.model

interface GetAllMusic {

    suspend fun getAllMusic() : ArrayList<Music>
}