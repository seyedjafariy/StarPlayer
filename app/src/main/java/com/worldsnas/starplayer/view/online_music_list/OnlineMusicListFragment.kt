package com.worldsnas.starplayer.view.online_music_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.worldsnas.starplayer.ConstValues
import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.databinding.FragmentOnlineMusicsListBinding
import com.worldsnas.starplayer.di.DaggerOnlineMusicListComponent
import com.worldsnas.starplayer.di.OnlineMusicListComponent
import com.worldsnas.starplayer.model.LocalMusic
import com.worldsnas.starplayer.model.Music
import kotlinx.android.synthetic.main.fragment_online_musics_list.view.*
import javax.inject.Inject

/**
 * created by Roohandeh  4/29/2020
 */
class OnlineMusicListFragment : Fragment() {


    private val TAG = OnlineMusicListFragment::class.java.simpleName
    private var _binding: FragmentOnlineMusicsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var onlineMusicListComponent: OnlineMusicListComponent
    private val adapter =
        OnlineMusicListAdapter { item -> onlineMusicListListener(item) }

    @Inject
    lateinit var onlineMusicListViewModel: OnlineMusicListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnlineMusicsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDagger()
        binding.root.recycler_online_musics.adapter = adapter
        liveDataSetup()
    }

    private fun initDagger() {
        onlineMusicListComponent = DaggerOnlineMusicListComponent.create()
        onlineMusicListComponent.inject(this)

    }

    private fun onlineMusicListListener(obj: LocalMusic) {
        val bundle = bundleOf(ConstValues.BUNDLE_KEY_MUSIC_INFO to obj)

        findNavController().navigate(R.id.action_onlineMusicsListFragment_to_playerFragment, bundle)

    }

    private fun liveDataSetup() {

        val musicObserver = Observer<List<Music>> {

            val musics = it
            Log.d(TAG, it.toString())
            adapter.submitList(musics)
        }
        onlineMusicListViewModel.postLiveMusicList()?.observe(viewLifecycleOwner, musicObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
