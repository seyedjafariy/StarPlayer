package com.worldsnas.starplayer.di

import com.worldsnas.starplayer.view.musics_list.MusicsListFragment
import dagger.Component


@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ViewModelModule::class, MusicProviderModule::class]
)
interface MusicListComponent {

    fun inject(musicsListFragment: MusicsListFragment)

    @Component.Builder
    interface Builder {
        fun build(): MusicListComponent


        fun appComponent(appComponent: AppComponent): Builder


    }
}