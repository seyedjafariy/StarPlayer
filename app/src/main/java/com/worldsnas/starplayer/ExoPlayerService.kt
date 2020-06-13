package com.worldsnas.starplayer

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.text.TextUtils
import androidx.annotation.RequiresApi
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

    private var playbackPosition: Long = 0
    private lateinit var musicList: ArrayList<Music>
    private var currentMusicPosition: Int = 0


    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }


    private fun play(trackModel: Music) {
        musicList = ArrayList<Music>()
        musicList.add(trackModel)
        play(musicList)
    }

    private fun play(trackModels: ArrayList<Music>) {
        playbackPosition = 0
        reInitPlayer()
        if (trackModels.size > 0) {
            val mediaSources =
                arrayOfNulls<MediaSource>(trackModels.size)
            for (i in trackModels.indices) {
                mediaSources[i] =
                    buildMediaSource(Uri.parse(trackModels[i].address))
            }
            mediaSource = ConcatenatingMediaSource(*mediaSources)
            player.prepare(mediaSource)
            startPlayer()
        }
    }

    private fun reInitPlayer() {
        if (player.isPlaying) {
        releasePlayer()
        initializePlayer()
        }
    }


    private fun pausePlayer() {
        playbackPosition = player.currentPosition
        currentMusicPosition = player.currentWindowIndex
        player.playWhenReady = false
        showCurrentTrackNotification(musicList[player.currentWindowIndex])
    }

    private fun startPlayer() {
        player.seekTo(currentMusicPosition, playbackPosition)
        player.playWhenReady = true
        showCurrentTrackNotification(musicList[player.currentWindowIndex])
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        player.setAudioAttributes(
            com.google.android.exoplayer2.audio.AudioAttributes.Builder()
                .setUsage(C.USAGE_MEDIA)
                .setContentType(C.CONTENT_TYPE_SPEECH)
                .build(), true
        )


        player.addListener(this)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = "star_player"
        return ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(uri)
    }

    override fun onCreate() {
        super.onCreate()
        initializePlayer()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        when (intent.action) {
            PLAY_ACTION -> {

                startPlayer()
            }
            PAUSE_ACTION -> pausePlayer()
            START_TRACKS_ARRAY_ACTION -> {
                musicList = intent.getParcelableArrayListExtra(
                    TAG
                )!!
                currentMusicPosition = intent.getIntExtra(CURRENT_MUSIC_POSITION_KEY, 0)
                play(
                    musicList
                )
            }
            START_TRACK_ACTION -> play(
                intent.getParcelableExtra<Music>(
                    TAG
                )!!
            )

            PREV_ACTION -> {
                val prev = player.previousWindowIndex
                if (prev >= 0 && prev <= mediaSource.size) {
                    currentMusicPosition = prev
                } else {
                    currentMusicPosition = 0
                    playbackPosition = 0
                }
                startPlayer()
            }

            NEXT_ACTION -> {
                val next = player.nextWindowIndex
                if (next >= 0 && next < mediaSource.size) {
                    currentMusicPosition = next
                } else {
                    currentMusicPosition = 0
                    playbackPosition = 0
                }
                startPlayer()
            }
            STOP_ACTION -> {
                stopAction()
            }
            else -> {
                stopAction()
                throw UnsupportedOperationException(intent.action + " not supported as an action to ExoPLayerService")
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
                FOREGROUND_SERVICE_NOTIFICATION_ID
            )
        } catch (n: NullPointerException) {
        }
    }

    private fun showCurrentTrackNotification(music: Music): Music {

        val pendingIntent = PendingIntent.getActivity(
            this@ExoPlayerService,
            FOREGROUND_SERVICE_NOTIFICATION_ID,
            Intent(this@ExoPlayerService, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder: NotificationCompat.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(applicationContext, createNotificationChannel())
        } else {
            NotificationCompat.Builder(applicationContext,"")
        }

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher_round)
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
            FOREGROUND_SERVICE_NOTIFICATION_ID,
            generateIntent(this, PREV_ACTION),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.exo_notification_previous,
                resources.getString(R.string.previous),
                pendingIntentPrev
            ).build()
        )
        if (player.playWhenReady) {
            val pendingIntentPause = PendingIntent.getService(
                this,
                FOREGROUND_SERVICE_NOTIFICATION_ID,
                generateIntent(this, PAUSE_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.exo_notification_pause,
                    resources.getString(R.string.pause),
                    pendingIntentPause
                ).build()
            )
        } else {
            val pendingIntentPlay = PendingIntent.getService(
                this,
                FOREGROUND_SERVICE_NOTIFICATION_ID,
                generateIntent(this, PLAY_ACTION),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.exo_notification_play,
                    resources.getString(R.string.play),
                    pendingIntentPlay
                ).build()
            )
        }
        val pendingIntentNext = PendingIntent.getService(
            this,
            FOREGROUND_SERVICE_NOTIFICATION_ID,
            generateIntent(this, NEXT_ACTION),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.exo_notification_next,
                resources.getString(R.string.next),
                pendingIntentNext
            ).build()
        )
        val pendingIntentStop = PendingIntent.getService(
            this,
            FOREGROUND_SERVICE_NOTIFICATION_ID,
            generateIntent(this, STOP_ACTION),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        notificationBuilder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.exo_notification_stop,
                resources.getString(R.string.stop),
                pendingIntentStop
            ).build()
        )
        startForeground(
            FOREGROUND_SERVICE_NOTIFICATION_ID,
            notificationBuilder.build()
        )

        return music
    }

    private fun releasePlayer() {
        player.stop()
        player.release()
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
                    showCurrentTrackNotification(musicList[player.currentWindowIndex])
            }
            Player.STATE_IDLE -> {
            }

        }

    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray,
        trackSelections: TrackSelectionArray
    ) {
        super.onTracksChanged(trackGroups, trackSelections)
        showCurrentTrackNotification(musicList[player.currentWindowIndex])
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        pausePlayer()
        player.prepare(mediaSource)
        player.seekTo(player.currentWindowIndex, playbackPosition)

    }

    override fun onPositionDiscontinuity(reason: Int) {

    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
    override fun onSeekProcessed() {}


    private fun generateIntent(context: Context, action: String) =
        Intent(context, ExoPlayerService::class.java).apply {
            this.action = action
        }


    companion object {
        private const val TAG = "ExoPlayerService"
        private const val PREV_ACTION = "action.prev"
        private const val PLAY_ACTION = "action.play"
        private const val PAUSE_ACTION = "action.pause"
        private const val NEXT_ACTION = "action.next"
        private const val START_TRACK_ACTION = "action.start.track"
        private const val START_TRACKS_ARRAY_ACTION = "action.start.tracksArray"
        private const val STOP_ACTION = "action.stop"
        private const val CURRENT_MUSIC_POSITION_KEY = "currentMusicPosition"
        private const val FOREGROUND_SERVICE_NOTIFICATION_ID = 101


        fun actionStart(
            context: Context,
            arr: ArrayList<Music>,
            currentMusicPosition: Int
        ) {

            callService(context, Intent(context, ExoPlayerService::class.java).apply {
                action = START_TRACKS_ARRAY_ACTION
                putParcelableArrayListExtra(TAG, arr)
                putExtra(CURRENT_MUSIC_POSITION_KEY, currentMusicPosition)
            })

        }

        fun actionStart(
            context: Context,
            music: Music
        ) {
            callService(context, Intent(context, ExoPlayerService::class.java).apply {
                action = START_TRACK_ACTION
                putExtra(TAG, music)
            })

        }

        private fun callService(context: Context, intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val chan = NotificationChannel(
            TAG,
            "StarPlayer", NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return TAG
    }
}