package com.worldsnas.starplayer.model

data class MusicRepoModel(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val genre: String,
    val address: String,
    val isFavorite: Boolean
)