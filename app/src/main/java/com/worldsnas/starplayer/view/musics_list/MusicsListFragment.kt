package com.worldsnas.starplayer.view.musics_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.di.DaggerMusicListComponent
import com.worldsnas.starplayer.di.MusicListComponent
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.view.ViewModelFactory
import javax.inject.Inject

class MusicsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val musicListViewModel by lazy {
        viewModelFactory.create(MusicListViewModel::class.java)
    }
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

        val musicObserver = Observer<List<Music>> {

            binding.recyclerview.adapter = MusicsListAdapter(it as ArrayList<Music>)
        }
        musicListViewModel.getAllMusic().observe(viewLifecycleOwner, musicObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun daggerSetup() {

        musicListComponent = DaggerMusicListComponent.builder()
            .appComponent((activity!!.application as App).appComponent).contextProvider(context!!)
            .build()
        musicListComponent.inject(this)
    }
}
