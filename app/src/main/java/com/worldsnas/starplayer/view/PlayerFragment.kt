package com.worldsnas.starplayer.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.worldsnas.starplayer.App
import com.worldsnas.starplayer.databinding.FragmentPlayerBinding
import com.worldsnas.starplayer.di.DaggerPlayerComponent
import com.worldsnas.starplayer.di.PlayerComponent
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class PlayerFragment : Fragment() {

    lateinit var playerComponent: PlayerComponent
    var exoPlayer: SimpleExoPlayer? = null
    var viewBinding: FragmentPlayerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentPlayerBinding.inflate(inflater, container, false)
        initializeExoplayer()
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerComponent = DaggerPlayerComponent.builder()
            .appComponent((activity!!.application as App).appComponent).build()
        playerComponent.inject(this)
    }

    private fun initializeExoplayer() {
        exoPlayer = SimpleExoPlayer.Builder(context!!).build()
        viewBinding?.exoControlView?.player = exoPlayer

        val filePath = Environment.getExternalStorageDirectory().path + "/Download/Temp/Test.mp3"
        val mediaSource = buildMediaSource(Uri.fromFile(File(filePath)))
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
}
