package com.example.skimmiatest.services

import android.app.*
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.skimmiatest.R
import com.example.skimmiatest.model.Song
import com.example.skimmiatest.view.activity.MainActivity
import java.util.*

class MusicService : Service(), OnPreparedListener, MediaPlayer.OnErrorListener {
    //media player
    private var player: MediaPlayer? = null
    //song list
    private var songs: ArrayList<Song>? = null
    //current position
    private var songPosn = 0
    //binder
    private val musicBind: IBinder = MusicBinder()
    //title of current song
    private var songTitle = ""

    private var mManager: NotificationManager? = null
    private var androidChannel: NotificationChannel? = null

    override fun onCreate() { //create the service
        super.onCreate()
        //initialize position
        songPosn = 0
        //create player
        player = MediaPlayer()
        //initialize
        initMusicPlayer()
    }

    private fun initMusicPlayer() { //set player properties
        player!!.setWakeMode(
            applicationContext,
            PowerManager.PARTIAL_WAKE_LOCK
        )
        player!!.setAudioAttributes(AudioAttributes
            .Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build())
        //set listeners
        player!!.setOnPreparedListener(this)
        player!!.setOnErrorListener(this)
    }

    //pass song list
    fun setList(theSongs: ArrayList<Song>?) {
        songs = theSongs
    }

    //binder
    inner class MusicBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }

    //activity will bind to service
    override fun onBind(intent: Intent?): IBinder? {
        return musicBind
    }

    //release resources when unbind
    override fun onUnbind(intent: Intent?): Boolean {
        player!!.stop()
        player!!.release()
        return false
    }

    //play a song
    fun playSong() { //play
        player!!.reset()
        //get song
        val playSong: Song = songs!![songPosn]
        //get title
        songTitle = playSong.title!!
        //get id
        val currSong: Long = playSong.id
        //set uri
        val trackUri = ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            currSong
        )
        //set the data source
        try {
            player!!.setDataSource(applicationContext, trackUri)
        } catch (e: Exception) {
            Log.e("MUSIC SERVICE", "Error setting data source", e)
        }
        player!!.prepareAsync()
    }

    //set the song
    fun setSong(songIndex: Int) {
        songPosn = songIndex
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        Log.v("MUSIC PLAYER", "Playback Error")
        mp.reset()
        return false
    }

    override fun onPrepared(mp: MediaPlayer) { //start playback
        mp.start()
        //notification
        val notIntent = Intent(this, MainActivity::class.java)
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendInt = PendingIntent.getActivity(
            this, 0,
            notIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (androidChannel == null) {
                androidChannel = NotificationChannel(
                    baseContext.getString(R.string.default_notification_channel_id),
                    songTitle, NotificationManager.IMPORTANCE_LOW
                )
                androidChannel!!.lightColor = Color.GREEN
                androidChannel!!.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
                getManager()!!.createNotificationChannel(androidChannel!!)
            }
        }

        val notificationBuilder = NotificationCompat.Builder(
            baseContext,
            baseContext.getString(R.string.default_notification_channel_id)
        )
            .setContentIntent(pendInt)
            .setContentTitle("Reproduciendo...")
            .setContentText(songTitle)
            .setTicker(songTitle)
            .setShowWhen(true)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.mipmap.ic_launcher)
        getManager()!!.notify(1, notificationBuilder.build())
    }

    //playback methods
    fun getPosn(): Int {
        return player!!.currentPosition
    }

    fun getDur(): Int {
        return player!!.duration
    }

    fun isPng(): Boolean {
        return player!!.isPlaying
    }

    fun pausePlayer() {
        player!!.pause()
    }

    fun seek(posn: Int) {
        player!!.seekTo(posn)
    }

    fun go() {
        player!!.start()
    }

    override fun onDestroy() {
        stopForeground(true)
    }

    private fun getManager(): NotificationManager? {
        if (mManager == null) {
            mManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        }
        return mManager
    }

}