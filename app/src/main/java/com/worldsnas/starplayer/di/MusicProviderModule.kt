package com.worldsnas.starplayer.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.model.LocalMusicProvider
import com.worldsnas.starplayer.model.LocalMusicProviderImpl
import dagger.Module
import dagger.Provides

@Module
object MusicProviderModule {

    @JvmStatic
    @Provides
    fun provideContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }

    @JvmStatic
    @Provides
    fun provideLocalMusicImpl(application: Application): LocalMusicProvider {
        return LocalMusicProviderImpl(application.contentResolver)
    }

}