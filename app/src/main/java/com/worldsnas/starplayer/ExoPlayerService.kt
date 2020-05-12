package com.worldsnas.starplayer

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Parcelable
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerNotificationManager.BitmapCallback
import com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.worldsnas.starplayer.model.Music
import java.util.*

class ExoPlayerService : Service(), Player.EventListener,
    OnAudioFocusChangeListener {
    private var mediaSource: ConcatenatingMediaSource? = null
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition: Long = 0
    private var pausedByAudioListener = false
    private val playerNotificationManager: PlayerNotificationManager? = null
    private var mAudioManager: AudioManager? = null
    private val notification: Notification? = null
    override fun onCreate() {
        super.onCreate()
        reInit()
    }

    private fun reInit() {
        makeAudioManager()
        resetDataQueue()
        currentWindow = 0
        playbackPosition = 0
        initializePlayer()
    }

    private fun makeAudioManager() {
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (mAudioManager != null) mAudioManager!!.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        if (mAudioManager != null) mAudioManager!!.abandonAudioFocus(this)
        sendBroadcast_stateIdle()
    }

    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange >= 0 && (pausedByAudioListener || player!!.playWhenReady)) startPlayer() else {
            if (player!!.playWhenReady) pausedByAudioListener = true
            pausePlayer()
        }
    }

    private fun resetDataQueue() {
        if (trackModelsQueue == null) trackModelsQueue =
            ArrayList() else trackModelsQueue!!.clear()
    }

    private fun play(trackModel: Music?) {
        val trackModels = ArrayList<Music?>()
        trackModels.add(trackModel)
        play(trackModels)
    }

    private fun play(trackModels: ArrayList<Music?>?) {
        reInit()
        if (trackModels != null) {
            trackModelsQueue = trackModels
            val mediaSources =
                arrayOfNulls<MediaSource>(trackModels.size)
            for (i in trackModels.indices) {
                mediaSources[i] =
                    buildMediaSource(Uri.parse(trackModels[i]!!.address))
            }
            mediaSource = ConcatenatingMediaSource(*mediaSources)

            player!!.prepare(mediaSource!!)
            startPlayer()
        }
    }

    private fun add(trackModel: Music?) {
        if (trackModelsQueue == null || trackModelsQueue!!.size == 0) play(
            trackModel
        ) else {
            addTrackIfNotAdded(trackModel)
        }
    }

    private fun addTrackIfNotAdded(trackModel: Music?) {
        if (!isInCurrentQueue(trackModel)) {
            mediaSource!!.addMediaSource(buildMediaSource(Uri.parse(trackModel!!.address)))
            trackModelsQueue!!.add(trackModel)
        }
    }

    private fun add(trackModels: ArrayList<Music?>) {
        if (trackModelsQueue == null || trackModelsQueue!!.size == 0) play(
            trackModels
        ) else {
            for (i in trackModels.indices) addTrackIfNotAdded(trackModels[i])
        }
    }

    private fun isInCurrentQueue(trackModel: Music?): Boolean {
        for (model in trackModelsQueue!!) if (model!!.id == trackModel!!.id) return true
        return false
    }

    private fun pausePlayer() {
        playbackPosition = player!!.currentPosition
        currentWindow = player!!.currentWindowIndex
        player!!.playWhenReady = false
        player!!.playbackState
        showCurrentTrackNotification()
    }

    private fun startPlayer() {
        pausedByAudioListener = false
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.playWhenReady = true
        player!!.playbackState
        showCurrentTrackNotification()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                this,
                DefaultRenderersFactory(this),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            player!!.seekTo(currentWindow, playbackPosition)
            player!!.addListener(this)
            player!!.playWhenReady = playWhenReady
            //            player.setRepeatMode(Player.REPEAT_MODE_OFF);
            //            player.setShuffleModeEnabled(true);
        }
    }

    private fun buildMediaSource(uri: Uri?): MediaSource? {
        val userAgent = "exoplayer-aseman-uxan"
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(uri)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            initializePlayer()
            if (intent.action != null) when (intent.action) {
                ACTION.PLAY_ACTION -> {
                    if (player!!.currentPosition == player!!.contentPosition && player!!.nextWindowIndex == C.INDEX_UNSET) player!!.seekTo(
                        0
                    )
                    startPlayer()
                }
                ACTION.PAUSE_ACTION -> pausePlayer()
                ACTION.REMOVE_ACTION_TRACK -> try {
                    val music = intent.getParcelableExtra<Music>(TAG)
                    var pos = -1
                    var i = 0
                    while (i < trackModelsQueue!!.size) {
                        val tr = trackModelsQueue!![i]
                        if (tr!!.id == music?.id) {
                            pos = i
                            break
                        }
                        i++
                    }
                    if (pos != -1) {
                        trackModelsQueue!!.removeAt(pos)
                        mediaSource!!.removeMediaSource(pos)
                    }
                } catch (ignored: Exception) {
                }
                ACTION.ACTION_BROADRCAST_TRACK -> broadCastCurrentTrack()
                ACTION.START_TRACKS_ARRAY_ACTION -> play(
                    intent.getParcelableArrayListExtra(
                        TAG
                    )
                )
                ACTION.START_TRACK_ACTION -> play(
                    intent.getParcelableExtra<Music>(
                        TAG
                    )
                )
                ACTION.ADD_ACTION_singleTrack -> add(
                    intent.getParcelableExtra<Music>(
                        TAG
                    )
                )
                ACTION.ADD_ACTION_tracksArray -> add(
                    intent.getParcelableArrayListExtra(
                        TAG
                    )!!
                )
                ACTION.PREV_ACTION -> if (player!!.currentPosition > 3210) {
                    val prev = player!!.previousWindowIndex
                    if (prev >= 0 && prev < mediaSource!!.size) player!!.seekToDefaultPosition(
                        prev
                    ) else player!!.seekTo(0)
                } else player!!.seekTo(0)
                ACTION.NEXT_ACTION -> {
                    val next = player!!.nextWindowIndex
                    if (next >= 0 && next < mediaSource!!.size) player!!.seekToDefaultPosition(
                        next
                    ) else {
                        player!!.seekTo(player!!.duration)
                        pausePlayer()
                    }
                }
                ACTION.PLAY_ACTION_From -> {
                    val music = intent.getParcelableExtra<Music>(TAG)
                    var i = 0
                    while (i < trackModelsQueue!!.size) {
                        if (trackModelsQueue!![i]!!.id == music?.id) {
                            player!!.seekToDefaultPosition(i)
                            break
                        }
                        i++
                    }
                }
                ACTION.STOP_ACTION -> stopAction()
            }
        }
        return START_STICKY
    }


    private fun stopAction() {
        playerNotificationManager?.setPlayer(null)
        releasePlayer()
        trackModelsQueue!!.clear()
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

    private fun showCurrentTrackNotification(): Music? {
        val current = currentPlayingTrack
        if (current == null) {
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
                    .setContentTitle(if (TextUtils.isEmpty(current.title)) current.artist else current.title)
                    .setContentText(current.artist)
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
            if (player!!.playWhenReady) {
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
        return current
    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            player!!.stop()
            player!!.release()
            player = null
        }
    }

    private fun sendBroadcastMusicReady(trackModel: Music?) {
        if (trackModel == null) {
            currentTrackId = ""
            LocalBroadcastManager.getInstance(this)
                .sendBroadcast(Intent(ACTION.ACTION_BROADRCAST_TRACK))
        } else {
            currentTrackId = trackModel.id.toString()
            val intent = Intent(ACTION.ACTION_BROADRCAST_TRACK)
            intent.putExtra(TAG, trackModel as Parcelable?)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    private fun sendBroadcast_stateIdle() {
        showCurrentTrackNotification()
        val intent = Intent(ACTION.ACTION_CALLBACK_STATE_IDLE)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onTimelineChanged(
        timeline: Timeline,
        manifest: Any?,
        reason: Int
    ) {
    }

    override fun onTracksChanged(
        trackGroups: TrackGroupArray,
        trackSelections: TrackSelectionArray
    ) {
        broadCastCurrentTrack()
    }

    private fun broadCastCurrentTrack() {
        sendBroadcastMusicReady(showCurrentTrackNotification())
    }

    private val currentPlayingTrack: Music?
         get() = if (trackModelsQueue!!.size > 0) trackModelsQueue!![player!!.currentWindowIndex] else null

    override fun onLoadingChanged(isLoading: Boolean) {}
    override fun onPlayerStateChanged(
        playWhenReady: Boolean,
        playbackState: Int
    ) {
        when (playbackState) {
            Player.STATE_BUFFERING -> {
            }
            Player.STATE_ENDED -> {
                player!!.seekTo(
                    player!!.currentWindowIndex,
                    0
                )
                pausePlayer()
            }
            Player.STATE_IDLE -> {
                sendBroadcastMusicReady(null)
                sendBroadcast_stateIdle()
            }
            Player.STATE_READY -> {
            }
            else -> {
            }
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {}
    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//        if (shuffleModeEnabled) {
//            player.setRepeatMode(Player.REPEAT_MODE_OFF);
//            int firstId = player.getPreviousWindowIndex();
//            while (firstId != -C.INDEX_UNSET) {
//                firstId = player.getPreviousWindowIndex();
//            }
//            playFrom(this, trackModelsQueue.get(firstId));
//        } else
//            player.setRepeatMode(Player.REPEAT_MODE_ALL);
//        player.seekToDefaultPosition();
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        pausePlayer()
        player!!.prepare(mediaSource!!)
        player!!.seekTo(currentWindow, playbackPosition)
        broadCastCurrentTrack()
        sendBroadcast_stateIdle()
    }

    override fun onPositionDiscontinuity(reason: Int) {}
    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
    override fun onSeekProcessed() {}
    interface ACTION {
        companion object {
            const val ACTION_CALLBACK_STATE_IDLE = "action.callback.state.idle"
            const val ACTION_BROADRCAST_TRACK = "action.track"
            const val MAIN_ACTION = "action.main"
            const val INIT_ACTION = "action.init"
            const val PREV_ACTION = "action.prev"
            const val ADD_ACTION_tracksArray = "action.add.album"
            const val ADD_ACTION_singleTrack = "action.add.track"
            const val PLAY_ACTION = "action.play"
            const val PAUSE_ACTION = "action.pause"
            const val NEXT_ACTION = "action.next"
            const val START_TRACK_ACTION = "action.start.track"
            const val START_TRACKS_ARRAY_ACTION = "action.start.tracksArray"
            const val STOP_ACTION = "action.stop"
            const val PLAY_ACTION_From = "action.play.from"
            const val SHUFFLE_ACTION = "action.shuffle"
            const val REMOVE_ACTION_TRACK = "action.remove.track"
        }
    }

    interface NOTIFICATION_ID {
        companion object {
            const val FOREGROUND_SERVICE = 101
        }
    }

    private inner class DescriptionAdapter : MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): String {
            return if (trackModelsQueue == null || trackModelsQueue!!.size <= 0 || trackModelsQueue!![player.currentWindowIndex] == null
            ) "" else trackModelsQueue!![player.currentWindowIndex]!!.album
        }

        override fun getCurrentContentText(player: Player): String? {
            return if (trackModelsQueue == null || trackModelsQueue!!.size <= 0 || trackModelsQueue!![player.currentWindowIndex] == null
            ) "" else trackModelsQueue!![player.currentWindowIndex]!!.artist
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: BitmapCallback
        ): Bitmap? {
            return null
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            return PendingIntent.getActivity(
                this@ExoPlayerService,
                NOTIFICATION_ID.FOREGROUND_SERVICE,
                Intent(this@ExoPlayerService, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    companion object {
        const val TAG = "PlayerService"
        var trackModelsQueue: ArrayList<Music?>? = ArrayList()
        var currentTrackId = ""
        var player: SimpleExoPlayer? = null
            private set

        private fun callService(context: Context, intent: Intent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun actionBroadCastCurrentTrack(context: Context) {
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.ACTION_BROADRCAST_TRACK
            callService(context, serviceIntent)
        }

        fun generateStopIntent(context: Context?): Intent {
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.STOP_ACTION
            return serviceIntent
        }

        fun actionStop(context: Context?) {
            if (context == null) return
            callService(
                context,
                generateStopIntent(context)
            )
        }

        fun actionNext(context: Context?) {
            if (context == null) return
            callService(
                context,
                generateNextIntent(context)
            )
        }

        fun generateNextIntent(context: Context?): Intent {
            val nextIntent = Intent(context, ExoPlayerService::class.java)
            nextIntent.action = ACTION.NEXT_ACTION
            return nextIntent
        }

        fun actionPrev(context: Context?) {
            if (context == null) return
            callService(
                context,
                generatePrevIntent(context)
            )
        }

        fun generatePrevIntent(context: Context?): Intent {
            val prevIntent = Intent(context, ExoPlayerService::class.java)
            prevIntent.action = ACTION.PREV_ACTION
            return prevIntent
        }

        fun playFrom(context: Context?, trackModel: Music?) {
            if (context == null || trackModel == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.PLAY_ACTION_From
            serviceIntent.putExtra(TAG, trackModel as Parcelable?)
            callService(context, serviceIntent)
        }

        fun actionRemove(context: Context?, trackModel: Music?) {
            if (context == null || trackModel == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.REMOVE_ACTION_TRACK
            serviceIntent.putExtra(TAG, trackModel as Parcelable?)
            callService(context, serviceIntent)
        }

        fun actionAdd(context: Context?, trackModel: Music?) {
            if (context == null || trackModel == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.ADD_ACTION_singleTrack
            serviceIntent.putExtra(TAG, trackModel as Parcelable?)
            callService(context, serviceIntent)
        }

        fun actionAdd(
            context: Context?,
            arr: ArrayList<Music?>?
        ) {
            if (context == null || arr == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.ADD_ACTION_tracksArray
            serviceIntent.putExtra(TAG, arr)
            callService(context, serviceIntent)
        }

        fun generatePlayIntent(context: Context?): Intent {
            val playIntent = Intent(context, ExoPlayerService::class.java)
            playIntent.action = ACTION.PLAY_ACTION
            return playIntent
        }

        fun generatePauseIntent(context: Context?): Intent {
            val pauseIntent = Intent(context, ExoPlayerService::class.java)
            pauseIntent.action = ACTION.PAUSE_ACTION
            return pauseIntent
        }

        fun actionStart(
            context: Context?,
            arr: ArrayList<Music>?
        ) {
            if (context == null || arr == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.START_TRACKS_ARRAY_ACTION
            serviceIntent.putParcelableArrayListExtra(TAG, arr)
            callService(context, serviceIntent)
        }

        fun actionStart(context: Context?, trackModel: Music?) {
            if (context == null || trackModel == null) return
            val serviceIntent = Intent(context, ExoPlayerService::class.java)
            serviceIntent.action = ACTION.START_TRACK_ACTION
            serviceIntent.putExtra(TAG, trackModel as Parcelable?)
            callService(context, serviceIntent)
        }
    }
}