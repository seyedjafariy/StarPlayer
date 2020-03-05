package com.worldsnas.starplayer.di

import com.worldsnas.starplayer.view.PlayerFragment
import dagger.Component

@Component (modules = [Androidmodule::class])
interface PlayerFragmentComponent {

    fun inject (playerFragment: PlayerFragment)
}