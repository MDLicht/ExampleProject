package com.mdlicht.zb.exampleproject.exoplayer.view

import android.content.Context
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.exoplayer.viewholder.VideoViewHolder

/**
 * @From - https://androidwave.com/exoplayer-in-recyclerview-in-android/
 */
class ExoPlayerRecyclerView: RecyclerView {
    private var playerView: PlayerView? = null
    private var player: SimpleExoPlayer? = null

    private var videoSurfaceDefaultHeight = 0
    private var screenDefaultHeight = 0

    private var currentPlayItem: Int = -1
    private var isAddedVideo: Boolean = false

    private var rowParent: View? = null

    constructor(context: Context?) : super(context) {
        initialize()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initialize()
    }

    private fun initialize() {
        playerView = PlayerView(context).apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            useController = false
        }
        player = ExoPlayerFactory.newSimpleInstance(context)
        playerView!!.player = player

        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                when (playbackState) {
                    Player.STATE_IDLE -> {
                        //재생 실패
                        //nothing to play media
                    }
                    Player.STATE_BUFFERING -> {
                        // 재생 준비
                        //more data needs to load
                    }
                    Player.STATE_READY -> {
                        // 재생 준비 완료
                        //can start playback
                        playerView!!.visibility = VISIBLE
                        playerView!!.alpha = 1f
                    }
                    Player.STATE_ENDED -> {
                        // 재생 마침
                        //playback ended
                        player?.seekTo(0)
                    }
                }
            }
        })

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    playVideo()
                } else {
                    pauseVideo()
                }
            }
        })

        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View?) {
                if(isAddedVideo && rowParent != null && rowParent?.equals(view)!!) {
                    removeVideoView(playerView!!)
                    currentPlayItem = -1
                    playerView!!.visibility = View.INVISIBLE
                }
            }

            override fun onChildViewAttachedToWindow(view: View?) {

            }
        })
    }

    private fun removeVideoView(videoView: PlayerView?) {
        videoView?.parent?.let {
            val parent = it as ViewGroup

            val index = parent.indexOfChild(videoView)
            if (index >= 0) {
                parent.removeViewAt(index)
                isAddedVideo = false
            }
        }
    }

    fun pauseVideo() {
        player?.playWhenReady = false
    }

    fun playVideo() {
        val startPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var endPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (endPosition - startPosition > 1) {
            endPosition = startPosition + 1
        }

        if (startPosition < 0 || endPosition < 0) {
            return
        }

        val targetPosition: Int = if (startPosition != endPosition) {
            val startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition)
            val endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition)
            if (startPositionVideoHeight > endPositionVideoHeight) startPosition else endPosition
        } else {
            startPosition
        }

        if(currentPlayItem == targetPosition) {
            player?.playWhenReady = true
            return
        }

        if (targetPosition < 0){// || targetPosition == currentPlayItem) {
            return
        }

        currentPlayItem = targetPosition
        if (playerView == null) {
            return
        }
        playerView?.let {
            it.visibility = View.INVISIBLE
            removeVideoView(it)

            // get target View targetPosition in RecyclerView
            val at = targetPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            val child = getChildAt(at) ?: return

            val holder = child.tag as VideoViewHolder
            if (holder == null) {
                currentPlayItem = -1
                return
            }
            val container = holder.itemView.findViewById<FrameLayout>(R.id.container)
            container.addView(playerView)
            isAddedVideo = true
            rowParent = holder.itemView
            playerView!!.requestFocus()
            // Bind the player to the view.
            playerView!!.player = player

            // Measures bandwidth during playback. Can be null if not required.
            val defaultBandwidthMeter = DefaultBandwidthMeter()
            // Produces DataSource instances through which media data is loaded.

            val dataSourceFactory = DefaultHttpDataSourceFactory(
                Util.getUserAgent(context, context.getString(R.string.app_name))
            )
            // This is the MediaSource representing the media to be played.
            val uriString = holder.binding.video?.sources?.get(0)
//            val uriString = videoInfoList.get(targetPosition).getUrl()
            if (uriString != null) {
                val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(uriString))
                // Prepare the player with the source.
                player!!.prepare(videoSource)
                player!!.playWhenReady = true
            }
        }
    }

    private fun getVisibleVideoSurfaceHeight(currentPlayItem: Int): Int {
        val at = currentPlayItem - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        val child = getChildAt(at) ?: return 0

        val location01 = IntArray(2)
        child.getLocationInWindow(location01)

        return if (location01[1] < 0) {
            location01[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location01[1]
        }
    }

    fun onPausePlayer() {
        playerView?.let {
            removeVideoView(it)
            player?.release()
            playerView = null
        }
    }

    fun onRestartPlayer() {
        if (playerView == null) {
            currentPlayItem = -1
            playVideo()
        }
    }

    fun onRelease() {
        player?.let {
            it.release()
            player = null
        }
        rowParent = null
    }
}