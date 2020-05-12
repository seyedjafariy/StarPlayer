package com.worldsnas.starplayer.model.persistent

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMusicDao {

    @Query("select * from favorites")
    suspend fun getFavoriteMusics(): List<FavoriteMusic>

    @Insert
    suspend fun insert(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun delete(favoriteMusic: FavoriteMusic)
}