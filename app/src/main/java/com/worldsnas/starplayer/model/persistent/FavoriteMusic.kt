package com.worldsnas.starplayer.model.persistent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMusic(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist")val artist: String,
    @ColumnInfo(name = "album")val album: String,
    @ColumnInfo(name = "genre")val genre: String,
    @ColumnInfo(name = "address")val address: String,
    @ColumnInfo(name = "isFavorite")val isFavorite: Boolean
)