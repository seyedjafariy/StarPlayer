package com.worldsnas.starplayer.model.persistent


import androidx.room.*
import com.worldsnas.starplayer.model.MusicRepoModel

@Dao
interface FavoriteMusicDao {

    @Query("select * from favorites")
    suspend fun getAllMusics(): List<MusicRepoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun delete(musicRepoModel: FavoriteMusic)

    @Update
    suspend fun update(musicRepoModel: FavoriteMusic)
}