package com.worldsnas.starplayer.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "page") val page: Long,
    @Json(name = "musicLink") val musicLink: String,
    @Json(name = "artist") val artist: String,
    @Json(name = "cover") val cover: String,
    @Json(name = "name") val name: String
)