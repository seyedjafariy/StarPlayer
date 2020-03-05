package com.worldsnas.starplayer.di

import android.app.Application
import android.content.Context
import dagger.Module
import javax.inject.Singleton

@Module
@Singleton

class Androidmodule (private val application: Application){

    fun provideApplicationContext():Context = application

}