package com.example.appdemo.ui.player

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.media3.common.Format
import androidx.media3.common.TrackGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.example.appdemo.R
import com.google.common.collect.ImmutableList

import java.util.*

/** A view for making track selections.  */
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class TrackSelectionView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    /** Listener for changes to the selected tracks.  */
    interface TrackSelectionListener {
        /**
         * Called when the selected tracks changed.
         *
         * @param isDisabled Whether the renderer is disabled.
         * @param overrides List of selected track selection overrides for the renderer.
         */
        fun onTrackSelectionChanged(isDisabled: Boolean, overrides: Map<TrackGroup, TrackSelectionOverride>?)
    }
    
    private var maxBitRate = -1
    private var isDisabled = false
    private val defaultView: TextView
    private val inflater: LayoutInflater
    private var allowMultipleOverrides = false
    private var allowAdaptiveSelections = false
    private val trackGroups: MutableList<Tracks.Group>
    private val componentListener: ComponentListener
    private var listener: TrackSelectionListener? = null
    private var trackViews: MutableList<TextView?> = mutableListOf()
    private val overrides: MutableMap<TrackGroup, TrackSelectionOverride>
    
    init {
        orientation = VERTICAL
        // Don't save view hierarchy as it needs to be reinitialized with a call to init.
        isSaveFromParentEnabled = false
        
        inflater = LayoutInflater.from(context)
        componentListener = ComponentListener()
        trackGroups = ArrayList()
        overrides = HashMap()
        
        // View for clearing the override to allow the selector to use its default selection logic.
        defaultView = inflater.inflate(R.layout.list_item_quality, this, false) as TextView
        defaultView.setText(androidx.media3.ui.R.string.exo_track_selection_auto)
        defaultView.isEnabled = false
        defaultView.isFocusable = true
        defaultView.setOnClickListener(componentListener)
        addView(defaultView)
    }
    
    companion object {
        /**
         * Returns the subset of {@code overrides} that apply to the specified {@code trackGroups}. If
         * {@code allowMultipleOverrides} is {@code} then at most one override is retained, which will be
         * the one whose track group is first in {@code trackGroups}.
         *
         * @param overrides The overrides to filter.
         * @param trackGroups The track groups whose overrides should be retained.
         * @param allowMultipleOverrides Whether more than one override can be retained.
         * @return The filtered overrides.
         */
        @JvmStatic
        fun filterOverrides(overrides: Map<TrackGroup, TrackSelectionOverride>, trackGroups: List<Tracks.Group>, allowMultipleOverrides: Boolean, ): Map<TrackGroup, TrackSelectionOverride> {
            val filteredOverrides = HashMap<TrackGroup, TrackSelectionOverride>()
            for (trackGroup in trackGroups) {
                val override = overrides[trackGroup.mediaTrackGroup]
                if (override != null && (allowMultipleOverrides || filteredOverrides.isEmpty())) {
                    filteredOverrides[override.mediaTrackGroup] = override
                }
            }
            return filteredOverrides
        }
    }
    /**
     * Sets whether adaptive selections (consisting of more than one track) can be made using this
     * selection view.
     *
     *
     * For the view to enable adaptive selection it is necessary both for this feature to be
     * enabled, and for the target renderer to support adaptation between the available tracks.
     *
     * @param allowAdaptiveSelections Whether adaptive selection is enabled.
     */
    fun setAllowAdaptiveSelections(allowAdaptiveSelections: Boolean) {
        if (this.allowAdaptiveSelections != allowAdaptiveSelections) {
            this.allowAdaptiveSelections = allowAdaptiveSelections
            updateViews()
        }
    }
    
    /**
     * Sets whether tracks from multiple track groups can be selected. This results in multiple [ ] to be returned by [.getOverrides].
     *
     * @param allowMultipleOverrides Whether multiple track selection overrides can be selected.
     */
    fun setAllowMultipleOverrides(allowMultipleOverrides: Boolean) {
        if (this.allowMultipleOverrides != allowMultipleOverrides) {
            this.allowMultipleOverrides = allowMultipleOverrides
            if (!allowMultipleOverrides && overrides.size > 1) {
                // Re-filter the overrides to retain only one of them.
                val filteredOverrides = filterOverrides(overrides, trackGroups, false)
                overrides.clear()
                overrides.putAll(filteredOverrides)
            }
            updateViews()
        }
    }
        /**
     * Initialize the view to select tracks for a specified renderer using [MappedTrackInfo] and
     * a set of [DefaultTrackSelector.Parameters].
     *
     * @param mappedTrackInfo The [MappedTrackInfo].
     * @param rendererIndex The index of the renderer.
     * @param isDisabled Whether the renderer should be initially shown as disabled.
     * @param overrides List of initial overrides to be shown for this renderer. There must be at most
     * one override for each track group. If [.setAllowMultipleOverrides] hasn't
     * been set to `true`, only the first override is used.
     * @param listener An optional listener for track selection updates.
     */
    
    fun init(maxBitRate: Int,
        isDisabled: Boolean,
        trackGroups: List<Tracks.Group>,
        overrides: Map<TrackGroup, TrackSelectionOverride>,
        listener: TrackSelectionListener?
    ) {
        this.overrides.clear()
        this.listener = listener
        this.trackGroups.clear()
        this.isDisabled = isDisabled
        this.maxBitRate = maxBitRate
        this.trackGroups.addAll(trackGroups)
        this.overrides.putAll(filterOverrides(overrides, trackGroups, allowMultipleOverrides))
        updateViews()
    }
    
    private fun updateViews() {
        // Remove previous per-track views.
        for (i in childCount - 1 downTo 3) {
            removeViewAt(i)
        }
        if (trackGroups.isEmpty()) {
            // The view is not initialized.
            defaultView.isEnabled = false
            return
        }
        
        defaultView.isEnabled = true
        var invisibleProfileCount = 0
        
        for (trackGroupIndex in trackGroups.indices) {
            val trackGroup = trackGroups[trackGroupIndex]
            
            for (trackIndex in 0 until trackGroup.length) {
                val trackInfo = TrackInfo(trackGroup, trackIndex)
                val trackView = inflater.inflate(R.layout.list_item_quality, this, false) as TextView
                val format = trackInfo.format
                if (maxBitRate > 0 && format.bitrate > maxBitRate) {
                    trackView.isVisible = false
                    invisibleProfileCount++
                }
                val profile = "${format.height}p"
                trackView.text = profile
                trackView.tag = trackInfo
                if (trackGroup.isTrackSupported(trackIndex)) {
                    trackView.isFocusable = true
                    trackView.setOnClickListener(componentListener)
                } else {
                    trackView.isFocusable = false
                    trackView.isEnabled = false
                }
                trackViews.add(trackView)
            }
            if (invisibleProfileCount == trackGroup.length) {
                trackViews.last()?.isVisible = true
            }
        }
        val sortedProfileList = trackViews.sortedByDescending {trackView ->
            trackView?.let { (it.tag as TrackInfo).format.bitrate }
        }
        sortedProfileList.forEachIndexed { index, trackView ->
            if (trackView != null && trackView.tag is TrackInfo && index > 0) {
                // get the current profile of the list and the immediate top and bottom profile and compare
                val previousProfile: String = sortedProfileList[index - 1]!!.text.toString()
                val previousProfileBitRate: Int = (sortedProfileList[index - 1]?.tag as TrackInfo).format.bitrate
                val currentBitRate: Int = (trackView.tag as TrackInfo).format.bitrate
                val nextProfile = sortedProfileList.getOrNull(index + 1)?.text?.toString()
                val nextProfileBitRate = sortedProfileList.getOrNull(index + 1)?.tag?.run { (this as TrackInfo).format.bitrate } ?: 0
                val hasDuplicateNext = nextProfile != null && nextProfileBitRate < currentBitRate
                
                if (trackView.text == previousProfile && currentBitRate < previousProfileBitRate && !hasDuplicateNext && currentBitRate < 2_097_152) {
                    trackView.text = trackView.text.toString().plus(" (Data Saver)")
                } else if (trackView.text == nextProfile && currentBitRate > nextProfileBitRate && hasDuplicateNext && currentBitRate > 2_097_152) {
                    trackView.text = trackView.text.toString().plus(" (HD)")
                }
            }
            addView(trackView)
        }
        updateViewStates()
    }
    
    private fun updateViewStates() {
        val image = ContextCompat.getDrawable(context, R.drawable.ic_check_video_quality)
        val h = image?.intrinsicHeight ?: 0
        val w = image?.intrinsicWidth ?: 0
        image?.setBounds(0, 0, w, h)
        
        val isDefaultProfileActive = (!isDisabled && overrides.isEmpty())
        if (isDefaultProfileActive) {
            defaultView.setCompoundDrawables(image, null, null, null)
            defaultView.setTextColor(ContextCompat.getColor(context, R.color.red))
        } else {
            defaultView.setCompoundDrawables(null, null, null, null)
        }
        for (i in trackGroups.indices) {
            val override = overrides[trackGroups[i].mediaTrackGroup]
            for (trackView in trackViews) {
                trackView?.let {
                    val trackInfo = (it.tag) as TrackInfo
                    if (override != null && override.trackIndices.contains(trackInfo.trackIndex)) {
                        it.setCompoundDrawables(image, null, null, null)
                        it.setTextColor(ContextCompat.getColor(context, R.color.red))
                    } else {
                        it.setCompoundDrawables(null, null, null, null)
                    }
                }
            }
        }
    }
    
    private fun onClick(view: View) {
        if (view === defaultView) {
            onDefaultViewClicked()
        } else {
            onTrackViewClicked(view)
        }
        updateViewStates()
        if (listener != null) {
            listener!!.onTrackSelectionChanged(isDisabled, overrides)
        }
    }
    
    private fun onDefaultViewClicked() {
        isDisabled = false
        overrides.clear()
    }
    
    private fun onTrackViewClicked(view: View) {
        isDisabled = false
        val trackInfo = view.tag as TrackInfo
        val trackGroup = trackInfo.trackGroup.mediaTrackGroup
        val trackIndex = trackInfo.trackIndex
        val override = overrides[trackGroup]
        if (override == null) {
            // Start new override.
            if (!allowMultipleOverrides && overrides.isNotEmpty()) {
                // Removed other overrides if we don't allow multiple overrides.
                overrides.clear()
            }
            overrides[trackGroup] = TrackSelectionOverride(trackGroup, ImmutableList.of(trackIndex))
        } else {
            // An existing override is being modified.
            val trackIndices = ArrayList(override.trackIndices)
            val isCurrentlySelected = (view as TextView).compoundDrawables[0] != null
            val isAdaptiveAllowed = shouldEnableAdaptiveSelection(trackInfo.trackGroup)
            val isUsingCheckBox = isAdaptiveAllowed || shouldEnableMultiGroupSelection()
            if (isCurrentlySelected && isUsingCheckBox) {
                // Remove the track from the override.
                trackIndices.remove(trackIndex)
                if (trackIndices.isEmpty()) {
                    // The last track has been removed, so remove the whole override.
                    overrides.remove(trackGroup)
                } else {
                    overrides[trackGroup] = TrackSelectionOverride(trackGroup, trackIndices)
                }
            } else if (!isCurrentlySelected) {
                if (isAdaptiveAllowed) {
                    // Add new track to adaptive override.
                    trackIndices.add(trackIndex)
                    overrides[trackGroup] = TrackSelectionOverride(trackGroup, trackIndices)
                } else {
                    // Replace existing track in override.
                    overrides[trackGroup] = TrackSelectionOverride(trackGroup, ImmutableList.of(trackIndex))
                }
            }
        }
    }
    
    private fun shouldEnableAdaptiveSelection(trackGroup: Tracks.Group): Boolean {
        return allowAdaptiveSelections && trackGroup.isAdaptiveSupported
    }
    
    private fun shouldEnableMultiGroupSelection(): Boolean {
        return allowMultipleOverrides && trackGroups.size > 1
    }
    
    // Internal classes.
    private inner class ComponentListener : OnClickListener {
        override fun onClick(view: View) {
            this@TrackSelectionView.onClick(view)
        }
    }
    
    private class TrackInfo(val trackGroup: Tracks.Group, val trackIndex: Int, ) {
        val format: Format
            get() = trackGroup.getTrackFormat(trackIndex)
    }
}