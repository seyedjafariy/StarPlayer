package com.worldsnas.starplayer.di

import com.worldsnas.starplayer.view.online_music_list.OnlineMusicListFragment
import dagger.Component

@FragmentScope
@Component(modules = [OnlineMusicListModule::class])
interface OnlineMusicListComponent {

    fun inject(onlineMusicListFragment: OnlineMusicListFragment)

}