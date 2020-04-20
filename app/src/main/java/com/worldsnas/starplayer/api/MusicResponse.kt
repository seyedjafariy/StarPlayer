package com.worldsnas.starplayer.api

data class MusicResponse(
    var id: Long,
    var page: Long,
    var musicLink: String,
    var artist: String,
    var cover: String,
    var name: String
)