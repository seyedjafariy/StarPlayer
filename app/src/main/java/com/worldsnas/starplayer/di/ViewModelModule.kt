package com.worldsnas.starplayer.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.worldsnas.starplayer.view.ViewModelFactory
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
import com.worldsnas.starplayer.view.online_music_list.OnlineMusicListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MusicListViewModel::class)
    abstract fun bindMusicListViewModel(viewModel : MusicListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OnlineMusicListViewModel::class)
    abstract fun bindOnlineMusicListViewModel(viewModel:OnlineMusicListViewModel):ViewModel
}