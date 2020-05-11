package com.worldsnas.starplayer.model.persistent

import androidx.room.RoomDatabase

abstract class AppDataBase : RoomDatabase() {
    abstract fun favoriteMusicDao(): FavoriteMusicDao
}