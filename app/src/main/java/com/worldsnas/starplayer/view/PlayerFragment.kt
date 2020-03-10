package com.worldsnas.starplayer.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.worldsnas.starplayer.R
import com.worldsnas.starplayer.di.DaggerPlayerFragmentComponent
import com.worldsnas.starplayer.di.PlayerFragmentComponent

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    lateinit var playerFragmentComponent: PlayerFragmentComponent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerFragmentComponent = DaggerPlayerFragmentComponent.builder().build()

    }
}
