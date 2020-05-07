package com.worldsnas.starplayer.di.modules

import com.worldsnas.starplayer.api.WebServiceApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object EndPointModule {

    @JvmStatic
    @Provides
    fun provideWebServiceApi(retrofit: Retrofit): WebServiceApi {
        return retrofit.create(WebServiceApi::class.java)
    }
}