package com.worldsnas.starplayer.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.databinding.FragmentPlayerBinding
import com.worldsnas.starplayer.di.DaggerPlayerComponent
import com.worldsnas.starplayer.di.PlayerComponent

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    lateinit var playerComponent: PlayerComponent
    lateinit var exoPlayer: SimpleExoPlayer
    lateinit var viewBinding: FragmentPlayerBinding

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentPlayerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerComponent = DaggerPlayerComponent.builder()
            .appComponent((activity!!.application as App).appComponent).build()
        playerComponent.inject(this)

        initializeExoplayer()
    }

    private fun initializeExoplayer() {
        exoPlayer = SimpleExoPlayer.Builder(context!!).build()
        viewBinding.exoControlView.player = exoPlayer

        val mediaSource = buildMediaSource(Uri.parse("asset:///take_your_time.mp3"))
        exoPlayer.playWhenReady = playWhenReady
        exoPlayer.seekTo(currentWindow, playbackPosition)
        exoPlayer.prepare(mediaSource, false, false)
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(context, "Star-player")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)
    }

    private fun releaseExoPlayer() {
        playWhenReady = exoPlayer.playWhenReady
        currentWindow = exoPlayer.currentWindowIndex
        playbackPosition = exoPlayer.currentPosition
        exoPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseExoPlayer()
    }
}
