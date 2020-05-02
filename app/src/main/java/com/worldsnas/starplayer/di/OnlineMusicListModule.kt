package com.worldsnas.starplayer.di

import androidx.lifecycle.ViewModel
import com.worldsnas.starplayer.IRepository
import com.worldsnas.starplayer.view.online_music_list.OnlineMusicListViewModel
import dagger.Module
import dagger.Provides

@Module
class  OnlineMusicListModule {

    @Provides
     fun provideOnlineViewModelImp(repository:IRepository): ViewModel{
        return OnlineMusicListViewModel(repository)
    }


}