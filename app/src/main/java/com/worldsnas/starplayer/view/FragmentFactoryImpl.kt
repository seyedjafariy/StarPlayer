package com.worldsnas.starplayer.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.worldsnas.starplayer.view.musics_list.MusicsListFragment

class FragmentFactoryImpl : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {

            OnlineListFragment::class.java.name -> OnlineListFragment()
            MusicsListFragment::class.java.name -> MusicsListFragment()
            FavoritesFragment::class.java.name -> FavoritesFragment()

            else -> super.instantiate(classLoader, className)
        }
    }
}