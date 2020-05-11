package com.worldsnas.starplayer.model.persistent

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMusicDao {

    @Query("select * from FavoriteMusic")
    suspend fun getFavoriteMusics(): LiveData<List<FavoriteMusic>>

    @Insert
    suspend fun insert(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun delete(favoriteMusic: FavoriteMusic)
}