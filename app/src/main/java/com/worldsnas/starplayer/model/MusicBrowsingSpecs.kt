package com.worldsnas.starplayer.model

data class MusicBrowsingSpecs(
    var id: Long,
    var page: Long,
    var musicLink: String,
    var artist: String,
    var cover: String,
    var name: String
)