package com.worldsnas.starplayer

import android.app.Application
import com.worldsnas.starplayer.di.*

class MainApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var mainApplicationComponent: MainApplicationComponent
        lateinit var playerFragmentComponent: PlayerFragmentComponent
    }

    override fun onCreate() {
        super.onCreate()
        mainApplicationComponent =
            DaggerMainApplicationComponent.builder().androidmodule(Androidmodule(this)).build()
        playerFragmentComponent =
            DaggerPlayerFragmentComponent.builder().androidmodule(Androidmodule(this)).build()
    }
}