package com.worldsnas.starplayer.model.persistent


import androidx.room.*
import com.worldsnas.starplayer.model.MusicRepoModel

@Dao
interface FavoriteMusicDao {

    @Query("select * from favorites")
    suspend fun getAllMusics(): MutableList<FavoriteMusic>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun delete(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun deleteFavoriteMusicsList(favoriteMusics: MutableList<FavoriteMusic>)

    @Update
    suspend fun update(favoriteMusic: FavoriteMusic)
}