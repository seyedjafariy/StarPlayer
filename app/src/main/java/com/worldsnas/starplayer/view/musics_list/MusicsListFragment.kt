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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.di.DaggerMusicListComponent
import com.worldsnas.starplayer.di.MusicListComponent
import com.worldsnas.starplayer.model.Music
import com.worldsnas.starplayer.view.ViewModelFactory
import javax.inject.Inject


class MusicsListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val musicListViewModel by
    viewModels<MusicListViewModel> { viewModelFactory }

    private val READ_EXTERNAL_STORAGE_REQUEST_CODE = 100

    private val musicListAdapter by lazy {
        MusicsListAdapter()
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
        provideMusic()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun liveDataSetup() {

        val musicObserver = Observer<List<Music>> {

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
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(READ_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
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

    private fun provideMusic() {

        musicListAdapter.onItemClick = {
            val bundle = Bundle()

            bundle.putParcelable("musicInfo", it)

            _binding!!.root.findNavController()
                .navigate(R.id.action_musicsListFragment_to_playerFragment, bundle)

//            Toast.makeText(context, "$it", Toast.LENGTH_SHORT).show()
        }


    }

}

