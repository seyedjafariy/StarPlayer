package com.worldsnas.starplayer.model.persistent


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMusicDao {

    @Query("select * from favorites")
    fun getAllFavorites(): Flow<List<FavoriteMusic>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun delete(favoriteMusic: FavoriteMusic)

    @Delete
    suspend fun deleteFavoriteMusicsList(favoriteMusics: MutableList<FavoriteMusic>)

    @Update
    suspend fun update(favoriteMusic: FavoriteMusic)
}