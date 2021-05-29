package com.worldsnas.starplayer.di.components

import android.app.Application
import com.worldsnas.starplayer.api.WebServiceApi
import com.worldsnas.starplayer.di.modules.DispatcherModule
import com.worldsnas.starplayer.di.modules.EndPointModule
import com.worldsnas.starplayer.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, EndPointModule::class])
@Singleton
interface AppComponent {

    fun provideApplication(): Application
    fun provideWebserviceApi(): WebServiceApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}