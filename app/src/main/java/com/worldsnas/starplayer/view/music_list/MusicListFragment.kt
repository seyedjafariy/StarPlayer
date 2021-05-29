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
import com.worldsnas.starplayer.utils.Resource
import com.worldsnas.starplayer.utils.showToast
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

        val musicObserver = Observer<Resource<List<MusicRepoModel>>> { response ->

            when (response.status) {
                Resource.Status.LOADING -> {
                    binding.progressMusicList.visibility = View.VISIBLE
                    binding.recyclerview.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.progressMusicList.visibility = View.GONE
                    binding.recyclerview.visibility = View.VISIBLE
                    musicListAdapter.submitList(response.data)
                    initMusicsForExoPlayerService(response.data)
                }
                Resource.Status.ERROR -> {
                    binding.recyclerview.visibility = View.GONE
                    binding.progressMusicList.visibility = View.GONE
                    showToast(response.message)

                }

            }


        }
        musicListViewModel.localMusicList.observe(viewLifecycleOwner, musicObserver)

    }

    private fun initMusicsForExoPlayerService(data: List<MusicRepoModel>?) {
        musicList = data?.map {
            Music(it.id, it.title, it.artist, it.album, it.genre, it.address)
        } as ArrayList<Music>
    }


    private fun daggerSetup() {

        musicListComponent = DaggerMusicListComponent.builder()
            .appComponent((activity!!.application as App).appComponent)
            .build()
        musicListComponent.inject(this)
    }

    private fun storagePermissionCheck() {
        if (checkSelfPermission()) {
            showToast("Already Granted")
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
                    showToast("Need Permission to Access Songs")

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

