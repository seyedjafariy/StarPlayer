package com.worldsnas.starplayer.view.musics_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.databinding.FragmentMusicsListBinding
import com.worldsnas.starplayer.model.Music

class MusicsListFragment : Fragment() {

    private var _binding: FragmentMusicsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicsListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val musics = ArrayList<Music>()
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        musics.add(Music(0, "Test", "Ali", "Single", "Pop"))
        binding.recyclerview.adapter = MusicsListAdapter(context!!, musics)
    }
}
