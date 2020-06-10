package com.worldsnas.starplayer.di.components

import android.app.Application
import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.di.modules.DatabaseModule
import com.worldsnas.starplayer.di.modules.EndPointModule
import com.worldsnas.starplayer.di.modules.NetworkModule
import com.worldsnas.starplayer.model.persistent.AppDataBase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, EndPointModule::class
                        , DatabaseModule::class])
@Singleton
interface AppComponent {

    fun provideApplication(): Application
    fun provideWebserviceApi(): WebServiceApi
    fun provideAppDataBase(): AppDataBase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}