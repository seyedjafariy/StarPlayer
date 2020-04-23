package com.worldsnas.starplayer.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicResponse(
    var id: Long,
    var page: Long,
    var musicLink: String,
    var artist: String,
    var cover: String,
    var name: String
)