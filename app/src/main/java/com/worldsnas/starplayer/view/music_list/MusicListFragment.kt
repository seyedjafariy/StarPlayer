package com.worldsnas.starplayer.view.music_list

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.ConstValues
import com.worldsnas.starplayer.ExoPlayerService
import com.worldsnas.starplayer.databinding.FragmentMusicListBinding
import com.worldsnas.starplayer.di.components.DaggerMusicListComponent
import com.worldsnas.starplayer.di.components.MusicListComponent
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.model.MusicRepoModel
import com.worldsnas.starplayer.view.ViewModelFactory
import javax.inject.Inject


class MusicListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    private val musicListViewModel by
    viewModels<MusicListViewModel> { viewModelFactory }

    private lateinit var musicList: ArrayList<Music>

    private val musicListAdapter =
        MusicListAdapter { item -> startExoplayerService(item, musicList) }

    private var _binding: FragmentMusicListBinding? = null
    private val binding get() = _binding!!
    private lateinit var musicListComponent: MusicListComponent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daggerSetup()
        storagePermissionCheck()

        binding.recyclerview.adapter = musicListAdapter

        musicListViewModel.getMusics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun liveDataSetup() {

        val musicObserver = Observer<List<MusicRepoModel>> { musics ->
            musicList = musics.map {
                Music(it.id, it.title, it.artist, it.album, it.genre, it.address)
            } as ArrayList<Music>
            val musics = musics
            Log.d("tag", musics.toString())
            musicListAdapter.submitList(musics)
        }
        musicListViewModel.musicList.observe(viewLifecycleOwner, musicObserver)
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
        ActivityCompat.requestPermissions(
            activity!!,
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

        when (requestCode) {
            ConstValues.READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    liveDataSetup()

                } else {
                    // disable function did not granted
                    Toast.makeText(context, "Need Permission to Access Songs", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else -> return
        }
    }

    private fun startExoplayerService(music: Music) {
//        val action = MusicsListFragmentDirections.actionMusicsListFragmentToPlayerFragment(music)
//
//        findNavController().navigate(action)

        ExoPlayerService.actionStart(context!!, music)
    }

    private fun startExoplayerService(music: Music, musicList: ArrayList<Music>) {

        ExoPlayerService.actionStart(context!!, musicList, musicList.indexOf(music))
    }


}

