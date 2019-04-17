package com.mdlicht.zb.exampleproject.exoplayer.acitivity

import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
import com.mdlicht.zb.exampleproject.databinding.ActivityExoPlayerBinding


/**
 * @Refence - https://black-jin0427.tistory.com/175
 */
class ExoPlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityExoPlayerBinding

    private var player: SimpleExoPlayer? = null
    private var windowIndex: Int = 0
    private var positionMs: Long = 0
    private var playWhenReady: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_exo_player)
        binding.apply {

        }
    }

    fun initPlayer() {
        if(player == null) {
            player = ExoPlayerFactory.newSimpleInstance(this@ExoPlayerActivity)

            binding.apply {
                // 플레이어 연결
                exoPlayer.player = player

                // 컨트롤러 제거
//            exoPlayer.useController = false

                // 사이즈 조절
            exoPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

                // 변화 감지
                player?.addListener(object : Player.EventListener {
                    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                        when (playbackState) {
                            Player.STATE_IDLE -> {
                                //재생 실패
                            }
                            Player.STATE_BUFFERING -> {
                                // 재생 준비
                            }
                            Player.STATE_READY -> {
                                // 재생 준비 완료
                            }
                            Player.STATE_ENDED -> {
                                // 재생 마침
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
