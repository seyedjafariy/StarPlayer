package com.worldsnas.starplayer.view.musics_list

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.ConstValues
import com.worldsnas.starplayer.NavDirections
import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.di.components.DaggerMusicListComponent
import com.worldsnas.starplayer.di.components.MusicListComponent
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.model.persistent.FavoriteMusic
import com.worldsnas.starplayer.view.ViewModelFactory
import javax.inject.Inject


class MusicsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val musicListViewModel by
    viewModels<MusicListViewModel> { viewModelFactory }
    private val musicListAdapter =
        MusicsListAdapter({ music -> onItemClickListener(music) }) { musicRepoModel ->
            onFavoriteClickListener(musicRepoModel)
        }

    private var _binding: FragmentMusicsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var musicListComponent: MusicListComponent
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
        storagePermissionCheck()

        binding.recyclerview.adapter = musicListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun liveDataSetup() {

        val musicObserver = Observer<List<MusicRepoModel>> {

            val musics = it
            Log.d("tag", it.toString())
            musicListAdapter.submitList(musics)
        }
        musicListViewModel.postMusic().observe(viewLifecycleOwner, musicObserver)
    }

    private fun daggerSetup() {

        musicListComponent = DaggerMusicListComponent.builder()
            .appComponent((activity!!.application as App).appComponent)
            .build()
        musicListComponent.inject(this)
    }

    private fun storagePermissionCheck() {
        if (checkSelfPermission()) {
            Toast.makeText(context, "Already Granted", Toast.LENGTH_SHORT).show()
            liveDataSetup()
        } else
            requestPermission()
    }

    private fun checkSelfPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            context!!,
            READ_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(READ_EXTERNAL_STORAGE),
            ConstValues.READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == ConstValues.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                liveDataSetup()

            } else {
                // disable function did not granted
                Toast.makeText(context, "Need Permission to Access Songs", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun onItemClickListener(music: Music) {
        val action = NavDirections.actionGlobalPlayerFragment(music)

        findNavController().navigate(action)

//        val bundle = bundleOf("music" to music)
//        findNavController().navigate(R.id.action_global_playerFragment, bundle)
    }

    private fun onFavoriteClickListener(musicRepoModel: MusicRepoModel) {
        musicListViewModel.favoritesHandler(musicRepoModel)
    }

}

