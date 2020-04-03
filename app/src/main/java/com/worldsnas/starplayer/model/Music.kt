package com.worldsnas.starplayer.model

import android.net.Uri

data class Music(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val uri: Uri
)