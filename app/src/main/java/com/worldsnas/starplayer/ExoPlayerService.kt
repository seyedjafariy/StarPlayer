package com.worldsnas.starplayer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.worldsnas.starplayer.model.Music
import java.util.*

class ExoPlayerService : Service(), Player.EventListener {


    lateinit var mediaSource: ConcatenatingMediaSource

    lateinit var player: SimpleExoPlayer

    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var musicList: ArrayList<Music?>? = null
    private var currentMusicId: Int = 0


    private fun reInit() {
        playbackPosition = 0
        prepareCurrentWindow(musicList, currentMusicId)
        initializePlayer()
    }


    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }


    private fun play(trackModel: Music?) {
        val trackModels = ArrayList<Music?>()
        trackModels.add(trackModel)
        play(trackModels)
    }

    private fun play(trackModels: ArrayList<Music?>?) {
        reInit()
        if (trackModels != null) {
            val mediaSources =
                arrayOfNulls<MediaSource>(trackModels.size)
            for (i in trackModels.indices) {
                mediaSources[i] =
                    buildMediaSource(Uri.parse(trackModels[i]?.address))
            }
            mediaSource = ConcatenatingMediaSource(*mediaSources)
            player.prepare(mediaSource)
            startPlayer()
        }
    }

    private fun prepareCurrentWindow(trackModels: ArrayList<Music?>?, currentMusicId: Int) {

        if (trackModels != null && trackModels.size > 0)
            for (i in trackModels.indices) {
                if (trackModels[i]?.id == currentMusicId)
                    currentWindow = i
            }
    }


    private fun pausePlayer() {
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.playWhenReady = false
        showCurrentTrackNotification(musicList?.get(currentWindow))
    }

    private fun startPlayer() {
        player.seekTo(currentWindow, playbackPosition)
        player.playWhenReady = true
        showCurrentTrackNotification(musicList?.get(currentWindow))
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        player.setAudioAttributes(
            com.google.android.exoplayer2.audio.AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_SPEECH)
                .build(), true
        )

        player.seekTo(currentWindow, playbackPosition)
        player.playWhenReady = true
        player.addListener(this)

    }

    private fun buildMediaSource(uri: Uri?): MediaSource? {
        val userAgent = "star_player"
        return ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(uri)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
//            initializePlayer()
            if (intent.action != null) when (intent.action) {
                ACTION.PLAY_ACTION -> {
                    if (player.currentPosition == player.contentPosition && player.nextWindowIndex == C.INDEX_UNSET) {
                        playbackPosition = 0
                    }
                    startPlayer()
                }
                ACTION.PAUSE_ACTION -> pausePlayer()
                ACTION.START_TRACKS_ARRAY_ACTION -> {
                    musicList = intent.getParcelableArrayListExtra(
                        TAG
                    )
                    currentMusicId = intent.getIntExtra("currentMusicId", 0)
                    play(
                        musicList
                    )
                }
                ACTION.START_TRACK_ACTION -> play(
                    intent.getParcelableExtra<Music>(
                        TAG
                    )
                )

                ACTION.PREV_ACTION -> {
                    val prev = player.previousWindowIndex
                    if (prev >= 0 && prev <= mediaSource.size) {
                        currentWindow = prev
                    } else {
                        currentMusicId = 0
                        currentWindow = currentMusicId
                        playbackPosition = 0
                    }
                    startPlayer()
                }

                ACTION.NEXT_ACTION -> {
                    val next = player.nextWindowIndex
                    if (next >= 0 && next < mediaSource.size) {
                        currentWindow = next
                    } else {
                        currentMusicId = 0
                        currentWindow = currentMusicId
                        playbackPosition = 0

                    }
                    startPlayer()
                }
                ACTION.STOP_ACTION -> stopAction()
            }
        }
        return START_STICKY
    }


    private fun stopAction() {
        releasePlayer()
        stopForeground(true)
        closeNotification()
        stopSelf()
    }

    private fun closeNotification() {
        try {
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(
                NOTIFICATION_ID.FOREGROUND_SERVICE
            )
        } catch (n: NullPointerException) {
        }
    }

    private fun showCurrentTrackNotification(music: Music?): Music? {
        if (music == null) {
            closeNotification()
        } else {
            val pendingIntent = PendingIntent.getActivity(
                this@ExoPlayerService,
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                Intent(this@ExoPlayerService, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notificationBuilder =
                NotificationCompat.Builder(applicationContext)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle(if (TextUtils.isEmpty(music.title)) music.artist else music.title)
                    .setContentText(music.artist)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .setStyle(
                        androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1, 2, 3)
                    )
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setWhen(0)
            val pendingIntentPrev = PendingIntent.getService(
                this,
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                generatePrevIntent(this),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.exo_notification_previous,
                    "قبلی",
                    pendingIntentPrev
                ).build()
            )
            if (player.playWhenReady) {
                val pendingIntentPause = PendingIntent.getService(
                    this,
                    NOTIFICATION_ID.FOREGROUND_SERVICE,
                    generatePauseIntent(this),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                notificationBuilder.addAction(
                    NotificationCompat.Action.Builder(
                        R.drawable.exo_notification_pause,
                        "توقف",
                        pendingIntentPause
                    ).build()
                )
            } else {
                val pendingIntentPlay = PendingIntent.getService(
                    this,
                    NOTIFICATION_ID.FOREGROUND_SERVICE,
                    generatePlayIntent(this),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                notificationBuilder.addAction(
                    NotificationCompat.Action.Builder(
                        R.drawable.exo_notification_play,
                        "اجرا",
                        pendingIntentPlay
                    ).build()
                )
            }
            val pendingIntentNext = PendingIntent.getService(
                this,
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                generateNextIntent(this),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.exo_notification_next,
                    "بعدی",
                    pendingIntentNext
                ).build()
            )
            val pendingIntentStop = PendingIntent.getService(
                this,
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                generateStopIntent(this),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.exo_notification_stop,
                    "بستن",
                    pendingIntentStop
                ).build()
            )
            startForeground(
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                notificationBuilder.build()
            )
        }
        return music
    }

    private fun releasePlayer() {
//        if (player != null) {
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.stop()
        player.release()
//            player = null
//        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLoadingChanged(isLoading: Boolean) {}
    override fun onPlayerStateChanged(
        playWhenReady: Boolean,
        playbackState: Int
    ) {
        when (playbackState) {
            Player.STATE_BUFFERING -> {
            }
            Player.STATE_ENDED -> {
                player.seekTo(
                    player.currentWindowIndex,
                    0
                )
                pausePlayer()
            }

            Player.STATE_READY -> {
               if (!playWhenReady)
                   showCurrentTrackNotification(musicList?.get(player.currentWindowIndex))
            }

        }

    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray,
        trackSelections: TrackSelectionArray
    ) {
        super.onTracksChanged(trackGroups, trackSelections)
        showCurrentTrackNotification(musicList?.get(player.currentWindowIndex))
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        pausePlayer()
        player.prepare(mediaSource!!)
        player.seekTo(currentWindow, playbackPosition)
//        broadCastCurrentTrack()
//        sendBroadcast_stateIdle()
    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
    override fun onSeekProcessed() {}
    interface ACTION {
        companion object {
            const val PREV_ACTION = "action.prev"
            const val PLAY_ACTION = "action.play"
            const val PAUSE_ACTION = "action.pause"
            const val NEXT_ACTION = "action.next"
            const val START_TRACK_ACTION = "action.start.track"
            const val START_TRACKS_ARRAY_ACTION = "action.start.tracksArray"
            const val STOP_ACTION = "action.stop"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            const val FOREGROUND_SERVICE = 101
        }
    }

    private fun generateStopIntent(context: Context?): Intent {
        val serviceIntent = Intent(context, ExoPlayerService::class.java)
        serviceIntent.action = ACTION.STOP_ACTION
        return serviceIntent
    }


    private fun generateNextIntent(context: Context?): Intent {
        val nextIntent = Intent(context, ExoPlayerService::class.java)
        nextIntent.action = ACTION.NEXT_ACTION
        return nextIntent
    }


    private fun generatePrevIntent(context: Context?): Intent {
        val prevIntent = Intent(context, ExoPlayerService::class.java)
        prevIntent.action = ACTION.PREV_ACTION
        return prevIntent
    }


    private fun generatePlayIntent(context: Context?): Intent {
        val playIntent = Intent(context, ExoPlayerService::class.java)
        playIntent.action = ACTION.PLAY_ACTION
        return playIntent
    }

    private fun generatePauseIntent(context: Context?): Intent {
        val pauseIntent = Intent(context, ExoPlayerService::class.java)
        pauseIntent.action = ACTION.PAUSE_ACTION
        return pauseIntent
    }

    companion object {
        const val TAG = "PlayerService"

        fun actionStart(
            context: Context?,
            arr: ArrayList<Music>?,
            currentMusicId: Int
        ) {
            if (context == null || arr == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.START_TRACKS_ARRAY_ACTION
            serviceIntent.putParcelableArrayListExtra(TAG, arr)
            serviceIntent.putExtra("currentMusicId", currentMusicId)
            callService(context, serviceIntent)
        }

        private fun callService(context: Context, intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }


    }


}