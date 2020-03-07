package com.worldsnas.starplayer.service.model

data class Music(
    val id: Int,
    val image: String,
    val title: String,
    val artist: String,
    val album: String,
    val genre: String,
    val duration: Int
)