package com.worldsnas.starplayer.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import com.worldsnas.starplayer.App
import dagger.Module
import dagger.Provides

@Module
class LocalMusicModule {


    @Provides
    fun provideContentResolver(context: Context): ContentResolver {
        return context.contentResolver
    }

}