package com.worldsnas.starplayer.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.NavDirections
import com.worldsnas.starplayer.databinding.FragmentFavoritesBinding
import com.worldsnas.starplayer.di.components.DaggerFavoritesComponent
import com.worldsnas.starplayer.di.components.FavoritesComponent
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.view.musics_list.MusicsListAdapter
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val favoritesViewModel by
    viewModels<FavoritesViewModel> { viewModelFactory }
    private lateinit var favoritesComponent: FavoritesComponent
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val musicListAdapter =
        MusicsListAdapter({ music -> onItemClickListener(music) }) { musicRepoModel ->
            onFavoriteClickListener(musicRepoModel)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerSetup()
        liveDataSetup()
        binding.favoritesRecyclerview.adapter = musicListAdapter

    }

    private fun daggerSetup() {

        favoritesComponent = DaggerFavoritesComponent.builder()
            .appComponent((activity!!.application as App).appComponent)
            .build()
        favoritesComponent.inject(this)
    }

    private fun liveDataSetup() {

        val musicObserver = Observer<List<MusicRepoModel>> {

            val musics = it
            Log.d("tag", it.toString())
            musicListAdapter.submitList(musics)
        }
        favoritesViewModel.postMusic().observe(viewLifecycleOwner, musicObserver)
    }

    private fun onItemClickListener(music: Music) {
        val action = NavDirections.actionGlobalPlayerFragment(music)

        findNavController().navigate(action)
    }

    private fun onFavoriteClickListener(musicRepoModel: MusicRepoModel) {
        favoritesViewModel.favoritesHandler(musicRepoModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}