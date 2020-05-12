package com.worldsnas.starplayer.di.modules

import android.app.Application
import androidx.room.Room
import com.worldsnas.starplayer.model.persistent.AppDataBase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    @JvmStatic
    fun provideRoom(application: Application) =
        Room.databaseBuilder(application, AppDataBase::class.java, "starDb").build()

    @Provides
    @JvmStatic
    fun provideDao(dataBase: AppDataBase) = dataBase.favoriteMusicDao()

}