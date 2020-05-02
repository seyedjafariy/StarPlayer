package com.worldsnas.starplayer.di

import com.worldsnas.starplayer.IRepository
import com.worldsnas.starplayer.RepositoryImp
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @JvmStatic
    @Provides
    fun provideRepository(): IRepository {
        return RepositoryImp()
    }
}