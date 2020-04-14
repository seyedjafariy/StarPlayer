package com.worldsnas.starplayer.di


import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.worldsnas.starplayer.view.ViewModelFactory
import com.worldsnas.starplayer.view.musics_list.MusicListViewModel
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
}