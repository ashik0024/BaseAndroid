package com.example.appdemo.Services

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.appdemo.R

class MediaPlayerForegroundService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    var isFirstLunch= true
    companion object {
        const val ACTION_PLAY_PAUSE = "com.example.appdemo.PLAY_PAUSE"
        const val ACTION_FORWARD = "com.example.appdemo.FORWARD"
        const val ACTION_REWIND = "com.example.appdemo.REWIND"
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startForeground(1, createNotification()) // Start Foreground Service
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio) // Your audio file in res/raw
        mediaPlayer?.isLooping = true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        mediaPlayer?.start()
//        return START_STICKY
        when (intent?.action) {
            ACTION_PLAY_PAUSE -> {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.pause()
                } else {
                    mediaPlayer?.start()
                }
            }
            ACTION_FORWARD -> {
                mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.plus(5000) ?: 0) // Forward 5 sec
            }
            ACTION_REWIND -> {
                mediaPlayer?.seekTo(mediaPlayer?.currentPosition?.minus(5000)?.coerceAtLeast(0) ?: 0) // Rewind 5 sec
            }
        }
        if (isFirstLunch){
            mediaPlayer?.start()
            isFirstLunch=false
        }

        // Update notification
        startForeground(1, createNotification())

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification(): Notification {
        val channelId = "media_playback_channel"
        val channelName = "Media Playback"

        val notificationManager = getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        // Intent for Play/Pause
        val playPauseIntent = PendingIntent.getService(
            this, 1,
            Intent(this, MediaPlayerForegroundService::class.java).apply {
                action = ACTION_PLAY_PAUSE
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent for Forward (5 sec)
        val forwardIntent = PendingIntent.getService(
            this, 2,
            Intent(this, MediaPlayerForegroundService::class.java).apply {
                action = ACTION_FORWARD
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent for Rewind (5 sec)
        val rewindIntent = PendingIntent.getService(
            this, 3,
            Intent(this, MediaPlayerForegroundService::class.java).apply {
                action = ACTION_REWIND
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Playing Audio")
            .setContentText("Your music is playing in the background")
            .setSmallIcon(R.drawable.baseline_home_24)
            .addAction(R.drawable.ic_fast_rewind, "Rewind 5s", rewindIntent)
            .addAction(R.drawable.ic_player_pause, "Play/Pause", playPauseIntent)
            .addAction(R.drawable.ic_fast_forward, "Forward 5s", forwardIntent)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
}

