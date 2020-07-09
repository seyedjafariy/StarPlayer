package com.worldsnas.starplayer.di.components

import com.worldsnas.starplayer.di.FragmentScope
import com.worldsnas.starplayer.di.modules.MusicProviderModule
import com.worldsnas.starplayer.di.modules.ViewModelModule
import com.worldsnas.starplayer.view.FavoritesFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ViewModelModule::class, MusicProviderModule::class]
)
interface FavoritesComponent
    {

        fun inject(favoritesFragment: FavoritesFragment)

        @Component.Builder
        interface Builder {
            fun build(): FavoritesComponent


            fun appComponent(appComponent: AppComponent): Builder


        }
}