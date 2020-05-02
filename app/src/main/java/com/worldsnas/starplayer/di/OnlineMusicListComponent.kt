package com.worldsnas.starplayer.di

import com.worldsnas.starplayer.view.online_music_list.OnlineMusicListFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ ViewModelModule::class,RepositoryModule::class,MusicProviderModule::class]
)
interface OnlineMusicListComponent {

    fun inject(onlineMusicListFragment: OnlineMusicListFragment)

    @Component.Builder
    interface Builder {
        fun build(): OnlineMusicListComponent


        fun appComponent(appComponent: AppComponent): Builder


    }
}