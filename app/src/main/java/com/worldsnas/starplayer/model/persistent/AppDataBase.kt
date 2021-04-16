package com.worldsnas.starplayer.model.persistent

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMusic::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteMusicDao(): FavoriteMusicDao
}