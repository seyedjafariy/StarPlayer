package com.worldsnas.starplayer.di

import android.app.Application
import com.worldsnas.starplayer.MainActivity
import dagger.Component

@Component(modules = [Androidmodule::class])
interface MainApplicationComponent {
    fun inject (context: MainActivity)
}