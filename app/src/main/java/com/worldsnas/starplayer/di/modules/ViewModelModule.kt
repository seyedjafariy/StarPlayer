package com.worldsnas.starplayer.di.modules


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.worldsnas.starplayer.di.ViewModelKey
import com.worldsnas.starplayer.view.FavoritesViewModel
import com.worldsnas.starplayer.view.OnlineListViewModel
import com.worldsnas.starplayer.view.ViewModelFactory
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MusicListViewModel::class)
    abstract fun bindMusicListViewModel(viewModel: MusicListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnlineListViewModel::class)
    abstract fun bindOnlineListViewModel(viewModel: OnlineListViewModel): ViewModel
}