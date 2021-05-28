package com.worldsnas.starplayer.di.modules

import com.worldsnas.starplayer.di.qualifier.DefaultDispatcher
import com.worldsnas.starplayer.di.qualifier.IoDispatcher
import com.worldsnas.starplayer.di.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatcherModule {


    @DefaultDispatcher
    @JvmStatic
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @JvmStatic
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @JvmStatic
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
