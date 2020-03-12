package com.worldsnas.starplayer.di.component

import android.app.Application
import com.worldsnas.starplayer.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application):
                Builder

        fun build(): AppComponent
    }
}