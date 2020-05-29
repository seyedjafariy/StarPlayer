package com.worldsnas.starplayer.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicRepoModel(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val genre: String,
    val address: String
): Parcelable