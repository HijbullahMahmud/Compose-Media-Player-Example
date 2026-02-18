package com.tenfingers.composevideoexample

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chaintech.videoplayer.host.MediaPlayerError
import chaintech.videoplayer.host.MediaPlayerEvent
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.PlayerSpeed
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.ui.video.VideoPlayerComposable

@Composable
fun ChainTechVideoPlayer() {
    val videoPlayerHost = MediaPlayerHost(
        mediaUrl = "https://ndtv24x7elemarchana.akamaized.net/hls/live/2003678/ndtv24x7/ndtv24x7master.m3u8",
        autoPlay = true,
        isMuted = false,
        initialSpeed = PlayerSpeed.X1,
        initialVideoFitMode = ScreenResize.FIT,
        isLooping = false,
        startTimeInSeconds = 10f,
        isFullScreen = true
    )
    videoPlayerHost.onEvent = { event ->
        when (event) {
            is MediaPlayerEvent.MuteChange -> {
                println("Mute status changed: ${event.isMuted}")
            }

            is MediaPlayerEvent.PauseChange -> {
                println("Pause status changed: ${event.isPaused}")
            }

            is MediaPlayerEvent.BufferChange -> {
                println("Buffering status: ${event.isBuffering}")
            }

            is MediaPlayerEvent.CurrentTimeChange -> {
                println("Current playback time: ${event.currentTime}s")
            }

            is MediaPlayerEvent.TotalTimeChange -> {
                println("Video duration updated: ${event.totalTime}s")
            }

            is MediaPlayerEvent.FullScreenChange -> {
                println("FullScreen status changed: ${event.isFullScreen}")
            }

            is MediaPlayerEvent.PIPChange -> {println("Video playback PIPChange")}

              MediaPlayerEvent.MediaEnd -> {
                println("Video playback ended")
            }
        }

    }

    videoPlayerHost.onError = { error ->
        when (error) {
            is MediaPlayerError.VlcNotFound -> {
                println("Error: VLC library not found. Please ensure VLC is installed.")
            }

            is MediaPlayerError.InitializationError -> {
                println("Initialization Error: ${error.details}")
            }

            is MediaPlayerError.PlaybackError -> {
                println("Playback Error: ${error.details}")
            }

            is MediaPlayerError.ResourceError -> {
                println("Resource Error: ${error.details}")
            }
        }
    }

    VideoPlayerComposable(
        modifier = Modifier.fillMaxSize(),
        playerHost = videoPlayerHost
    )
}