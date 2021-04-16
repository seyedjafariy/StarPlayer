package com.worldsnas.starplayer.view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.worldsnas.starplayer.view.musics_list.MusicsListFragment

class PagerFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        val ff = FragmentFactoryImpl()
        val classloader = ClassLoader.getSystemClassLoader()
        return when (position) {
            0 -> ff.instantiate(classloader, MusicsListFragment::class.java.name)
            1 -> ff.instantiate(classloader, OnlineListFragment::class.java.name)
            else -> ff.instantiate(classloader, FavoritesFragment::class.java.name)
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

}