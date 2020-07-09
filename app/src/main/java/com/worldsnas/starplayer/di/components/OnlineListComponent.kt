package com.worldsnas.starplayer.di.components

import com.worldsnas.starplayer.di.FragmentScope
import com.worldsnas.starplayer.di.modules.MusicProviderModule
import com.worldsnas.starplayer.di.modules.ViewModelModule
import com.worldsnas.starplayer.view.OnlineListFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [ViewModelModule::class, MusicProviderModule::class]
)
interface OnlineListComponent {


    fun inject(onlineListFragment: OnlineListFragment)

    @Component.Builder
    interface Builder {
        fun build(): OnlineListComponent


        fun appComponent(appComponent: AppComponent): Builder


    }
}