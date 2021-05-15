package com.worldsnas.starplayer.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.ConstValues
import com.worldsnas.starplayer.databinding.FragmentPlayerBinding
import com.worldsnas.starplayer.di.components.DaggerPlayerComponent
import com.worldsnas.starplayer.di.components.PlayerComponent
import com.worldsnas.starplayer.model.Music

/**
 * Fragment to Play Musics
 */
class PlayerFragment : Fragment() {

    private lateinit var playerComponent: PlayerComponent
    private var exoPlayer: SimpleExoPlayer? = null
    private var viewBinding: FragmentPlayerBinding? = null
    private lateinit var musicArgs: Music
    private val args: PlayerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentPlayerBinding.inflate(inflater, container, false)
        setMusicArguments()
        initializeExoplayer()

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        daggerSetup()
    }

    private fun initializeExoplayer() {
        exoPlayer = SimpleExoPlayer.Builder(context!!).build()
        viewBinding?.exoControlView?.player = exoPlayer

        val filePath = ConstValues.PRE_ADDRESS_VOLUME + musicArgs.address
        val mediaSource = buildMediaSource(Uri.parse(filePath))
        exoPlayer?.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context!!, "Star-player")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    private fun releaseExoPlayer() {
        exoPlayer!!.release()
        exoPlayer = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releaseExoPlayer()
        viewBinding = null
    }

    private fun setMusicArguments() {
        musicArgs = args.music

        viewBinding?.tvMusicTitle?.text = musicArgs.title
        viewBinding?.tvMusicArtist?.text = musicArgs.artist
    }

    private fun daggerSetup() {
        playerComponent = DaggerPlayerComponent.builder()
            .appComponent((activity!!.application as App).appComponent).build()
        playerComponent.inject(this)
    }


}
