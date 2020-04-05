package com.worldsnas.starplayer.view.musics_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.worldsnas.starplayer.App

import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.di.DaggerMusicListComponent
import com.worldsnas.starplayer.di.MusicListComponent
import com.worldsnas.starplayer.model.Music

class MusicsListFragment : Fragment() {

    private var _binding: FragmentMusicsListBinding? = null
    private val binding get() = _binding!!
    lateinit var musicListComponent: MusicListComponent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicsListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daggerSetup()

        val musics = ArrayList<Music>()
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop","somewhere"))
        binding.recyclerview.adapter = MusicsListAdapter(musics)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun daggerSetup() {

        musicListComponent = DaggerMusicListComponent.builder()
            .appComponent((activity!!.application as App).appComponent).build()
        musicListComponent.inject(this)
    }
}
