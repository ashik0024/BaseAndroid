package com.example.appdemo.ui.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector

@UnstableApi
class PlayerViewModel : ViewModel() {
    var player: ExoPlayer? = null

    var dashMpdContent="https://5b44cf20b0388.streamlock.net:8443/vod/smil:bbb.smil/manifest.mpd"
    var mp4Content="http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    var m3u8HLSContent="https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.mp4/.m3u8"

    var trackSelector: DefaultTrackSelector? = null


    override fun onCleared() {
        super.onCleared()
        player?.release()
        player = null
        trackSelector = null
        Log.d("testingState", "onCleared: ")
    }
}