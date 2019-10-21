package com.mdlicht.zb.exampleproject.exoplayer.acitivity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mdlicht.zb.exampleproject.R
import com.mdlicht.zb.exampleproject.databinding.ActivitySingleVideoBinding

class SingleVideoActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingleVideoBinding

    private var player: SimpleExoPlayer? = null
    private var windowIndex: Int = 0
    private var positionMs: Long = 0
    private var playWhenReady: Boolean = true
    private var isPlayerFullScreen: Boolean = false

    private var originLayoutParam: ViewGroup.LayoutParams? = null
    lateinit var fullScreenDialog: Dialog

    private fun initFullScreenDialog() {
        fullScreenDialog = object : Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            override fun onBackPressed() {
                if (isPlayerFullScreen) {
                    closeFullScreenDilaog()
                }
                super.onBackPressed()
            }
        }
    }

    private fun openFullScreenDialog() {
        binding.exoPlayer.let {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            (it.parent as ViewGroup).removeView(it)
            fullScreenDialog.addContentView(
                it,
                ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
            binding.exoPlayer.findViewById<ImageView>(R.id.exo_fullscreen_icon).setImageDrawable(
                ContextCompat.getDrawable(
                    this@SingleVideoActivity,
                    R.drawable.exo_controls_fullscreen_exit
                )
            )
            isPlayerFullScreen = true
            fullScreenDialog.show()
        }
    }

    private fun closeFullScreenDilaog() {
        binding.exoPlayer.let {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            (it.parent as ViewGroup).removeView(it)
            binding.container.addView(it, originLayoutParam)
            isPlayerFullScreen = false
            fullScreenDialog.dismiss()
            binding.exoPlayer.findViewById<ImageView>(R.id.exo_fullscreen_icon).setImageDrawable(
                ContextCompat.getDrawable(
                    this@SingleVideoActivity,
                    R.drawable.exo_controls_fullscreen_enter
                )
            )
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUiFullScreen()
        } else {
            hideSystemUi()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUiFullScreen() {
        binding.exoPlayer.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding.exoPlayer.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_video)
        binding.apply {
            exoPlayer.findViewById<FrameLayout>(R.id.exo_fullscreen_button).setOnClickListener {
                if (!isPlayerFullScreen) {
                    openFullScreenDialog()
                } else {
                    closeFullScreenDilaog()
                }
            }
            originLayoutParam = exoPlayer.layoutParams
        }
        initFullScreenDialog()
    }

    fun initPlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(this@SingleVideoActivity)

            binding.apply {
                // 플레이어 연결
                exoPlayer.player = player

                // 컨트롤러 제거
//            exoPlayer.useController = false

                // 사이즈 조절
                exoPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT

                // 변화 감지
                player?.addListener(object : Player.EventListener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
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
                            }
                            Player.STATE_ENDED -> {
                                // 재생 마침
                                //playback ended
                            }
                        }
                    }
                })
            }
        }

        val sampleVideo = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mediaSource = buildMediaSource(Uri.parse(sampleVideo))

        player?.let {
            it.prepare(mediaSource, true, false)
            it.playWhenReady = playWhenReady

            // 음량 조절
//            it.volume = 0f

            // 영상 포지션 설정
            it.seekTo(windowIndex, positionMs)
        }
    }

    fun releasePlayer() {
        player?.let {
            positionMs = it.currentPosition
            windowIndex = it.currentWindowIndex
            playWhenReady = it.playWhenReady
            binding.exoPlayer.player = null
            it.release()
            player = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = Util.getUserAgent(this, getString(R.string.app_name))

        return if (uri.lastPathSegment.contains("mp3") || uri.lastPathSegment.contains("mp4")) {
            ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)

        } else if (uri.lastPathSegment.contains("m3u8")) {
            //com.google.android.exoplayer:exoplayer-hls 확장 라이브러리를 빌드 해야 합니다.
            HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri)

        } else {
            ExtractorMediaSource.Factory(DefaultDataSourceFactory(this, userAgent))
                .createMediaSource(uri)
        }
    }

    override fun onStart() {
        super.onStart()
        initPlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}
