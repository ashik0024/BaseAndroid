package com.example.appdemo.ui.player


import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appdemo.databinding.FragmentPlayerBinding
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.TrackGroupArray
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector.Parameters
import androidx.media3.exoplayer.trackselection.TrackSelectionArray
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.PlayerView
import com.example.appdemo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

@UnstableApi
open class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding?=null
    private val binding get() = _binding

    private var playPauseButton: ImageButton? = null
    private var nextButton: ImageButton? = null
    private var previousButton: ImageButton? = null
    private var fastForwordButton: ImageButton? = null
    private var fastRewindButton: ImageButton? = null
    private var playerSettingsButton: ImageButton? = null
    private var contentNameTv: TextView? = null
    private var exo_buffering: ProgressBar? = null
    private var exo_progress: DefaultTimeBar? = null
    private var defaultTrackSelector: DefaultTrackSelector? = null
    private val viewModel: PlayerViewModel by viewModels()
    private var trackSelectorParameters: Parameters? = null
    private var startWindow = 0
    private var maxBitRate: Int = 0
    private var playCounter: Int = -1
    private var startAutoPlay = false
    private var retryCounter: Int = 0
    private var reloadCounter: Int = 0
    private var startPosition: Long = 0
    private var stateReadyFirstTime = false
    companion object {
        private const val KEY_WINDOW = "window"
        private const val KEY_POSITION = "position"
        private const val KEY_VAST_URL = "vast_url"
        private const val KEY_AUTO_PLAY = "auto_play"
        private const val KEY_PLAY_COUNTER = "play_counter"
        private const val KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPlayerBinding.inflate(layoutInflater)
        return binding?.root
    }

    @OptIn(UnstableApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            startWindow = savedInstanceState.getInt(KEY_WINDOW)
            startPosition = savedInstanceState.getLong(KEY_POSITION)
//            playCounter = savedInstanceState.getInt(KEY_PLAY_COUNTER)
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY)
//            currentlyPlayingVastUrl = savedInstanceState.getString(KEY_VAST_URL) ?: ""
             trackSelectorParameters = savedInstanceState
                ?.getBundle(KEY_TRACK_SELECTOR_PARAMETERS)
                ?.let { DefaultTrackSelector.Parameters.fromBundle(it) }
        } else {
            val builder = Parameters.Builder(requireContext())
            trackSelectorParameters = builder.build()
//            clearStartPosition()
        }

        inItPlayer()
        initControllerButton()


    }


    private fun initControllerButton() {

        exo_buffering
        playPauseButton = binding?.root?.findViewById<ImageButton>(R.id.player_play_pause)
        nextButton = binding?.root?.findViewById<ImageButton>(R.id.player_next)
        previousButton = binding?.root?.findViewById<ImageButton>(R.id.player_previous)
        fastForwordButton = binding?.root?.findViewById<ImageButton>(R.id.player_fast_forword)
        fastRewindButton = binding?.root?.findViewById<ImageButton>(R.id.player_fast_rewind)
        playerSettingsButton = binding?.root?.findViewById<ImageButton>(R.id.player_settings)
        contentNameTv = binding?.root?.findViewById(R.id.content_name)
        exo_progress = binding?.root?.findViewById<DefaultTimeBar>(R.id.exo_progress)
        exo_buffering = binding?.root?.findViewById<ProgressBar>(R.id.exo_buffering)

        playPauseButton?.setOnClickListener {
            when (binding?.playerView?.player?.isPlaying) {
                true -> {
                    binding?.playerView?.player?.pause()
                    playPauseButton?.setImageResource(R.drawable.ic_player_play)
                    playPauseButton?.isVisible = true
                }

                else -> {
                    binding?.playerView?.player?.play()
                    playPauseButton?.setImageResource(R.drawable.ic_player_pause)
                    playPauseButton?.isVisible = true
                }
            }
        }
        nextButton?.setOnClickListener {
//            playNext()
            Toast.makeText(context, "next", Toast.LENGTH_SHORT).show()
        }
        previousButton?.setOnClickListener {
//            playPrevious()
            Toast.makeText(context, "previous", Toast.LENGTH_SHORT).show()
        }

        fastForwordButton?.setOnClickListener {
            viewModel.player?.seekForward()
        }
        fastRewindButton?.setOnClickListener {
            viewModel.player?.seekBack()
        }

        playerSettingsButton?.setOnClickListener {
            if (defaultTrackSelector == null || defaultTrackSelector?.currentMappedTrackInfo == null){
                Toast.makeText(context, "defaultTrackSelector is null", Toast.LENGTH_SHORT).show()
            }
           else{
                val bottomSheetDialog = TrackSelectionDialog(requireContext())

                bottomSheetDialog.init(viewModel.player!!)
                lifecycle.addObserver(bottomSheetDialog)
                bottomSheetDialog.setOnDismissListener {
                    lifecycle.removeObserver(bottomSheetDialog)
                    onTrackerDialogDismissed()
                }
                bottomSheetDialog.setOnCancelListener {
                    lifecycle.removeObserver(bottomSheetDialog)
                    onTrackerDialogDismissed()
                }
                bottomSheetDialog.show()
                bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }


        }

    }
    protected open fun onTrackerDialogDismissed() {
        //hook for subclass to listen the dismiss event
    }

    @OptIn(UnstableApi::class)
    private fun inItPlayer() {
        // Initialize the TrackSelector
        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory()
        defaultTrackSelector = DefaultTrackSelector(requireContext(), adaptiveTrackSelectionFactory).apply {
            parameters = trackSelectorParameters!!
        }


        if (viewModel.player == null) {
            viewModel.player = ExoPlayer.Builder(requireContext())
                .setTrackSelector(defaultTrackSelector!!)
                .build()
        }
        var player = viewModel.player
        binding?.playerView?.player = player


        val mediaItem = MediaItem.fromUri(viewModel.m3u8HLSContent)
        player?.setMediaItem(mediaItem)
        player?.addListener(object : Player.Listener {
            // Called when the player state changes.
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                Log.d("ExoPlayer", "Player State Changed: $playWhenReady, State: $playbackState")
            }

            // Called when an error occurs during playback.
            override fun onPlayerError(error: PlaybackException) {
                Log.e("ExoPlayer", "Playback error: ${error.message}")
            }

            // Called when a timeline change occurs (e.g., new media is loaded).
            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                Log.d("ExoPlayer", "Timeline Changed: $reason")
            }

            // Called when a media item is loaded or ready to play.
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                Log.d("ExoPlayer", "Media Item Transition: $reason, $mediaItem")
            }



            // Called when the playback position changes (seek).
            override fun onPositionDiscontinuity(reason: Int) {
                Log.d("ExoPlayer", "Position Discontinuity: $reason")
            }

            // Called when the playback duration or position changes.
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
                Log.d("ExoPlayer", "Playback Parameters Changed: $playbackParameters")
            }

            // Called when the audio/video tracks change.
            override fun onTracksChanged(tracks: Tracks) {
                tracks.groups.forEach { group ->
                    Log.d("Tracks", "Track group: ${group.mediaTrackGroup.id}")
                    for (i in 0 until group.length) {
                        val track = group.getTrackFormat(i)
                        Log.d(
                            "Trackseerr",
                            "Track: ${track.bitrate}bps, ${track.width}x${track.height}, Mime: ${track.sampleMimeType}"
                        )
                    }
                }
            }
            // Called when buffering starts or stops.
            override fun onIsLoadingChanged(isLoading: Boolean) {
                if (isLoading && !stateReadyFirstTime) {
                    exo_buffering?.visibility = View.VISIBLE // Show during buffering/loading
                } else {
                    exo_buffering?.visibility = View.GONE // Hide once content is ready
                }
            }
            // Called when the player's events are updated (volume, seek, etc.).
            override fun onPlaybackStateChanged(state: Int) {
                Log.d("ExoPlayer", "Playback State Changed: $state")
                when (state) {
                    Player.STATE_BUFFERING -> {
                        exo_buffering?.visibility = View.VISIBLE
                        Log.d("ExoPlayer", "Player is buffering")
                    }
                    Player.STATE_READY -> {
                        exo_buffering?.visibility = View.GONE
                        stateReadyFirstTime=true
                        Log.d("ExoPlayer", "Player is ready")
                    }
                    Player.STATE_ENDED -> {
                        exo_buffering?.visibility = View.GONE
                        Log.d("ExoPlayer", "Playback ended")
                    }
                    Player.STATE_IDLE -> {
                        exo_buffering?.visibility = View.GONE
                        Log.d("ExoPlayer", "Player is idle")
                    }
                }
            }
        })
        player?.prepare()
        player?.play()

    }
//    private fun playNext() {
//        if (!playlistManager?.isNextContentPremium()!!) {
//            playlistManager?.nextContent()
//            playContent(false)
//        } else {
//            baseViewModel.playContentLiveData.postValue(playlistManager?.getNextContent())
//        }
//    }

//    private fun playPrevious() {
//        if (!playlistManager?.isPreviousContentPremium()!!) {
//            playlistManager?.previousContent()
//            playContent(false)
//        } else {
//            baseViewModel.playContentLiveData.postValue(playlistManager?.getPreviousContent())
//        }
//    }

//    private fun cancelPlayNext() {
//        playNextJob?.cancel()
//        playNextJob = null
//        binding?.playNextGroup?.hide()
//    }

    override fun onStop() {
        super.onStop()
        Log.d("testingState", "onStop: ")
    }
    override fun onPause() {
        super.onPause()
        binding?.playerView?.player?.pause()
        Log.d("testingState", "onPause: ")
    }

    override fun onResume() {
        super.onResume()
        binding?.playerView?.player?.play()
        Log.d("testingState", "onResume: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("testingState", "onDestroy: ")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("testingState", "onDestroy: ")
    }

}


