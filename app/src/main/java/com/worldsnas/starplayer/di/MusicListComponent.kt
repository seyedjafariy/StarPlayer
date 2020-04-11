package com.worldsnas.starplayer.di

import android.app.Application
import android.content.Context
import com.worldsnas.starplayer.view.musics_list.MusicsListFragment
import dagger.BindsInstance
import dagger.Component


@FragmentScope
@Component(dependencies = [AppComponent::class], modules = [ViewModelModule::class , LocalMusicModule::class])
interface MusicListComponent {

    fun inject(musicsListFragment: MusicsListFragment)

    @Component.Builder
    interface Builder {
        fun build(): MusicListComponent

        fun appComponent(appComponent: AppComponent): Builder
        @BindsInstance
        fun contextProvider(context: Context): Builder
    }
}