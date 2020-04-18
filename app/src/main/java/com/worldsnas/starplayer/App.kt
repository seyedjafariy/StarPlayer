package com.worldsnas.starplayer

import android.app.Application
import com.worldsnas.starplayer.di.components.AppComponent
import com.worldsnas.starplayer.di.components.DaggerAppComponent


class App  :Application(){

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
    }
}