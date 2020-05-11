package com.worldsnas.starplayer.di.components

import com.worldsnas.starplayer.di.FragmentScope
import com.worldsnas.starplayer.di.modules.DatabaseModule
import com.worldsnas.starplayer.di.modules.MusicProviderModule
import com.worldsnas.starplayer.di.modules.ViewModelModule
import com.worldsnas.starplayer.view.musics_list.MusicsListFragment
import dagger.Component


@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ViewModelModule::class, MusicProviderModule::class, DatabaseModule::class]
)
interface MusicListComponent {

    fun inject(musicsListFragment: MusicsListFragment)

    @Component.Builder
    interface Builder {
        fun build(): MusicListComponent


        fun appComponent(appComponent: AppComponent): Builder


    }
}