package com.example.appdemo.ui.player

import android.content.Context
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks

import com.example.appdemo.R
import com.example.appdemo.ui.player.TrackSelectionView.Companion.filterOverrides
import com.google.android.material.bottomsheet.BottomSheetDialog

class TrackSelectionDialog(context: Context) : BottomSheetDialog(context),
    DefaultLifecycleObserver {
    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    fun init(player: Player, maxBitRate: Int = -1) {
        val trackSelectionParameters = player.trackSelectionParameters
        val bottomView = layoutInflater.inflate(R.layout.track_selection_dialog, null) as TrackSelectionView
        val trackType = C.TRACK_TYPE_VIDEO
        val trackGroups = ArrayList<Tracks.Group>()
        for (trackGroup in player.currentTracks.groups) {
            if (trackGroup.type == trackType) {
                Log.d("trackGroups", ":"+trackGroup.mediaTrackGroup.length)
                trackGroups.add(trackGroup)
            }
        }
        val isDisabled = player.trackSelectionParameters.disabledTrackTypes.contains(trackType)
        val overrides = HashMap(filterOverrides(player.trackSelectionParameters.overrides, trackGroups, false))

        bottomView.init(maxBitRate, isDisabled, trackGroups, overrides,
            object : TrackSelectionView.TrackSelectionListener {
                override fun onTrackSelectionChanged(isDisabled: Boolean, overrides: Map<TrackGroup, TrackSelectionOverride>?, ) {
                    val builder = trackSelectionParameters.buildUpon()
                    builder.setTrackTypeDisabled(trackType,
                        trackGroups.isNotEmpty() && trackSelectionParameters.disabledTrackTypes.contains(trackType))
                    builder.clearOverridesOfType(trackType)
                    overrides?.values?.let {
                        for (override in it) {
                            builder.addOverride(override)
                        }
                    }
                    builder.build().let {
                        player.trackSelectionParameters = it
                    }
                    dismiss()
                }
            }
        )
        setContentView(bottomView)
    }

    override fun onPause(owner: LifecycleOwner) {
        dismiss()
        super.onPause(owner)
    }
}