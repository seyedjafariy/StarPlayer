package com.worldsnas.starplayer.di.module

import com.squareup.moshi.Moshi
import com.worldsnas.starplayer.model.api.utils.AppJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @JvmStatic
    @Reusable
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            //TODO insert Base URL here.
            .baseUrl("")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @JvmStatic
    @Provides
    fun provideTypeAdapter(): Moshi {
        val builder = Moshi.Builder()
        builder.add(AppJsonAdapterFactory.INSTANCE)
        return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @JvmStatic
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

//    @JvmStatic
//    @Provides
//    fun provideCache(application: Application): Cache {
//        val cacheSize: Long = 10 * 1024 * 1024
//        return Cache(application.cacheDir, cacheSize)
//    }
}