package com.worldsnas.starplayer

import android.app.Application
import com.worldsnas.starplayer.di.Androidmodule
import com.worldsnas.starplayer.di.DaggerMainApplicationComponent
import com.worldsnas.starplayer.di.MainApplicationComponent

class MainApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var mainApplicationComponent: MainApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        mainApplicationComponent =
            DaggerMainApplicationComponent.builder().androidmodule(Androidmodule(this)).build()
    }
}